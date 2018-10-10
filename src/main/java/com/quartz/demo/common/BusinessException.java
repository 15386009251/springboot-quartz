package com.quartz.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LIU on 2018/10/9 15:33
 */
public class BusinessException extends GenericException {
    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);

    public BusinessException() {
    }

    public BusinessException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, (Object[])null, LocaleUtil.getLocale());
        this.writeBusinessException();
    }

    public BusinessException(String errorCode, String[] keyInfos) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, keyInfos, LocaleUtil.getLocale());
        this.writeBusinessException();
    }

    public BusinessException(final String message, final String errorCode) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = message;
        this.writeBusinessException();
    }



    public BusinessException(final Throwable e) {
        this.errorCode = "999999";
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, null, LocaleUtil.getLocale());
        this.initCause(e);
        this.writeBusinessException();
    }
    private void writeBusinessException() {
        logger.error("线程：" + ThreadId.getThreadId() + " ; 业务异常编码：" + this.getErrorCode() + " ；业务异常信息：" + this.getErrorMessage());
    }
}
