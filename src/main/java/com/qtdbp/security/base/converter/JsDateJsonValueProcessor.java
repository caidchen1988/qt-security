package com.qtdbp.security.base.converter;

import com.qtdbp.security.base.exception.DateFormatException;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.util.Date;

/**
 * 
 * @author caidchen
 * 
 * JSON转换时日期类型的格式化
 * 根据时分秒的值自动匹配转换格式
 */
public class JsDateJsonValueProcessor implements JsonValueProcessor {
	final String DATE_FORMAT = "yyyy-MM-dd";
	final String DATETIME_FORMAT = "yyyy-MM-dd";

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1){
		return this.process(arg0);
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2){
		return this.process(arg1);
	}
	
	@SuppressWarnings("deprecation")
	private Object process(Object value){
		//if(value instanceof Date){
		if(value == null)return value;
		Object rs = null;
		Date date = (Date)value;
		try {
			if(date.getMinutes()>0){
				rs = DateFormater.DATETIME_FORMAT.doFormat((Date)value);
			}else if(date.getHours()>0){
				rs = DateFormater.DATEHM_FORMAT.doFormat((Date)value);
			}else{
				rs = DateFormater.DATE_FORMAT.doFormat((Date)value);
			}
		} catch (DateFormatException e3) {
			rs = value.toString();
		}
		return rs;
    }
}
