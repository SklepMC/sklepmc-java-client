package pl.daffit.sklepmc.api;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ApiContext {

    private static final String DEFAULT_MAIN_URL = "https://www.sklepmc.pl/api";

    private final String shopId;
    private final String secret;

    private String mainUrl;
    private Client client;

    public ApiContext(String shopId, String secret) {
        this.shopId = shopId;
        this.secret = secret;
        this.client = ClientBuilder.newBuilder().register(JacksonJsonProvider.class).build();
        this.mainUrl = DEFAULT_MAIN_URL;
    }

    protected String getSecret() {
        return this.secret;
    }

    protected Client getClient() {
        return this.client;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getShopId() {
        return this.shopId;
    }
}
