package com.jkrumm.btcpay.btc.websocket.dto;

import com.jkrumm.btcpay.btc.websocket.dto.blockcypher.BlockCypherCompactDTO;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;

public class ConfirmationDTO {

    private String address;

    private ConfidenceDTO confidence;

    private BlockCypherCompactDTO blockCypher;

    public ConfirmationDTO(String address, ConfidenceDTO confidence, TransactionDTO transaction, BlockCypherCompactDTO bc) {
        this.address = address;
        this.confidence = confidence;
        this.confidence.setTransaction(transaction);
        this.blockCypher = bc;
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

    public BlockCypherCompactDTO getBlockCypher() {
        return blockCypher;
    }

    public void setBlockCypher(BlockCypherCompactDTO blockCypher) {
        this.blockCypher = blockCypher;
    }

    @Override
    public String toString() {
        return "ConfirmationDTO{" + "address='" + address + '\'' + ", confidence=" + confidence + ", blockCypher=" + blockCypher + '}';
    }
}
