package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * the {@link ParamConverterProvider} for {@link EmployeeTranscript} class
 */
@Provider
public class EmployeeConverterProvider implements ParamConverterProvider {
    /**
     * Enables one to obtain an {@link EmployeeConverter} to convert {@link String} to {@link EmployeeTranscript}
     * @param aClass raw type of the parameter provided, should be {@link String}
     * @param type to which the conversion should be executed
     * @param annotations annotations of the method’s context
     * @param <T> genericity provided by parent method
     * @return if eveyrthing concords, a {@link EmployeeConverter}
     */
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if (type == EmployeeTranscript.class) {
            return (ParamConverter<T>)new EmployeeConverter();
        } else {
            return null;
        }
    }

    /**
     * static class which effectively converts {@link String} json to {@link EmployeeTranscript}
     */
    private static class EmployeeConverter implements ParamConverter<EmployeeTranscript> {
        Gson cypher = new Gson();
        @Override
        public EmployeeTranscript fromString(String s) {
            return cypher.fromJson(s, EmployeeTranscript.class);
        }

        @Override
        public String toString(EmployeeTranscript t) {
            return cypher.toJson(t);
        }
    }
}
