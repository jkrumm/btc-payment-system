package com.jkrumm.btcpay.btc.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    {
        "id",
        "currency",
        "symbol",
        "name",
        "logo_url",
        "status",
        "price",
        "price_date",
        "price_timestamp",
        "circulating_supply",
        "max_supply",
        "market_cap",
        "num_exchanges",
        "num_pairs",
        "num_pairs_unmapped",
        "first_candle",
        "first_trade",
        "first_order_book",
        "rank",
        "rank_delta",
        "high",
        "high_timestamp",
    }
)
public class BtcDataDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("status")
    private String status;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_date")
    private String priceDate;

    @JsonProperty("price_timestamp")
    private String priceTimestamp;

    @JsonProperty("circulating_supply")
    private String circulatingSupply;

    @JsonProperty("max_supply")
    private String maxSupply;

    @JsonProperty("market_cap")
    private String marketCap;

    @JsonProperty("num_exchanges")
    private String numExchanges;

    @JsonProperty("num_pairs")
    private String numPairs;

    @JsonProperty("num_pairs_unmapped")
    private String numPairsUnmapped;

    @JsonProperty("first_candle")
    private String firstCandle;

    @JsonProperty("first_trade")
    private String firstTrade;

    @JsonProperty("first_order_book")
    private String firstOrderBook;

    @JsonProperty("rank")
    private String rank;

    @JsonProperty("rank_delta")
    private String rankDelta;

    @JsonProperty("high")
    private String high;

    @JsonProperty("high_timestamp")
    private String highTimestamp;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("price_date")
    public String getPriceDate() {
        return priceDate;
    }

    @JsonProperty("price_date")
    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    @JsonProperty("price_timestamp")
    public String getPriceTimestamp() {
        return priceTimestamp;
    }

    @JsonProperty("price_timestamp")
    public void setPriceTimestamp(String priceTimestamp) {
        this.priceTimestamp = priceTimestamp;
    }

    @JsonProperty("circulating_supply")
    public String getCirculatingSupply() {
        return circulatingSupply;
    }

    @JsonProperty("circulating_supply")
    public void setCirculatingSupply(String circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    @JsonProperty("max_supply")
    public String getMaxSupply() {
        return maxSupply;
    }

    @JsonProperty("max_supply")
    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    @JsonProperty("market_cap")
    public String getMarketCap() {
        return marketCap;
    }

    @JsonProperty("market_cap")
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    @JsonProperty("num_exchanges")
    public String getNumExchanges() {
        return numExchanges;
    }

    @JsonProperty("num_exchanges")
    public void setNumExchanges(String numExchanges) {
        this.numExchanges = numExchanges;
    }

    @JsonProperty("num_pairs")
    public String getNumPairs() {
        return numPairs;
    }

    @JsonProperty("num_pairs")
    public void setNumPairs(String numPairs) {
        this.numPairs = numPairs;
    }

    @JsonProperty("num_pairs_unmapped")
    public String getNumPairsUnmapped() {
        return numPairsUnmapped;
    }

    @JsonProperty("num_pairs_unmapped")
    public void setNumPairsUnmapped(String numPairsUnmapped) {
        this.numPairsUnmapped = numPairsUnmapped;
    }

    @JsonProperty("first_candle")
    public String getFirstCandle() {
        return firstCandle;
    }

    @JsonProperty("first_candle")
    public void setFirstCandle(String firstCandle) {
        this.firstCandle = firstCandle;
    }

    @JsonProperty("first_trade")
    public String getFirstTrade() {
        return firstTrade;
    }

    @JsonProperty("first_trade")
    public void setFirstTrade(String firstTrade) {
        this.firstTrade = firstTrade;
    }

    @JsonProperty("first_order_book")
    public String getFirstOrderBook() {
        return firstOrderBook;
    }

    @JsonProperty("first_order_book")
    public void setFirstOrderBook(String firstOrderBook) {
        this.firstOrderBook = firstOrderBook;
    }

    @JsonProperty("rank")
    public String getRank() {
        return rank;
    }

    @JsonProperty("rank")
    public void setRank(String rank) {
        this.rank = rank;
    }

    @JsonProperty("rank_delta")
    public String getRankDelta() {
        return rankDelta;
    }

    @JsonProperty("rank_delta")
    public void setRankDelta(String rankDelta) {
        this.rankDelta = rankDelta;
    }

    @JsonProperty("high")
    public String getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(String high) {
        this.high = high;
    }

    @JsonProperty("high_timestamp")
    public String getHighTimestamp() {
        return highTimestamp;
    }

    @JsonProperty("high_timestamp")
    public void setHighTimestamp(String highTimestamp) {
        this.highTimestamp = highTimestamp;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
