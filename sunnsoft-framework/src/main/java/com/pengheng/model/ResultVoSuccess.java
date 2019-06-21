package com.pengheng.model;

public class ResultVoSuccess  extends ResultVo{
    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoSuccess()
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), ReplyCode.ALL_SUCCESS.getTxtCode());
    }

    public ResultVoSuccess(String paramString)
    {

        super(ReplyCode.ALL_SUCCESS.getCode(),paramString);
    }

    public ResultVoSuccess(String paramString, Object paramObject)
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), paramString, paramObject);
    }

    public ResultVoSuccess(String paramString, Object paramObject1, Object paramObject2)
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), paramString, paramObject1, paramObject2);
    }
}