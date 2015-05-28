package com.kylin.test.dorm.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kylin.dorm.data.model.Dormentry;
import com.kylin.dorm.service.DormentryService;

import junit.framework.TestCase;

public class DormentryServiceTest extends TestCase {

	public void testGetDormEntryList() {
		Integer dormId = 3;
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext_dorm.xml",
						"applicationContext_common.xml" });
		DormentryService ds = ctx.getBean(DormentryService.class);
		List<Dormentry> dormentries = ds.getDormentryList(dormId);
		assertTrue(dormentries != null);
	}

}
