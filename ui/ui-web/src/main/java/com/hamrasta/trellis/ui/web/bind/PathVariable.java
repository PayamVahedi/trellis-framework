package com.hamrasta.trellis.ui.web.bind;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathVariable {

    String value() default "";

}
