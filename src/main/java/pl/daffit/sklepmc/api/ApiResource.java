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
package pl.daffit.sklepmc.api;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public final class ApiResource {

    public static <T> T get(ApiContext apiContext, String paty, Class<? extends T> clazz) {
        return get(apiContext, paty, clazz, null);
    }

    public static <T> T get(ApiContext apiContext, String path, Class<? extends T> clazz, Map<String, String> params) {
        return request(apiContext, path, RequestMethod.GET, clazz, params);
    }

    public static <T> T post(ApiContext apiContext, String path, Object body, Class<? extends T> clazz) {
        return post(apiContext, path, body, clazz, null);
    }

    public static <T> T post(ApiContext apiContext, String path, Object body, Class<? extends T> clazz, Map<String, String> params) {
        return request(apiContext, path, RequestMethod.POST, clazz, params, body);
    }

    public static <T> T request(ApiContext apiContext, String path, RequestMethod method, Class<? extends T> clazz, Map<String, String> params) {
        return request(apiContext, path, method, clazz, params, "");
    }

    public static <T> T request(ApiContext apiContext, String path, RequestMethod method, Class<? extends T> clazz, Map<String, String> params, Object body) {

        Client client = apiContext.getClient();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {

                if (entry.getValue() == null) {
                    ApiError apiError = new ApiError(ApiError.ErrorType.INVALID_INPUT.name(),
                            "Parameter " + entry.getKey() + " is null.");
                    throw new ApiException(apiError, "Failed to create request: " + apiError.getType() + ", " + apiError.getMessage());
                }

                path = replace(path, "{" + entry.getKey() + "}", entry.getValue());
            }
        }

        WebTarget target = client.target(apiContext.getMainUrl()).path(path);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON)
                .header("X-Api-Secret", apiContext.getSecret());
        Response response;

        if (method == RequestMethod.GET) {
            response = invocationBuilder.get();
        } else if (method == RequestMethod.POST) {
            response = invocationBuilder.post(Entity.entity(body, MediaType.APPLICATION_JSON));
        } else {
            ApiError apiError = new ApiError(ApiError.ErrorType.INVALID_INPUT.name(), "Request method " + method + " is not implemented.");
            throw new ApiException(apiError, "Failed to create request: " + apiError.getType() + ", " + apiError.getMessage());
        }

        if (response.getStatus() != 200) {
            try {
                ApiError apiError = response.readEntity(ApiError.class);
                throw new ApiException(apiError, "Failed to GET resource (" + target.getUri() + "). "
                        + "API returned " + response.getStatus() + " HTTP code "
                        + "with error: " + apiError.getType() + ", " + apiError.getMessage());
            } catch (ProcessingException | IllegalStateException exception) {
                ApiError apiError = new ApiError(ApiError.ErrorType.UNKNOWN.name(), "Unknown error: " + exception.getMessage());
                throw new ApiException(apiError, "Failed to GET resource (" + target.getUri() + "). "
                        + "API returned " + response.getStatus() + " HTTP code "
                        + "with error: " + apiError.getType() + ", " + apiError.getMessage(), exception);
            }
        }

        T entity;
        try {
            entity = response.readEntity(clazz);
        } catch (ProcessingException | IllegalStateException exception) {
            ApiError apiError = new ApiError(ApiError.ErrorType.PARSE_ERROR.name(), "Failed to convert JSON resource.");
            throw new ApiException(apiError, "Failed to convert resource JSON: " + target.getUri(), exception);
        }

        return entity;
    }

    private static String replace(String text, String searchString, String replacement) {
        if ((text == null) || (searchString == null) || (replacement == null)) {
            return text;
        }
        int max = -1;
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = ((increase < 0) ? 0 : increase);
        increase *= 16;
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text, start, end).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public enum RequestMethod {
        GET, POST
    }
}
