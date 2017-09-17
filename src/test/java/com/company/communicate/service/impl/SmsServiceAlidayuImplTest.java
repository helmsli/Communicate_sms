/**
 * 
 */
package com.company.communicate.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.communicate.Const.CommunicateConst;
import com.company.communicate.domain.SmsInfo;
import com.company.communicate.service.SmsService;
import com.company.communicate.utils.SecuritySmsAlgorithm;

/**
 * @author helmsli
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsServiceAlidayuImplTest {
	@Resource(name="alidayuSmsService")
	private SmsService alidayySmsService;

	@Value("${alidayu.transferKey}")  
	private String transferKey;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.company.communicate.service.impl.SmsServiceAlidayuImpl#sendSms(com.company.communicate.domain.SmsInfo)}.
	 */
	@Test
	public void testSendSms() {
		
		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setTransId(String.valueOf(System.currentTimeMillis()));
		smsInfo.setDestAppName("chunzeAcademy");
		smsInfo.setSmsTemplateCode("Register");
		smsInfo.setCountryCode("0086");
		smsInfo.setCalledPhoneNumbers("00861861016171133");
		smsInfo.setParameters("{ \"code\":\"157098\"}");
		smsInfo.setCheckCrc(SecuritySmsAlgorithm.getCrcString(transferKey, smsInfo));
		int iRet = alidayySmsService.sendSms(smsInfo);
		assertEquals("send sms need ok but error",CommunicateConst.RESULT_SUCCESS,iRet);
	}

}
