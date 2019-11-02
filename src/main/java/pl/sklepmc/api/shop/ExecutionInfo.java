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
import com.fasterxml.jackson.annotation.JsonValue;
import pl.sklepmc.api.ShopContext;
import pl.sklepmc.api.ApiException;
import pl.sklepmc.api.ApiResource;

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
    public static ExecutionInfo get(ShopContext apiContext, int serverId) throws ApiException {

        Map<String, String> params = new HashMap<>();
        params.put("shopId", apiContext.getShopId());
        params.put("serverId", String.valueOf(serverId));

        return ApiResource.get(apiContext, "/{shopId}/server/{serverId}/awaitingExecution", ExecutionInfo.class, params);
    }
}
