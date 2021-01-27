package com.jkrumm.btcpay.wallet.dto;

/**
 * DTO for storing a user's activity.
 */
public class GreetingDTO {

    private String content;

    public GreetingDTO() {}

    public GreetingDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
