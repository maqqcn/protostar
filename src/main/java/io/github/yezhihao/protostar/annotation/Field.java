package io.github.yezhihao.protostar.annotation;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.converter.Converter;

import java.lang.annotation.*;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Repeatable(Fs.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    int index() default -1;

    int length() default -1;

    int lengthSize() default -1;

    DataType type() default DataType.BYTE;

    String charset() default "GBK";

    byte pad() default 0;

    String desc() default "";

    int[] version() default {-1, 0, 1};

    Class<? extends Converter> converter() default Converter.class;
}