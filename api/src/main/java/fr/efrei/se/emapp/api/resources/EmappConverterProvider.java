package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * the {@link ParamConverterProvider} for {@link EmployeeTranscript} and {@link fr.efrei.se.emapp.common.model.CredentialTranscript} classes
 */
@Provider
public class EmappConverterProvider implements ParamConverterProvider {
    /**
     * Enables one to obtain an {@link EmployeeConverter} to convert {@link String} to {@link EmployeeTranscript}
     * Enables one to obtain an {@link CredentialConverter} to convert {@link String} to {@link CredentialTranscript}
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
        } else if(type == CredentialTranscript.class) {
            return (ParamConverter<T>)new CredentialConverter();
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

    /**
     * static class which effectively converts {@link String} json to {@link CredentialTranscript}
     */
    private static class CredentialConverter implements ParamConverter<CredentialTranscript> {
        Gson cypher = new Gson();
        @Override
        public CredentialTranscript fromString(String s) {
            return cypher.fromJson(s, CredentialTranscript.class);
        }

        @Override
        public String toString(CredentialTranscript t) {
            return cypher.toJson(t);
        }
    }
}
