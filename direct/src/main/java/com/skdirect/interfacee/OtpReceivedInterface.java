package com.skdirect.interfacee;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);
    void onOtpTimeout();

}
