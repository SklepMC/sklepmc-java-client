package pl.daffit.sklepmc.api;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public final class ApiResource {

    public static <T> T get(ApiContext apiContext, String paty, Class<? extends T> clazz) {
        return get(apiContext, paty, clazz, null);
    }

    public static <T> T get(ApiContext apiContext, String path, Class<? extends T> clazz, Map<String, String> params) {

        Client client = apiContext.getClient();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                path = replace(path, "{" + entry.getKey() + "}", entry.getValue());
            }
        }

        WebTarget target = client.target(apiContext.getMainUrl()).path(path);
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("X-Api-Secret", apiContext.getSecret())
                .get();

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

    public static <T> T post(ApiContext apiContext, String path, Object body, Class<? extends T> clazz) {
        return post(apiContext, path, body, clazz, null);
    }

    public static <T> T post(ApiContext apiContext, String path, Object body, Class<? extends T> clazz, Map<String, String> params) {

        Client client = apiContext.getClient();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                path = replace(path, "{" + entry.getKey() + "}", entry.getValue());
            }
        }

        WebTarget target = client.target(apiContext.getMainUrl()).path(path);
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("X-Api-Secret", apiContext.getSecret())
                .post(Entity.entity(body, MediaType.APPLICATION_JSON));

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
}
