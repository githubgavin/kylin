package com.store59.kylin.test.dorm.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.store59.kylin.common.Application;
import com.store59.kylin.dorm.data.model.Dormentry;
import com.store59.kylin.dorm.service.DormentryService;

import junit.framework.TestCase;

public class DormentryServiceTest extends TestCase {

	public void testGetDormEntryList() {
		Integer dormId = 3;
		ApplicationContext ctx = Application.getContext();
		DormentryService ds = ctx.getBean(DormentryService.class);
		List<Dormentry> dormentries = ds.getDormentryList(dormId);
		assertTrue(dormentries != null);
	}

}
