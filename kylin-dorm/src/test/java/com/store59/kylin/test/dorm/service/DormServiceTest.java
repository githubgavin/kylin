package com.store59.kylin.test.dorm.service;

import org.springframework.context.ApplicationContext;

import com.store59.kylin.common.Application;
import com.store59.kylin.dorm.data.model.Dorm;
import com.store59.kylin.dorm.service.DormService;

import junit.framework.TestCase;

public class DormServiceTest extends TestCase {
	
	public void testUpdateDorm(){
		int dormId=3;
		ApplicationContext ctx = Application.getContext();
		DormService dormService = ctx.getBean(DormService.class);
		Dorm dorm = dormService.getDorm(dormId);
		if(dorm !=null){
			String oldNotice = dorm.getNotice();
			Dorm newDorm = new Dorm();
			newDorm.setDormId(dorm.getDormId());
			newDorm.setNotice(oldNotice);
			Boolean isUpdated = dormService.updateDorm(newDorm);
			assertTrue(isUpdated);
		}
	}

}
