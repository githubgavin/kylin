package com.store59.kylin.order.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.common.C;
import com.store59.kylin.common.Util;
import com.store59.kylin.common.exception.ServiceException;
import com.store59.kylin.dorm.data.model.Dormentry;
import com.store59.kylin.dorm.data.model.Dormrepo;
import com.store59.kylin.dorm.service.DormentryService;
import com.store59.kylin.dorm.service.DormitemService;
import com.store59.kylin.dorm.service.DormrepoService;
import com.store59.kylin.order.data.dao.OrderDao;
import com.store59.kylin.order.data.dao.OrderfoodDao;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.CartItem;
import com.store59.kylin.order.data.model.Coupon;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.model.Orderfood;
import com.store59.kylin.user.data.model.User;
import com.store59.kylin.user.service.UserService;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderfoodDao orderfoodDao;
    @Autowired
    private DormitemService dormitemService;
    @Autowired
    private DormrepoService dormrepoService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserService userService;
    @Autowired
    private DormentryService dormentryService;

    public List<Order> getOrderList(OrderFilter filter) {
        List<Byte> status = filter.getStatus();
        if (status != null && status.size() == 0) {
            filter.setStatus(null);
        }
        return orderDao.selectByFilter(filter);
    }

    public Boolean updateOrder(Order order) {
        if (order.getStatus() != null) {
            this.setStatus(order.getOrderId(), order.getStatus());
        }
        return orderDao.updateByPrimaryKeySelective(order);
    }

    public Order getOrder(Long orderId) {
        return orderDao.selectByPrimaryKey(orderId);
    }

    public Boolean setStatus(Long orderId, Byte status) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (status == null || order == null) {
            throw new RuntimeException("请求参数有误");
        }
        Byte oldStatus = order.getStatus();
        if (oldStatus == 4 || oldStatus == 5) {
            throw new RuntimeException("订单不允许更新状态");
        }
        Order updateOrder = new Order();
        updateOrder.setOrderId(orderId);
        updateOrder.setStatus(status);
        int now = (int) (System.currentTimeMillis() / 1000);
        if (status == 1) {
            updateOrder.setConfirmTime(now);
        }
        if (status == 2) {
            updateOrder.setSendTime(now);
        }
        orderDao.updateByPrimaryKeySelective(updateOrder);
        // 取消订单，需要返回库存
        if (status == 5) {
            backDormItemStock(orderId, order.getDormId());
            couponService.backOrderCoupon(orderId);
        }
        return true;
    }

    private void backDormItemStock(Long orderId, int dormId) {
        List<Orderfood> foodList = orderfoodDao.selectByOrderId(orderId);
        if (foodList == null) {
            return;
        }
        for (Orderfood food : foodList) {
            // 要增加的库存数量
            dormitemService.addDormItemStock(dormId, food.getRid(),
                    food.getQuantity());
        }
    }

    public Boolean addOrder(Order order) {
        int count = orderDao.insertSelective(order);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public Order placeOrder(byte type, byte paytype, byte source,
            int dormentryId, Long uid, List<CartItem> cartItemList,
            String couponCode, BigDecimal promotionDiscount,
            BigDecimal foodAmount, int foodNum, BigDecimal orderAmount,
            String phone, String dormitory, String ip, String remark) {
        // 校验手机号码
        boolean r = Util.isPhoneNumber(phone);
        if (!r) {
            throw new ServiceException(1101, "手机号码格式错误");
        }
        // 校验用户信息
        User user;
        if (uid > 0) {
            user = userService.getUser(uid);
            if (user == null) {
                throw new ServiceException(1108, "用户不存在");
            }
            if (user.getRole() != C.USER_ROLE_USER) {
                throw new ServiceException(1102, "管理员账号不能下单");
            }
        } else {
            user = new User();
            user.setUid(0L);
            user.setUname("游客");
            user.setPortrait("");
        }
        String uname = user.getUname();
        String portrait = user.getPortrait();
        Dormentry dormentry = dormentryService.getDormentry(dormentryId);
        if (dormentry == null) {
            throw new ServiceException(1103, "请选择夜猫店");
        }
        Integer dormId = dormentry.getDormId();
        if (dormId == null) {
            throw new ServiceException(1109, "该楼栋未绑定楼主");
        }

        if (dormentry.getStatus() == C.DORMENTRY_STATUS_PREPARING) {
            throw new ServiceException(1104, "夜猫店还未开张");
        } else if (dormentry.getStatus() == C.DORMENTRY_STATUS_CLOSED) {
            throw new ServiceException(1105, "夜猫店还在休息中");
        }

        String address1 = dormentry.getAddress1();
        String address2 = dormentry.getAddress2();
        int siteId = dormentry.getSiteId();
        // 校验优惠券信息
        BigDecimal couponDiscount = null;
        if (couponCode != null) {
            r = couponService.isValid(couponCode, uid, foodAmount);
            if (!r) {
                throw new ServiceException(1106, "优惠券不可用");
            }

            Coupon coupon = couponService.getCouponByCode(couponCode);
            couponDiscount = coupon.getDiscount();
        }

        // 校验商品库存
        if (cartItemList.size() == 0) {
            throw new ServiceException(1107, "购物车内没有商品");
        }
        Map<Integer, Integer> cartQuantityMap = new HashMap<>();
        for (CartItem item : cartItemList) {
            cartQuantityMap.put(item.getRid(), item.getQuantity());
        }
        dormitemService.checkAndUpdateStock(dormId, cartQuantityMap);

        // 生成订单id
        // TODO 精确到毫秒的Unix时间 ＋ 6位的随机数
        Random random = new Random();
        int bound = 1_000_000;
        int orderIdSuffix = random.nextInt(bound);
        long curTime = System.currentTimeMillis();
        int addTime = (int) (curTime / 1000);
        int expectDate = Util.getStartTimeOfOneDay(curTime);
        long orderId = curTime * bound + orderIdSuffix;

        // 插入订单
        Order order = new Order();
        order.setOrderId(orderId);
        order.setType(type);
        order.setPaytype(paytype);
        order.setSource(source);
        order.setSiteId(siteId);
        order.setDormId(dormId);
        order.setDormentryId(dormentryId);
        order.setUid(uid);
        order.setFoodAmount(foodAmount);
        order.setFoodNum((short) foodNum);
        order.setCouponDiscount(couponDiscount);
        order.setPromotionDiscount(promotionDiscount);
        order.setOrderAmount(orderAmount);
        order.setAddTime(addTime);
        order.setExpectDate(expectDate);
        order.setUname(uname);
        order.setPortrait(portrait);
        order.setPhone(phone);
        order.setAddress1(address1);
        order.setAddress2(address2);
        order.setDormitory(dormitory);
        order.setIp(ip);
        order.setCouponCode(couponCode);
        order.setRemark(remark);
        // insert
        r = addOrder(order);
        if (!r) {
            throw new ServiceException(1190, "插入订单错误");
        }

        // 插入订单相关商品
        List<Orderfood> orderfoodList = new ArrayList<>();
        for (CartItem item : cartItemList) {
            int rid = item.getRid();
            Dormrepo repo = dormrepoService.getDormRepoByRid(rid);
            Orderfood food = new Orderfood();
            food.setOrderId(orderId);
            food.setRid(rid);
            food.setPrice(item.getPrice());
            food.setQuantity(item.getQuantity().shortValue());
            food.setAmount(item.getAmount());
            food.setOriginPrice(item.getOriginPrice());
            food.setPromotionId(item.getPromotionId());
            food.setPromotionType(item.getPromotionType());
            food.setPromotionLabel(item.getPromotionLabel());
            food.setFname(repo.getName());
            food.setImageSmall(repo.getImageSmall());
            food.setImageMedium(repo.getImageMedium());
            food.setImageBig(repo.getImageBig());
            orderfoodList.add(food);
        }
        r = orderfoodDao.insertOrderfoodList(orderfoodList);
        if (!r) {
            throw new ServiceException(1191, "插入订单商品错误");
        }

        // 标记优惠券为已使用
        if (couponCode != null) {
            r = couponService.useCoupon(couponCode, uid, orderId);
            if (!r) {
                throw new ServiceException(1193, "修改优惠券状态错误");
            }
        }
        return order;
    }
}
