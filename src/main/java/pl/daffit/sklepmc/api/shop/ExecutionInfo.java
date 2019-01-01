package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import pl.daffit.sklepmc.api.ApiContext;
import pl.daffit.sklepmc.api.ApiException;
import pl.daffit.sklepmc.api.ApiResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ExecutionInfo {

    private final List<ExecutionTaskInfo> executionTasks;

    @JsonCreator
    public ExecutionInfo(List<ExecutionTaskInfo> executionTasks) {
        this.executionTasks = executionTasks;
    }

    @JsonValue
    public List<ExecutionTaskInfo> getExecutionTasks() {
        return this.executionTasks;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExecutionInfo.class.getSimpleName() + "[", "]")
                .add("executionTasks=" + this.executionTasks)
                .toString();
    }

    @JsonIgnore
    public static ExecutionInfo get(ApiContext apiContext, int serverId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serverId", String.valueOf(serverId));

        return ApiResource.get(apiContext, "/{shopId}/server/{serverId}/awaitingExecution", ExecutionInfo.class, params);
    }
}
