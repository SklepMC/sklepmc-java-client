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
package pl.sklepmc.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {

    private final String type;
    private final String message;

    @JsonCreator()
    public ApiError(@JsonProperty("type") String type, @JsonProperty("message") String message) {
        this.type = type;
        this.message = message;
    }

    @JsonProperty("error")
    public boolean isError() {
        return true;
    }

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiError.class.getSimpleName() + "[", "]")
                .add("type='" + this.type + "'")
                .add("message='" + this.message + "'")
                .toString();
    }

    public enum ErrorType {
        UNKNOWN,
        PARSE_ERROR,
        AUTHORIZATION_FAILED,
        ELEMENT_NOT_FOUND,
        INVALID_INPUT
    }
}
