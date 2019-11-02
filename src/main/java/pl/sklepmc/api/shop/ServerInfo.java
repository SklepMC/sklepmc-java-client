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
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ServerInfo {

    private final int id;
    private final String name;
    private final List<ServiceInfo> services;

    @JsonCreator
    public ServerInfo(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("services") List<ServiceInfo> services) {
        this.id = id;
        this.name = name;
        this.services = services;
    }

    @JsonProperty("id")
    public int getId() {
        return this.id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("services")
    public List<ServiceInfo> getServices() {
        return this.services;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServerInfo.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("name='" + this.name + "'")
                .add("services=" + this.services)
                .toString();
    }

    @JsonIgnore
    public static ServerInfo get(ShopContext apiContext, int serverId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serverId", String.valueOf(serverId));

        return ApiResource.get(apiContext, "/{shopId}/server/{serverId}", ServerInfo.class, params);
    }
}
