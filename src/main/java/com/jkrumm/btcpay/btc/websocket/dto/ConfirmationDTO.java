package com.jkrumm.btcpay.btc.websocket.dto;

import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import java.util.Collection;

public class ConfirmationDTO {

    private String address;

    private ConfidenceDTO confidence;

    public ConfirmationDTO(String address, ConfidenceDTO confidence, TransactionDTO transaction) {
        this.address = address;
        this.confidence = confidence;
        this.confidence.setTransaction(transaction);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ConfidenceDTO getConfidence() {
        return confidence;
    }

    public void setConfidence(ConfidenceDTO confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ConfirmationDTO{" + "address='" + address + '\'' + ", confidence=" + confidence + '}';
    }
}
