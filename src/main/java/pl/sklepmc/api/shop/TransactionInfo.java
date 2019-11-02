/*
 * SklepMC Java API
 * Copyright (C) 2019 SklepMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package pl.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.sklepmc.api.ShopContext;
import pl.sklepmc.api.ApiException;
import pl.sklepmc.api.ApiResource;

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
    public static TransactionInfo get(ShopContext apiContext, String transactionId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("transactionId", transactionId);

        return ApiResource.get(apiContext, "/{shopId}/transaction/{transactionId}", TransactionInfo.class, params);
    }

    @JsonIgnore
    public static boolean updateStatus(ShopContext apiContext, String transactionId, String newStatus) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("transactionId", transactionId);

        return "OK".equals(ApiResource.post(apiContext, "/{shopId}/transaction/{transactionId}/status", newStatus, String.class, params, true));
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
