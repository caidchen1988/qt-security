package com.qtdbp.security.base.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author caidchen
 * 
 * 处理前台String类型的日期到Date日期类型的转换
 * Spring 配置中使用
 */
public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		Date date = null;// = new Date();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format3 = new SimpleDateFormat("yyyyMMdd");
		if(!"".equals(source)){
			try {
				date = format1.parse(source);
			} catch (ParseException e) {
				try {
					date = format2.parse(source);
				} catch (ParseException e1) {
					try {
						date = format3.parse(source);
					} catch (ParseException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		return date;
	}

}
