package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

public class ServiceDescriptionInfo {

    private final String source;
    private final String compiled;

    @JsonCreator
    public ServiceDescriptionInfo(@JsonProperty("source") String source, @JsonProperty("compiled") String compiled) {
        this.source = source;
        this.compiled = compiled;
    }

    @JsonProperty("source")
    public String getSource() {
        return this.source;
    }

    @JsonProperty("compiled")
    public String getCompiled() {
        return this.compiled;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceDescriptionInfo.class.getSimpleName() + "[", "]")
                .add("source='" + this.source + "'")
                .add("compiled='" + this.compiled + "'")
                .toString();
    }
}
