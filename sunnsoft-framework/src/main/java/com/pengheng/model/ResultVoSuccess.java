package com.pengheng.model;

public class ResultVoSuccess  extends ResultVo{
    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoSuccess()
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), ReplyCode.ALL_SUCCESS.getTxtCode());
    }

    public ResultVoSuccess(String msg)
    {

        super(ReplyCode.ALL_SUCCESS.getCode(),msg);
    }

    public ResultVoSuccess(String msg, Object data)
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), msg, data);
    }

    public ResultVoSuccess(String msg, Object data, Object info)
    {
        super(ReplyCode.ALL_SUCCESS.getCode(), msg, data, info);
    }
}