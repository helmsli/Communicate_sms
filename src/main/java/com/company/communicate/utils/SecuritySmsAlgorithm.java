package com.company.communicate.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.DigestUtils;

import com.company.communicate.domain.SmsInfo;

public  class SecuritySmsAlgorithm {
	public static String getCrcString(String key,SmsInfo smsInfo)
	{
		try {
			StringBuilder ls = new StringBuilder();
			ls.append(key);
			ls.append("*");
			ls.append(smsInfo.getTransId());
			ls.append("*");
			ls.append(smsInfo.getSmsTemplateCode());
			ls.append("*");
			ls.append(smsInfo.getCalledPhoneNumbers());
			ls.append("*");
			ls.append(smsInfo.getDestAppName());
			return DigestUtils.md5DigestAsHex(ls.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 比较密码
	 * @param key
	 * @param source
	 * @param md5String
	 * @return
	 */
	public static boolean checkCrc(String key,SmsInfo smsInfo)  {
		try {
			String crcString = getCrcString(key,smsInfo);
			return smsInfo.getCheckCrc().equalsIgnoreCase(crcString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
