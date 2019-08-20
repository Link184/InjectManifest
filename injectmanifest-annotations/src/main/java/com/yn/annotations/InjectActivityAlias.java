package com.yn.annotations;

import com.yn.annotations.enums.Correct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface InjectActivityAlias {
    String name();
    String targetActivity();
    Correct enabled() default Correct.NONE;
    Correct exported() default Correct.NONE;
    String label() default "";
//    String permission() default "";
}
