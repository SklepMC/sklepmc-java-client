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
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class TransactionSearchInfo {

    private final int transactionsCount;
    private final double transactionsValue;
    private final List<TransactionInfo> transactions;

    @JsonCreator
    public TransactionSearchInfo(@JsonProperty("transactions_count") int transactionsCount,
                                 @JsonProperty("transactions_value") double transactionsValue,
                                 @JsonProperty("transactions") List<TransactionInfo> transactions) {
        this.transactionsCount = transactionsCount;
        this.transactionsValue = transactionsValue;
        this.transactions = transactions;
    }

    @JsonProperty("transactions_count")
    public int getTransactionsCount() {
        return this.transactionsCount;
    }

    @JsonProperty("transactions_value")
    public double getTransactionValue() {
        return this.transactionsValue;
    }

    @JsonProperty("transactions")
    public List<TransactionInfo> getTransactions() {
        return this.transactions;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionSearchInfo.class.getSimpleName() + "[", "]")
                .add("transactionsCount=" + this.transactionsCount)
                .add("transactionsValue=" + this.transactionsValue)
                .add("transactions=" + this.transactions)
                .toString();
    }

    @JsonIgnore
    public static TransactionSearchInfo get(ShopContext apiContext, TransactionSearchQuery query) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());

        return ApiResource.post(apiContext, "/{shopId}/transaction/search", query, TransactionSearchInfo.class, params);
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
