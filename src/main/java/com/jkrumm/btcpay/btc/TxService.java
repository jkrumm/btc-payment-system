package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.User;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.service.dto.UserDTO;
import com.jkrumm.btcpay.service.mapper.MerchantMapperImpl;
import com.jkrumm.btcpay.service.mapper.TransactionMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Principal;
import java.time.*;
import java.util.Optional;
import org.bitcoinj.core.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TxService {

    private final Logger log = LoggerFactory.getLogger(TxService.class);

    @Autowired
    private BtcRepositoryContainer.Container repos;

    @Autowired
    private WalletService walletService;

    @Autowired
    private MerchantMapperImpl merchantMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    TransactionDTO initTx(Principal principal, Double amount) throws IOException {
        log.info("Called initTx() in TxService");
        Optional<User> user = repos.user.findOneByLogin(principal.getName());
        log.info(user.toString());
        if (user.isPresent()) {
            Address address = walletService.newAddress();

            Double eurBtcPrice = getBtcEuroPrice();
            log.info("initTx: eurBtcPrice: " + eurBtcPrice);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.get().getId());
            userDTO.setLogin(user.get().getLogin());
            log.info("initTx: userDTO: " + userDTO.toString());

            Merchant merchant = repos.merchantUser.findMerchantUserByUser(user).getMerchant();
            MerchantDTO merchantDTO = merchantMapper.toDto(merchant);
            log.info("initTx: merchantDTO: " + merchant.toString());

            Double btcAmount = getEuroToBtc(amount);
            Double serviceFee = btcAmount * (merchant.getFee().getPercent().doubleValue() / 100);

            TransactionDTO txDto = new TransactionDTO();
            txDto.setInitiatedAt(Instant.now());
            txDto.setTransactionType(TransactionType.INCOMING_CUSTOMER);
            txDto.setExpectedAmount(toSats(btcAmount - serviceFee));
            txDto.setServiceFee(toSats(serviceFee));
            txDto.setBtcUsd(eurBtcPrice);
            txDto.setUser(userDTO);
            txDto.setMerchant(merchantDTO);
            txDto.setAddress(address.toString());
            txDto.setAmount(amount);
            log.info(txDto.toString());
            log.info("initTx: txDto: " + txDto.toString());
            Transaction transaction = repos.transaction.save(transactionMapper.toEntity(txDto));
            log.info("initTx: txSaved: " + transaction.toString());
            return txDto;
        } else {
            throw new NullPointerException();
        }
    }

    Double getEuroToBtc(Double amount) throws IOException {
        URL url = new URL("https://blockchain.info/tobtc?currency=EUR&value=" + amount);
        double result = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = in.readLine()) != null) {
                try {
                    result = Double.parseDouble(line);
                } catch (NumberFormatException ex) { // handle your exception
                    log.info("NumberFormatException getEuroToBtc \n" + ex.toString());
                    return 0.0;
                }
            }
            in.close();
        } catch (IOException e) {
            log.info("I/O Error: " + e.getMessage());
        }
        log.info("getEuroToBtc : " + String.valueOf(result));
        return result;
    }

    Double getBtcEuroPrice() throws IOException {
        log.info("getBtcEuroPrice : " + String.valueOf(10000 / getEuroToBtc(10000.0)));
        return 10000 / getEuroToBtc(10000.0);
    }

    Long toSats(Double amount) {
        return (long) (amount * 100000000);
    }
}
