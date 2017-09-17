package com.company.communicate.service.impl;

import com.company.communicate.Const.CommunicateConst;
import com.company.communicate.domain.SmsInfo;
import com.company.communicate.service.SmsService;
import com.company.communicate.utils.SecuritySmsAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.dysmsapi.transform.v20170525.SendSmsResponseUnmarshaller;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Service("alidayuSmsService")
public class SmsServiceAlidayuImpl implements SmsService,InitializingBean {

	@Value("${alidayu.accessKeyId}")  
	private String accessKeyId;
	@Value("${alidayu.accessKeySecret}")  
	private String accessKeySecret;
	
	@Value("${alidayu.product}")  
	private String product;
	@Value("${alidayu.domain}")  
	private String domain;
	
	@Value("${alidayu.appName}")  
	private String appName;
	
	@Value("${alidayu.templateCode}")  
	private String templateCodes;
	

	@Value("${alidayu.transferKey}")  
	private String transferKey;
	
	private Map<String,String>templateMaps = new ConcurrentHashMap<String,String>();
			
	protected String getAlidayuPhone(String countryCode,String phone)
	{
		if(phone.startsWith(countryCode))
		{
			return phone.substring(countryCode.length());
		}
		if(phone.startsWith("00"))
		{
			return "+" + phone.substring(2);
		}
		
		return phone;
	}
	
	
	@Override
	public int sendSms(SmsInfo smsInfo)  {
		// TODO Auto-generated method stub
		try {
			if(!SecuritySmsAlgorithm.checkCrc(transferKey, smsInfo))
			{
				return CommunicateConst.RESULT_Error_CheckCrc;
			}
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

	        //初始化acsClient,暂不支持region化
	        //System.out.println(accessKeyId+":" +accessKeySecret);
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId.trim(), accessKeySecret.trim());
	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product.trim(), domain.trim());
	        IAcsClient acsClient = new DefaultAcsClient(profile);

	        //组装请求对象-具体描述见控制台-文档部分内容
	        SendSmsRequest request = new SendSmsRequest();
	        //必填:待发送手机号
	        request.setPhoneNumbers(getAlidayuPhone(smsInfo.getCountryCode(),smsInfo.getCalledPhoneNumbers()));
	        //必填:短信签名-可在短信控制台中找到
	        request.setSignName(appName.trim());
	        //必填:短信模板-可在短信控制台中找到
	       String alidayuTemplateCode = getAlidayTemplateCode(smsInfo.getSmsTemplateCode());
	       if(StringUtils.isEmpty(alidayuTemplateCode))
	       {
	    	   return CommunicateConst.RESULT_Error_templateNotExist;
	       }
	      // System.out.println(alidayuTemplateCode);
	       request.setTemplateCode(alidayuTemplateCode.trim());
	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	       //System.out.println(smsInfo.getParameters());
	       request.setTemplateParam(smsInfo.getParameters().trim());

	        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
	        //request.setSmsUpExtendCode("90997");

	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	        request.setOutId(smsInfo.getRequestAppName() + CommunicateConst.Common_Split +  smsInfo.getTransId());

	        //hint 此处可能会抛出异常，注意catch
	        SendSmsResponse sendSmsResponse=null;
				//System.out.println(product+":"+domain+":"+appName);
				sendSmsResponse = acsClient.getAcsResponse(request);
				//System.out.println(sendSmsResponse.getCode() + sendSmsResponse.getMessage());
				if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		        	//请求成功
		        	return CommunicateConst.RESULT_SUCCESS;
		        }
				else if(sendSmsResponse.getCode().equals("isv.MOBILE_NUMBER_ILLEGAL")) 
				{
					return CommunicateConst.RESULT_Error_PhoneError;
				}
				else if(sendSmsResponse.getCode().equals("isv.MOBILE_COUNT_OVER_LIMIT")) 
				{
					return CommunicateConst.RESULT_Error_PhoneNumberError;
				}
				else if(sendSmsResponse.getCode().equals("isv.AMOUNT_NOT_ENOUGH")) 
				{
					return CommunicateConst.RESULT_Error_NotEnough;
				}
				
					
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return CommunicateConst.RESULT_FAILURE;
	        
		
	}

	@Override
	public int querySendSmsResult(String appType, String smsType, String transId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 根据客户端发送的模版请求获取阿里大鱼的模版
	 * @param clientTemplateCode
	 * @return
	 */
	public String getAlidayTemplateCode(String clientTemplateCode)
	{
		//System.out.println(templateMaps + ":" + clientTemplateCode + ":" + this.templateMaps.get(clientTemplateCode.toLowerCase()));
		return this.templateMaps.get(clientTemplateCode.toLowerCase());
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		String[] templateInfos = this.templateCodes.split(",");
		for(int i =0;i<templateInfos.length;i++)
		{
			String templateInfoString=templateInfos[i];
			String []templateInfo = templateInfoString.split(":");
			this.templateMaps.put(templateInfo[0].toLowerCase(), templateInfo[1].trim());
			
		}
	}

}
