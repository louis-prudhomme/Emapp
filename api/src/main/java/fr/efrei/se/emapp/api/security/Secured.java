package fr.efrei.se.emapp.api.security;

import fr.efrei.se.emapp.common.security.Role;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation made to mark the rest api methods
 * it is used to specify the authorized roles in a method
 * and bridges the gap between the method and the actual security filter
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
//might be used class-wide
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    /**
     * list of the authorized roles
     * @return authorized roles
     */
    Role[] value() default {};
}
