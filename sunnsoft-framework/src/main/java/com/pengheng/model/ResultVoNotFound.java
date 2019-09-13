package com.pengheng.model;

public class ResultVoNotFound extends ResultVo{

    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoNotFound()
    {
        super(ReplyCode.NOT_FOUND.getCode(), ReplyCode.NOT_FOUND.getTxtCode());
    }

    public ResultVoNotFound(String paramString)
    {
        super(ReplyCode.NOT_FOUND.getCode(), paramString);
    }

    public ResultVoNotFound(String paramString, Object paramObject)
    {
        super(ReplyCode.NOT_FOUND.getCode(),paramString, paramObject);
    }

}