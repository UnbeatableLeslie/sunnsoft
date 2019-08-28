package com.pengheng.model;

public class ResultVoSuccess  extends ResultVo{
    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoSuccess()
    {
        super(ReplyCode.SUCCESS.getCode(), ReplyCode.SUCCESS.getTxtCode());
    }

    public ResultVoSuccess(String msg)
    {

        super(ReplyCode.SUCCESS.getCode(),msg);
    }

    public ResultVoSuccess(String msg, Object data)
    {
        super(ReplyCode.SUCCESS.getCode(), msg, data);
    }

}