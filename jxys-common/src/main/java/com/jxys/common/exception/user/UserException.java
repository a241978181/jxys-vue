package com.jxys.common.exception.user;

import com.jxys.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author jxys
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
