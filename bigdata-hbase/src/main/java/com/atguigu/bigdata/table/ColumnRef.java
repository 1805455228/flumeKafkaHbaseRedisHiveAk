package com.atguigu.bigdata.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zxfcode
 * @create 2018-10-24 20:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnRef {
    String columnFamily() default "info";
    String column() default "";
}
