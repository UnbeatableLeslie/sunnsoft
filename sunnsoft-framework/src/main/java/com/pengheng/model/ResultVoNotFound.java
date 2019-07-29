package com.pengheng.model;

public class ResultVoNotFound extends ResultVo{

    private static final long serialVersionUID = 5519830221310451414L;

    public ResultVoNotFound()
    {
        super(ReplyCode.ALL_FAILURE.getCode(), ReplyCode.ALL_FAILURE.getTxtCode());
    }

    public ResultVoNotFound(String paramString)
    {
        super(ReplyCode.getReplayCodes(paramString), ReplyCode.getReplayTexts(paramString));
    }

    public ResultVoNotFound(String paramString, Object paramObject)
    {
        super(ReplyCode.getReplayCodes(paramString), ReplyCode.getReplayTexts(paramString), paramObject);
    }

}