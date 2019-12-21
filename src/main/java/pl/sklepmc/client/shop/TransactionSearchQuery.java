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

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionSearchQuery {

    private final String status;
    private final String method;
    private final boolean requirePaid;
    private final String buyer;
    private final String giftTo;
    private final int serverId;
    private final String serverName;
    private final int serviceId;
    private final String serviceName;
    private final int limit;

    public TransactionSearchQuery(@JsonProperty("status") String status,
                                  @JsonProperty("method") String method,
                                  @JsonProperty("require_paid") boolean requirePaid,
                                  @JsonProperty("buyer") String buyer,
                                  @JsonProperty("gift_to") String giftTo,
                                  @JsonProperty("server_id") int serverId,
                                  @JsonProperty("server_name") String serverName,
                                  @JsonProperty("service_id") int serviceId,
                                  @JsonProperty("service_name") String serviceName,
                                  @JsonProperty("limit") int limit) {
        this.status = status;
        this.method = method;
        this.requirePaid = requirePaid;
        this.buyer = buyer;
        this.giftTo = giftTo;
        this.serverId = serverId;
        this.serverName = serverName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.limit = limit;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("method")
    public String getMethod() {
        return this.method;
    }

    @JsonProperty("require_paid")
    public boolean getRequirePaid() {
        return this.requirePaid;
    }

    @JsonProperty("buyer")
    public String getBuyer() {
        return this.buyer;
    }

    @JsonProperty("gift_to")
    public String getGiftTo() {
        return this.giftTo;
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

    @JsonProperty("limit")
    public int getLimit() {
        return this.limit;
    }
}
