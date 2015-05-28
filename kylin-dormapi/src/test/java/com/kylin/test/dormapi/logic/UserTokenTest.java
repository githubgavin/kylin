package com.kylin.test.dormapi.logic;

import com.kylin.dormapi.logic.UserToken;

import junit.framework.TestCase;

public class UserTokenTest extends TestCase {

	public void testCreateFromString() {
		UserToken tu = new UserToken();
		tu.setUserId(1);
		String token = UserToken.getTokenString(tu);
		assertTrue(token != null);
		System.out.println(String.format("token length:%s", token.length()));
		UserToken tu2 = UserToken.createToken(token);
		assertTrue(tu2 != null);
		assertTrue(tu.getUserId().equals(tu2.getUserId()));
		assertTrue(tu.getEndTime() == tu2.getEndTime());
	}

	public void testIsValid() {
		UserToken tu = new UserToken();
		assertTrue(tu.isValid());
		long t = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 8;
		tu.setEndTime(t);
		assertTrue(!tu.isValid());
	}
}
