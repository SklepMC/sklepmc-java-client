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
package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.daffit.sklepmc.api.ApiContext;
import pl.daffit.sklepmc.api.ApiException;
import pl.daffit.sklepmc.api.ApiResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ShopInfo {

    private final String id;
    private final String name;
    private final List<BasicServerInfo> servers;

    @JsonCreator
    public ShopInfo(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("servers") List<BasicServerInfo> servers) {
        this.id = id;
        this.name = name;
        this.servers = servers;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("servers")
    public List<BasicServerInfo> getServers() {
        return this.servers;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ShopInfo.class.getSimpleName() + "[", "]")
                .add("id='" + this.id + "'")
                .add("name='" + this.name + "'")
                .add("servers=" + this.servers)
                .toString();
    }

    @JsonIgnore
    public static ShopInfo get(ApiContext apiContext) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());

        return ApiResource.get(apiContext, "/{shopId}", ShopInfo.class, params);
    }
}