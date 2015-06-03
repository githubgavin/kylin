package com.store59.kylin.dormapi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store59.kylin.dorm.data.model.Dorm;
import com.store59.kylin.dorm.service.DormService;
import com.store59.kylin.dormapi.exception.ServiceException;

@Component
public class DormLogic {
	@Autowired
	private DormService dormService;

	public Dorm getDormByUid(Integer uid) {
		Dorm dorm = dormService.getDormByUid(uid);
		if (dorm == null) {
			throw new ServiceException(1000, "不存在该楼主");
		}
		int dormStatus = dorm.getStatus().intValue();
		if (dormStatus == 1) {
			throw new ServiceException(1001, "楼主账号已被删除");
		} else if (dormStatus == 2) {
			throw new ServiceException(1002, "楼主账号已被限制登录");
		} else if (dormStatus != 0) {
			throw new ServiceException(-1);
		}
		return dorm;
	}

	/**
	 * 确认楼主与token是否匹配
	 * 
	 * @param dormId
	 * @param token
	 * @return
	 */
	public Boolean checkDormId(Integer dormId, UserToken token) {
		if (dormId == null || token == null
				|| !dormId.equals(token.getDormId())) {
			throw new ServiceException(1003, "不匹配的楼主");
		}
		return true;
	}

}
