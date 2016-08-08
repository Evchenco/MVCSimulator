package com.simulator.annotation.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Requirements of use:
 * * value - indicate mapped url must start with letter. 
 * * method in param Model
 * * method must return String - jsp page name.
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

	String value();

}
