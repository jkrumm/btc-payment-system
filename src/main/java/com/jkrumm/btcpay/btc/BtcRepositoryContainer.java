package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class BtcRepositoryContainer {

    @Component
    public class Container {

        @Autowired
        public TransactionRepository transaction;

        @Autowired
        public ConfidenceRepository confidence;

        @Autowired
        public FeeRepository fee;

        @Autowired
        public MerchantRepository merchant;

        @Autowired
        public UserRepository user;

        @Autowired
        public MerchantUserRepository merchantUser;
    }
}
