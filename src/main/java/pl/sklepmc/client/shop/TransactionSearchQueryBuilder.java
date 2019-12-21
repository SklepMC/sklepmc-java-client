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

public class TransactionSearchQueryBuilder {

    private String status = null;
    private String method = null;
    private boolean requirePaid = true;
    private String buyer = null;
    private String giftTo = null;
    private int serverId = -1;
    private String serverName = null;
    private int serviceId = -1;
    private String serviceName = null;
    private int limit = 10_000;

    public TransactionSearchQueryBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public TransactionSearchQueryBuilder withMethod(String method) {
        this.method = method;
        return this;
    }

    public TransactionSearchQueryBuilder withRequirePaid(boolean requirePaid) {
        this.requirePaid = requirePaid;
        return this;
    }

    public TransactionSearchQueryBuilder withBuyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public TransactionSearchQueryBuilder withGifTTo(String giftTo) {
        this.giftTo = giftTo;
        return this;
    }

    public TransactionSearchQueryBuilder withServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public TransactionSearchQueryBuilder withServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public TransactionSearchQueryBuilder withServiceId(int serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public TransactionSearchQueryBuilder withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public TransactionSearchQueryBuilder withLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public TransactionSearchQuery createTransactionSearchQuery() {
        return new TransactionSearchQuery(this.status, this.method, this.requirePaid, this.buyer, this.giftTo, this.serverId, this.serverName, this.serviceId, this.serviceName, this.limit);
    }
}