/**
 * 
 */
package com.qtdbp.security.system;

import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@Deprecated
public class MethodKey {
	private String className;
	private String methodName;
	
	public MethodKey(){};
	
	public MethodKey(String fullName){
		this.className = StringUtils.stripFilenameExtension(fullName);
		this.methodName = StringUtils.getFilenameExtension(fullName);
	};
	
	public MethodKey(Method method) {
		super();
		this.className = method.getDeclaringClass().getName();
		this.methodName = method.getName();
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getFullMethodName(){
		return this.className + "." + this.methodName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MethodKey))return false;
		
		MethodKey target = (MethodKey)obj;
		
		if(this.className.equals(target.getClassName()) && 
				this.methodName.equals(target.getMethodName()))return true;
		
		return false;
	}
	
}
