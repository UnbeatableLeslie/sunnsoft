package com.pengheng.model;

public class ResultVoFailure extends ResultVo{

    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoFailure()
    {
        super(ReplyCode.ERROR.getCode(), ReplyCode.ERROR.getTxtCode());
    }

    public ResultVoFailure(String msg)
    {
        super(ReplyCode.ERROR.getCode(), msg);
    }

    public ResultVoFailure(String msg, Object data)
    {
        super(ReplyCode.ERROR.getCode(), msg, data);
    }

    public ResultVoFailure(ReplyCode code, String msg)
    {
        super(code.getCode(), msg);
    }

}