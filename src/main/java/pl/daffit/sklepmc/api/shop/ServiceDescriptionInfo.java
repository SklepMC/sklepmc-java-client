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
