package com.jkrumm.btcpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to BTC Payment System.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Wallet wallet = new Wallet();

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    private static class Wallet {

        private String network;
        private String filePrefix;
        private String btcFileLocation;

        public String getNetwork() {
            return network;
        }

        public Wallet setNetwork(String network) {
            this.network = network;
            return this;
        }

        public String getFilePrefix() {
            return filePrefix;
        }

        public Wallet setFilePrefix(String filePrefix) {
            this.filePrefix = filePrefix;
            return this;
        }

        public String getBtcFileLocation() {
            return btcFileLocation;
        }

        public Wallet setBtcFileLocation(String btcFileLocation) {
            this.btcFileLocation = btcFileLocation;
            return this;
        }
    }
}
