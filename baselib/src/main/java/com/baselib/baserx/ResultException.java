package com.baselib.baserx;

import java.io.IOException;

/**
 * 作者: llk on 2017/10/9.
 */
@SuppressWarnings("unused")
public class ResultException extends IOException {

    private String errMsg;
    private boolean errCode;

    public ResultException(String errMsg, boolean errCode) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isErrCode() {
        return errCode;
    }

    public void setErrCode(boolean errCode) {
        this.errCode = errCode;
    }
}