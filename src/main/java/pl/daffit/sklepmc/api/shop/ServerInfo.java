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
    public static ServerInfo get(ApiContext apiContext, int serverId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serverId", String.valueOf(serverId));

        return ApiResource.get(apiContext, "/{shopId}/server/{serverId}", ServerInfo.class, params);
    }
}
