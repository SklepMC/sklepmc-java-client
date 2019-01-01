package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.daffit.sklepmc.api.ApiContext;
import pl.daffit.sklepmc.api.ApiException;
import pl.daffit.sklepmc.api.ApiResource;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ServiceInfo {

    private final int id;
    private final String name;
    private final ServiceDescriptionInfo description;

    @JsonCreator
    public ServiceInfo(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("description") ServiceDescriptionInfo description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceInfo.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("name='" + this.name + "'")
                .add("description=" + this.description)
                .toString();
    }

    @JsonIgnore
    public static ServiceInfo get(ApiContext apiContext, int serviceId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serviceId", String.valueOf(serviceId));

        return ApiResource.get(apiContext, "/{shopId}/service/{serviceId}", ServiceInfo.class, params);
    }
}
