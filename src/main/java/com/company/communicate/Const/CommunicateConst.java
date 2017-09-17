package com.company.communicate.Const;

public class CommunicateConst {
	
	public static final String Common_Split = ":";
    
	//范围2001 到3000
	//成功
    public static final int RESULT_SUCCESS = 0;
    
    
    public static final int RESULT_Error_startCode = 2001;
    public static final int RESULT_FAILURE = RESULT_Error_startCode;
    
    /**
     * 短信模版不存在
     */
    public static final int RESULT_Error_templateNotExist = RESULT_Error_startCode+1;
    
    /**
     * 电话号码格式错误
     */
    public static final int RESULT_Error_PhoneError = RESULT_Error_startCode+2;
    /**
     * 手机号码数量超过限制
     */
    public static final int RESULT_Error_PhoneNumberError = RESULT_Error_startCode+3;
    /**
     * 账户余额不足
     */
    public static final int RESULT_Error_NotEnough = RESULT_Error_startCode+4;
    /**
     * 
     */
    public static final int RESULT_Error_CheckCrc = RESULT_Error_startCode+5;
}
