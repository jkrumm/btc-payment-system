package com.jkrumm.btcpay.config;

import io.github.jhipster.config.JHipsterProperties;
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
import org.springframework.core.env.Environment;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WalletConfiguration {
    private final Logger log = LoggerFactory.getLogger(WalletConfiguration.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    public WalletConfiguration(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Value("${wallet.network}")
    private String network;

    @Value("${wallet.filePrefix}")
    private String filePrefix;

    @Value("${wallet.btcFileLocation}")
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
