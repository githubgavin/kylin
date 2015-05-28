package com.kylin.test.common;

import com.kylin.common.DESUtil;

import junit.framework.TestCase;

public class DESUtilTest extends TestCase {
	public void testEncryptAndDecrypt() throws Exception{
		 String source = "abcTest测试6789";
		 String key = "qbcde1343jio";
		 String encryptData = DESUtil.encrypt(source, key);
		 String decryptData = DESUtil.decrypt(encryptData, key);
		 assertTrue(source.equals(decryptData));
		 
	}
}
