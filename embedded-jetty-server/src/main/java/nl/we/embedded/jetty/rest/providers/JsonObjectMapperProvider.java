package nl.we.embedded.jetty.rest.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.util.Json;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author wilelb
 */
@Provider
public class JsonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    public JsonObjectMapperProvider() {
        //Json.mapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return Json.mapper();
    }
}