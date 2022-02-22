package de.conciso.mockitodemo.org.mockito;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value=TYPE)
@Retention(value=RUNTIME)
public @interface DoNotMock{}