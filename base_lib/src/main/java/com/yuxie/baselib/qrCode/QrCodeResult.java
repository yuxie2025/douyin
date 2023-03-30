package com.yuxie.baselib.qrCode;

public class QrCodeResult {

    private String text;

    public QrCodeResult() {
    }

    public QrCodeResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
