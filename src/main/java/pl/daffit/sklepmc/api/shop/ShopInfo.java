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