package com.yn.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Whyn on 2017/9/5.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface InjectService {
    String name() default "";

    String label() default "";

    InjectIntentFilter intentFilter() default @InjectIntentFilter;
}
