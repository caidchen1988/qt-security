package com.qtdbp.security.base.converter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 qtbigdata.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@SuppressWarnings({"serial"})
public class CustomsPropertyNamingStrategry extends PropertyNamingStrategy {

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field,
			String defaultName) {
		JsonProperty property = field.getAnnotation(JsonProperty.class);
		if(property == null){
			return field.getName();
		}else{
			return property.value();
		}
	}

	@Override
	public String nameForGetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		
		return this.methodToName(method);
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		
		return this.methodToName(method);
	}

	private String methodToName(AnnotatedMethod method){
		JsonProperty property = method.getAnnotation(JsonProperty.class);
		if(property == null){
			String name = method.getName().replace("get", "").replace("set", "");
			char[] arr = name.toCharArray();
			if(Character.isLowerCase(arr[1])){
				arr[0] = Character.toLowerCase(arr[0]);
			}
			return new String(arr);
		}else{
			return property.value();
		}
	}
	
	/*@Test
	public void test(){
		String str = "DtIame";
		char[] arr = str.toCharArray();
		if(Character.isLowerCase(arr[1])){
			arr[0] = Character.toLowerCase(arr[0]);
		}
		System.out.println(new String(arr));
		VName   vname
	}*/
	
}
