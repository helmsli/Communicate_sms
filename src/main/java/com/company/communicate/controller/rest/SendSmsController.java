package com.company.communicate.controller.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.communicate.Const.CommunicateConst;
import com.company.communicate.domain.SmsInfo;
import com.company.communicate.service.SmsService;
import com.xinwei.nnl.common.domain.ProcessResult;

@RestController
@RequestMapping("/smsService")
public class SendSmsController {
	@Resource(name="alidayuSmsService")
	private SmsService alidayySmsService;
	
	@RequestMapping(method = RequestMethod.POST,value = "{destAppName}/{countryCode}")
	public  ProcessResult sendSms(@PathVariable String destAppName,@PathVariable String countryCode,@RequestBody SmsInfo smsInfo) {
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(CommunicateConst.RESULT_FAILURE);
		try {
			int iRet = alidayySmsService.sendSms(smsInfo);
			processResult.setRetCode(iRet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processResult;
	}
	@RequestMapping(method = RequestMethod.GET,value = "{destAppName}/{countryCode}")
	public  ProcessResult querySendSmsResult(@PathVariable String phone,@RequestBody SmsInfo smsInfo) {
		return null;
	}
}
