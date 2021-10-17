package com.hamrasta.trellis.ui.bread.annotaion;


import com.hamrasta.trellis.ui.bread.metadata.Bread;
import com.hamrasta.trellis.ui.bread.metadata.HttpMethodKind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BreadMethod {
    Bread kind();

    boolean disable() default false;

    String path() default "";

    HttpMethodKind method() default HttpMethodKind.DEFAULT;

}
