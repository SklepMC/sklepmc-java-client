package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.daffit.sklepmc.api.ApiContext;
import pl.daffit.sklepmc.api.ApiException;
import pl.daffit.sklepmc.api.ApiResource;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class TransactionInfo {

    private final String id;
    private final String buyer;
    private final double amount;
    private final String currency;
    private final String status;
    private final String method;
    private final long started;
    private final long expiresAfter;

    @JsonCreator
    public TransactionInfo(@JsonProperty("id") String id, @JsonProperty("buyer") String buyer, @JsonProperty("amount") double amount,
                           @JsonProperty("currency") String currency, @JsonProperty("status") String status, @JsonProperty("method") String method,
                           @JsonProperty("started") long started, @JsonProperty("expires_after") long expiresAfter) {
        this.id = id;
        this.buyer = buyer;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.method = method;
        this.started = started;
        this.expiresAfter = expiresAfter;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    @JsonProperty("buyer")
    public String getBuyer() {
        return this.buyer;
    }

    @JsonProperty("amount")
    public double getAmount() {
        return this.amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return this.currency;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("method")
    public String getMethod() {
        return this.method;
    }

    @JsonProperty("started")
    public long getStarted() {
        return this.started;
    }

    @JsonProperty("expires_after")
    public long getExpiresAfter() {
        return this.expiresAfter;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionInfo.class.getSimpleName() + "[", "]")
                .add("id='" + this.id + "'")
                .add("buyer='" + this.buyer + "'")
                .add("amount=" + this.amount)
                .add("currency='" + this.currency + "'")
                .add("status='" + this.status + "'")
                .add("method='" + this.method + "'")
                .add("started=" + this.started)
                .add("expiresAfter=" + this.expiresAfter)
                .toString();
    }

    @JsonIgnore
    public static TransactionInfo get(ApiContext apiContext, String transactionId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("transactionId", transactionId);

        return ApiResource.get(apiContext, "/{shopId}/transaction/{transactionId}", TransactionInfo.class, params);
    }

    @JsonIgnore
    public static boolean updateStatus(ApiContext apiContext, String transactionId, String newStatus) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("transactionId", transactionId);

        return "OK".equals(ApiResource.post(apiContext, "/{shopId}/transaction/{transactionId}/status", newStatus, String.class, params));
    }

    public enum TransactionStatus {
        STARTED,
        WAITING,
        PENDING,
        EXECUTION,
        COMPLETED,
        CANCELLED,
        EXPIRED
    }
}
