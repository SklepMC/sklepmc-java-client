/*
 * SklepMC Java API
 * Copyright (C) 2019 SklepMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pl.daffit.sklepmc.api.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.StringJoiner;

public class ExecutionTaskInfo {

    private final String buyer;
    private final String transactionId;
    private final List<ExecutionCommandInfo> commands;
    private final boolean requireOnline;

    @JsonCreator
    public ExecutionTaskInfo(@JsonProperty("buyer") String buyer, @JsonProperty("transaction_id") String transactionId,
                             @JsonProperty("commands") List<ExecutionCommandInfo> commands, @JsonProperty("require_online") boolean requireOnline) {
        this.buyer = buyer;
        this.transactionId = transactionId;
        this.commands = commands;
        this.requireOnline = requireOnline;
    }

    @JsonProperty("buyer")
    public String getBuyer() {
        return this.buyer;
    }

    @JsonProperty("transaction_id")
    public String getTransactionId() {
        return this.transactionId;
    }

    @JsonProperty("commands")
    public List<ExecutionCommandInfo> getCommands() {
        return this.commands;
    }

    @JsonProperty("require_online")
    public boolean isRequireOnline() {
        return this.requireOnline;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExecutionTaskInfo.class.getSimpleName() + "[", "]")
                .add("buyer='" + this.buyer + "'")
                .add("transactionId='" + this.transactionId + "'")
                .add("commands=" + this.commands)
                .add("require_online=" + this.requireOnline)
                .toString();
    }
}
