package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.repository.ConfidenceRepository;
import com.jkrumm.btcpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class WalletRepositoryContainer {

    @Component
    public class Container {

        @Autowired
        public TransactionRepository transaction;

        @Autowired
        public ConfidenceRepository confidence;
    }
}
