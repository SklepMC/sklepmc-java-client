package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

public class ExecutionCommandInfo {

    private final String text;
    private final String target;

    @JsonCreator
    public ExecutionCommandInfo(@JsonProperty("text") String text, @JsonProperty("target") String target) {
        this.text = text;
        this.target = target;
    }

    @JsonProperty("text")
    public String getText() {
        return this.text;
    }

    @JsonProperty("target")
    public String getTarget() {
        return this.target;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExecutionCommandInfo.class.getSimpleName() + "[", "]")
                .add("text='" + this.text + "'")
                .add("target='" + this.target + "'")
                .toString();
    }
}
