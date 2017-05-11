package com.qtdbp.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface MapType {
	public static final String TAG = "tag";
	public static final String PROPERTY = "property";
	public static final String TEXT = "text";
	
	public String keyType() default MapType.TAG;
	public String valueType() default MapType.TEXT;

}
