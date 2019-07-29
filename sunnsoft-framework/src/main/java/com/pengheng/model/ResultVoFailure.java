package com.pengheng.model;

public class ResultVoFailure extends ResultVo{

    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoFailure()
    {
        super(ReplyCode.ALL_FAILURE.getCode(), ReplyCode.ALL_FAILURE.getTxtCode());
    }

    public ResultVoFailure(String msg)
    {
        super(ReplyCode.ALL_FAILURE.getCode(), msg);
    }

    public ResultVoFailure(String msg, Object data)
    {
        super(ReplyCode.ALL_FAILURE.getCode(), msg, data);
    }

}