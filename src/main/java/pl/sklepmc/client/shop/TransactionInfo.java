/*
 * SklepMC Java Client
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
package pl.sklepmc.client.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.sklepmc.client.ApiException;
import pl.sklepmc.client.ApiResource;
import pl.sklepmc.client.ShopContext;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class TransactionInfo {

    private final String id;
    private final String buyer;
    private final String giftTo;
    private final double amount;
    private final String currency;
    private final String status;
    private final String method;
    private final long timeStarted;
    private final long timePaid;
    private final long timeExecuted;
    private final long expiresAfter;
    private final int serverId;
    private final String serverName;
    private final int serviceId;
    private final String serviceName;
    private final boolean paid;
    private final boolean executed;

    @JsonCreator
    public TransactionInfo(@JsonProperty("id") String id,
                           @JsonProperty("buyer") String buyer,
                           @JsonProperty("gift_to") String giftTo,
                           @JsonProperty("amount") double amount,
                           @JsonProperty("currency") String currency,
                           @JsonProperty("status") String status,
                           @JsonProperty("method") String method,
                           @JsonProperty("time_started") long timeStarted,
                           @JsonProperty("time_paid") long timePaid,
                           @JsonProperty("time_executed") long timeExecuted,
                           @JsonProperty("expires_after") long expiresAfter,
                           @JsonProperty("server_id") int serverId,
                           @JsonProperty("server_name") String serverName,
                           @JsonProperty("service_id") int serviceId,
                           @JsonProperty("service_name") String serviceName,
                           @JsonProperty("paid") boolean paid,
                           @JsonProperty("executed") boolean executed) {
        this.id = id;
        this.buyer = buyer;
        this.giftTo = giftTo;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.method = method;
        this.timeStarted = timeStarted;
        this.timePaid = timePaid;
        this.timeExecuted = timeExecuted;
        this.expiresAfter = expiresAfter;
        this.serverId = serverId;
        this.serverName = serverName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.paid = paid;
        this.executed = executed;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    @JsonProperty("buyer")
    public String getBuyer() {
        return this.buyer;
    }

    @JsonProperty("gift_to")
    public String getGiftTo() {
        return this.giftTo;
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

    @JsonProperty("time_started")
    public long getTimeStarted() {
        return this.timeStarted;
    }

    @JsonProperty("time_paid")
    public long getTimePaid() {
        return this.timePaid;
    }

    @JsonProperty("time_executed")
    public long getTimeExecuted() {
        return this.timeExecuted;
    }

    @JsonProperty("expires_after")
    public long getExpiresAfter() {
        return this.expiresAfter;
    }

    @JsonProperty("server_id")
    public int getServerId() {
        return this.serverId;
    }

    @JsonProperty("server_name")
    public String getServerName() {
        return this.serverName;
    }

    @JsonProperty("service_id")
    public int getServiceId() {
        return this.serviceId;
    }

    @JsonProperty("service_name")
    public String getServiceName() {
        return this.serviceName;
    }

    @JsonProperty("paid")
    public boolean isPaid() {
        return this.paid;
    }

    @JsonProperty("executed")
    public boolean isExecuted() {
        return this.executed;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionInfo.class.getSimpleName() + "[", "]")
                .add("id='" + this.id + "'")
                .add("buyer='" + this.buyer + "'")
                .add("giftTo='" + this.giftTo + "'")
                .add("amount=" + this.amount)
                .add("currency='" + this.currency + "'")
                .add("status='" + this.status + "'")
                .add("method='" + this.method + "'")
                .add("timeStarted=" + this.timeStarted)
                .add("timePaid=" + this.timePaid)
                .add("timeExecuted=" + this.timeExecuted)
                .add("expiresAfter=" + this.expiresAfter)
                .add("serverId=" + this.serverId)
                .add("serverName='" + this.serverName + "'")
                .add("serviceId=" + this.serviceId)
                .add("serviceName='" + this.serviceName + "'")
                .add("paid=" + this.paid)
                .add("executed=" + this.executed)
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
