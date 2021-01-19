package com.jkrumm.btcpay.wallet;

import java.io.File;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WalletConfiguration {

    private final Logger log = LoggerFactory.getLogger(WalletConfiguration.class);

    @Value("${application.wallet.network}")
    private String network;

    @Value("${application.wallet.filePrefix}")
    private String filePrefix;

    @Value("${application.wallet.btcFileLocation}")
    private String btcFileLocation;

    @Bean
    public NetworkParameters networkParameters() {
        if (network.equals("testnet")) {
            return TestNet3Params.get();
        } else if (network.equals("regtest")) {
            return RegTestParams.get();
        } else {
            return MainNetParams.get();
        }
    }

    @Bean
    public WalletAppKit walletAppKit(@Autowired NetworkParameters networkParameters) {
        return new WalletAppKit(networkParameters, new File(btcFileLocation), filePrefix) {
            @Override
            protected void onSetupCompleted() {
                if (wallet().getKeyChainGroupSize() < 1) {
                    wallet().importKey(new ECKey());
                }
            }
        };
    }
}
