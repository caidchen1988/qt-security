/**
 * 
 */
package com.qtdbp.security.system;

import com.qtdbp.security.service.SysResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 类功能说明：通过数据库管理方法
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@Deprecated
public class MethodSecurityMetadataSource extends
		AbstractMethodSecurityMetadataSource implements InitializingBean{
	
	protected Log logger = LogFactory.getLog(getClass());
	
	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();
	
	private final static String RES_KEY = "resourcePath";
	private final static String AUTH_KEY = "authorityMark";
	
	private Map<MethodKey, Collection<ConfigAttribute>> requestMap;

	@Autowired
	private SysResourceService resourceService ;
	
	/**
	 * 根据方法获取到访问方法所需要的权限
	 * @param method 访问的方法
	 * @param targetClass 方法所属的类
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Method method,
			Class<?> targetClass) {
		MethodKey key = new MethodKey(method);
		Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;
		
		for (Map.Entry<MethodKey, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().equals(key)) {
            	attrs =  entry.getValue();
            	break;
            }
        }
		//logger.info("METHOD资源："+key.getFullMethodName()+ " -> " +attrs);
		
		return attrs;
	}

	/**
	 * 获取到所有方法对应的权限集合
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<MethodKey, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
	}

	/**
	 * 初始化方法权限对应集合，绑定方法权限集合
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.requestMap = this.bindRequestMap();
	}
	
	/**
	 * 从数据库中获取方法及权限对应信息
	 * @return
	 */
	private Map<String,String> loadMehod(){
		Map<String,String> resMap = new LinkedHashMap<String, String>();
		List<Map<String,String>> list = resourceService.getMethodResourceMapping();
		
		for(Map<String,String> map : list){
			String resourcePath = map.get(RES_KEY);
			String authorityMark = map.get(AUTH_KEY);
			
			if(resMap.containsKey(resourcePath)){
				String mark = resMap.get(resourcePath);
				resMap.put(resourcePath, mark+","+authorityMark);
			}else{
				resMap.put(resourcePath, authorityMark);
			}
		}
		
		return resMap;
	}
	
	/**
	 * 封装从数据库中获取的方法权限集合
	 * @return
	 */
	public Map<MethodKey, Collection<ConfigAttribute>> bindRequestMap(){
		Map<MethodKey, Collection<ConfigAttribute>> resMap = 
				new LinkedHashMap<MethodKey, Collection<ConfigAttribute>>();
		
		Map<String,String> map = this.loadMehod();
		for(Map.Entry<String, String> entry : map.entrySet()){
			MethodKey key = new MethodKey(entry.getKey());
			Collection<ConfigAttribute> atts = 
					SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
			
			resMap.put(key, atts);
		}
		
		return resMap;
	}

}
