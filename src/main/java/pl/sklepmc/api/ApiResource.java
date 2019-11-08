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
package pl.sklepmc.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public abstract class ApiResource {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    public static <T> T get(ShopContext apiContext, String paty, Class<? extends T> clazz) {
        return get(apiContext, paty, clazz, null);
    }

    public static <T> T get(ShopContext apiContext, String path, Class<? extends T> clazz, Map<String, String> params) {
        return request(apiContext, path, RequestMethod.GET, clazz, params);
    }

    public static <T> T post(ShopContext apiContext, String path, Object body, Class<? extends T> clazz) {
        return post(apiContext, path, body, clazz, null);
    }

    public static <T> T post(ShopContext apiContext, String path, Object body, Class<? extends T> clazz,
                             Map<String, String> params) {
        return request(apiContext, path, RequestMethod.POST, clazz, params, body);
    }

    public static <T> T post(ShopContext apiContext, String path, Object body, Class<? extends T> clazz,
                             Map<String, String> params, boolean raw) {
        return request(apiContext, path, RequestMethod.POST, clazz, params, body, raw, raw);
    }

    public static <T> T post(ShopContext apiContext, String path, Object body, Class<? extends T> clazz,
                             Map<String, String> params, boolean rawInput, boolean rawOutput) {
        return request(apiContext, path, RequestMethod.POST, clazz, params, body, rawInput, rawOutput);
    }

    public static <T> T request(ShopContext apiContext, String path, RequestMethod method, Class<? extends T> clazz,
                                Map<String, String> params) {
        return request(apiContext, path, method, clazz, params, "");
    }

    public static <T> T request(ShopContext apiContext, String path, RequestMethod method, Class<? extends T> clazz,
                                Map<String, String> params, Object body) {
        return request(apiContext, path, method, clazz, params, body, false, false);
    }

    public static <T> T request(ShopContext apiContext, String path, RequestMethod method, Class<? extends T> clazz,
                                Map<String, String> params, Object body, boolean rawInput, boolean rawOutput) {

        OkHttpClient client = apiContext.getClient();

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

        String targetUri = apiContext.getMainUrl() + path;
        Request.Builder requestBuilder = new Request.Builder()
                .header("X-Api-Secret", apiContext.getSecret())
                .url(targetUri);
        Request request;

        if (method == RequestMethod.GET) {
            request = requestBuilder
                    .get()
                    .build();
        } else if (method == RequestMethod.POST) {

            String jsonBody;
            try {
                if (rawInput) {
                    jsonBody = String.valueOf(body);
                } else {
                    jsonBody = OBJECT_MAPPER.writeValueAsString(body);
                }
            } catch (JsonProcessingException exception) {
                ApiError apiError = new ApiError(ApiError.ErrorType.INVALID_INPUT.name(), "Failed to parse request body: " + exception.getMessage());
                throw new ApiException(apiError, "Failed to create request: " + apiError.getType() + ", " + apiError.getMessage());
            }

            request = requestBuilder
                    .post(RequestBody.create(MEDIA_TYPE_JSON, jsonBody))
                    .build();
        } else {
            ApiError apiError = new ApiError(ApiError.ErrorType.INVALID_INPUT.name(), "Request method " + method + " is not implemented.");
            throw new ApiException(apiError, "Failed to create request: " + apiError.getType() + ", " + apiError.getMessage());
        }

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                try {
                    ApiError apiError = OBJECT_MAPPER.readValue(readBodyBytes(response), ApiError.class);
                    throw new ApiException(apiError, "Failed to GET resource (" + targetUri + "). "
                            + "API returned " + response.code() + " HTTP code "
                            + "with error: " + apiError.getType() + ", " + apiError.getMessage());
                } catch (JsonParseException | JsonMappingException exception) {
                    ApiError apiError = new ApiError(ApiError.ErrorType.UNKNOWN.name(), "Unknown error: " + exception.getMessage());
                    throw new ApiException(apiError, "Failed to GET resource (" + targetUri + "). "
                            + "API returned " + response.code() + " HTTP code "
                            + "with error: " + apiError.getType() + ", " + apiError.getMessage(), exception);
                }
            }
            if (rawOutput) {
                //noinspection unchecked
                return (T) readBodyString(response);
            } else {
                return OBJECT_MAPPER.readValue(readBodyBytes(response), clazz);
            }
        } catch (JsonParseException | JsonMappingException exception) {
            ApiError apiError = new ApiError(ApiError.ErrorType.PARSE_ERROR.name(), exception.getMessage());
            throw new ApiException(apiError, "Failed to GET resource (" + targetUri + "). Failed to parse response: "
                    + apiError.getType() + ", " + apiError.getMessage(), exception);
        } catch (IOException exception) {
            ApiError apiError = new ApiError(ApiError.ErrorType.UNKNOWN.name(), exception.getMessage());
            throw new ApiException(apiError, "Failed to GET resource (" + targetUri + "). IOException: "
                    + apiError.getType() + ", " + apiError.getMessage(), exception);
        }
    }

    private static String readBodyString(Response response) throws IOException {
        return (response.body() == null) ? null : response.body().string();
    }

    private static byte[] readBodyBytes(Response response) throws IOException {
        return (response.body() == null) ? null : response.body().bytes();
    }

    public static String replace(String text, String searchString, String replacement) {
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
