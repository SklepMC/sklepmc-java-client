/*
 * SklepMC Java Client
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
package pl.sklepmc.client.shop;

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
