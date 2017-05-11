package com.qtdbp.security.base.converter;

import com.qtdbp.security.base.exception.DateFormatException;
import com.qtdbp.security.utils.DebugUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caidchen
 * 
 * 日期的格式化
 */
public enum DateFormater implements SimpleDateFormater{
	DATE_FORMAT{
		@Override
		public String doFormat(Date date)  throws DateFormatException {
			try {
				DateFormat df = new SimpleDateFormat(DATE);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
	},
	
	DATETIME_FORMAT{
		@Override
		public String doFormat(Date date)  throws DateFormatException{
			try {
				DateFormat df = new SimpleDateFormat(DATE_TIME);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
		
	},
	
	DATEHM_FORMAT{
		@Override
		public String doFormat(Date date)  throws DateFormatException{
			try {
				DateFormat df = new SimpleDateFormat(DATE_HM);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
	},
	
	DATETIMEMS_FORMAT{
		@Override
		public String doFormat(Date date) throws DateFormatException{
			try {
				DateFormat df = new SimpleDateFormat(DATE_TIME_MS);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
		
	},
	
	CNDATE_FORMAT{
		@Override
		public String doFormat(Date date) throws DateFormatException{
			try {
				DateFormat df = new SimpleDateFormat(CN_DATE);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
	},

	CNDATETIME_FORMAT{
		@Override
		public String doFormat(Date date) throws DateFormatException{
			try {
				DateFormat df = new SimpleDateFormat(CN_DATE_TIME);
				return df.format(date);
			} catch (Exception e) {
				if(DebugUtil.IS_DEBUG)
				e.printStackTrace();
				throw new DateFormatException(e.getMessage());
			}
		}
	}
	
}
