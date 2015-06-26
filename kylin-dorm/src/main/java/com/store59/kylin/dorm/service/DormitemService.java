package com.store59.kylin.dorm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.common.exception.ServiceException;
import com.store59.kylin.dorm.data.dao.DormitemDao;
import com.store59.kylin.dorm.data.filter.StockFilter;
import com.store59.kylin.dorm.data.model.Dormitem;

@Service
public class DormitemService {
	@Autowired
	DormitemDao dormitemDao;
	@Autowired
	private SqlSessionTemplate masterSqlSession;

	public Boolean addDormItemStock(int dormId, int rid, int stock) {
		int rows = dormitemDao.addDormItemStock(dormId, rid, stock);
		if (rows == 0) {
			return false;
		}
		return true;
	}

	public void checkAndUpdateStock(int dormId,
			Map<Integer, Integer> cartQuantityMap) {
		if (cartQuantityMap.size() == 0) {
			return;
		}
		Collection<Integer> ridList = cartQuantityMap.keySet();
		List<Dormitem> itemList = dormitemDao.selectByDormIdRidList(dormId,
				ridList);
		List<StockFilter> filterList = new ArrayList<>();
		for (Dormitem item : itemList) {
			int curStock = item.getStock();
			int salesStock = cartQuantityMap.get(item.getRid());
			StockFilter filter = new StockFilter();
			filter.setRid(item.getRid());
			filter.setStock(-salesStock);
			filterList.add(filter);
			if (salesStock > curStock) {
				throw new ServiceException(1108, item.getRid() + "库存不足");
			}
		}
		// 批量减库存
		int count = dormitemDao.bulkUpdateStock(dormId, filterList);
		if (count < ridList.size()) {
			// rollback
			masterSqlSession.rollback();
			throw new ServiceException(1192, "减少库存错误");
		}
	}
}
