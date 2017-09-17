package com.company.communicate.service;

import com.company.communicate.domain.SmsInfo;

public interface SmsService {
	/**
	 * 发送短信的服务，
	 * @param smsInfo
	 * @return 0 -- 成功，失败原因
	 */
	public int sendSms(SmsInfo smsInfo);
	/**
	 * 
	 * @param transId
	 * @param appType
	 * @param smsType
	 * @return 0 -- 成功，失败原因
	 */
	public int querySendSmsResult(String appType,String smsType,String transId);
}
