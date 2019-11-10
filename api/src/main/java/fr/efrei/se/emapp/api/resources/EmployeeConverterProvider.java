package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class EmployeeConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if (type == EmployeeTranscript.class) {
            return (ParamConverter<T>)new EmployeeConverter();
        } else {
            return null;
        }
    }

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
