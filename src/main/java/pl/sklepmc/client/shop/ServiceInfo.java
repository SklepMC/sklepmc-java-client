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
import pl.sklepmc.client.ShopContext;
import pl.sklepmc.client.ApiException;
import pl.sklepmc.client.ApiResource;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ServiceInfo {

    private final int id;
    private final String name;
    private final ServiceDescriptionInfo description;
    private final Map<String, Double> prices;

    @JsonCreator
    public ServiceInfo(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") ServiceDescriptionInfo description,
                       @JsonProperty("prices") Map<String, Double> prices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prices = prices;
    }

    @JsonProperty("id")
    public int getId() {
        return this.id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("description")
    public ServiceDescriptionInfo getDescription() {
        return this.description;
    }

    @JsonProperty("prices")
    public Map<String, Double> getPrices() {
        return this.prices;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceInfo.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("name='" + this.name + "'")
                .add("description=" + this.description)
                .add("prices=" + this.prices)
                .toString();
    }

    @JsonIgnore
    public static ServiceInfo get(ShopContext apiContext, int serviceId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serviceId", String.valueOf(serviceId));

        return ApiResource.get(apiContext, "/{shopId}/service/{serviceId}", ServiceInfo.class, params);
    }
}
