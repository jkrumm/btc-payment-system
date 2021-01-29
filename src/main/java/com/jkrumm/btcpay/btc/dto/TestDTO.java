package com.jkrumm.btcpay.btc.dto;

/**
 * DTO for storing a user's activity.
 */
public class TestDTO {

    private String name;

    public TestDTO() {}

    public TestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestDTO{" + "name='" + name + '\'' + '}';
    }
}
