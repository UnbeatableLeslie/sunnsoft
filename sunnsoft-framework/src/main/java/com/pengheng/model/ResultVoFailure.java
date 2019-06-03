package com.pengheng.model;

public class ResultVoFailure extends ResultVo{

    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoFailure()
    {
        super(ReplyCode.ALL_FAILURE.getCode(), ReplyCode.ALL_FAILURE.getTxtCode());
    }

    public ResultVoFailure(String paramString)
    {
        super(ReplyCode.ALL_FAILURE.getCode(), paramString);
    }

    public ResultVoFailure(String paramString, Object paramObject)
    {
        super(ReplyCode.ALL_FAILURE.getCode(), paramString, paramObject);
    }

    public ResultVoFailure(String paramString, Object paramObject, Object paramObject2)
    {
        super(ReplyCode.ALL_FAILURE.getCode(), paramString, paramObject, paramObject2);
    }




}