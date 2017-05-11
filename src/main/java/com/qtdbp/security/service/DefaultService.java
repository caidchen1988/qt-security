/**
 * 
 */
package com.qtdbp.security.service;

import com.qtdbp.security.base.Message;

import javax.servlet.http.HttpServletRequest;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@SuppressWarnings("rawtypes")
public interface DefaultService {

	public Message getEntity(String className, String id) throws Exception;
	public Message savaEntity(HttpServletRequest request, Parameters params) throws Exception;
	public Message savaEntity(Object obj) throws Exception;
	public Message updateEntity(HttpServletRequest request, Parameters params) throws Exception;
	public Message updateEntityEx(HttpServletRequest request, Parameters params) throws Exception;
	public Message deleteEntity(String className, String id) throws Exception;
	public Message query(HttpServletRequest request, Parameters params) throws Exception;
	public Message fuzzyQueryAll(HttpServletRequest request, Parameters params) throws Exception ;
	public Message getDepTreeNode(HttpServletRequest request, String id) throws Exception;
	public Message getEntity(Class clz, String id) throws Exception;
	public Message queryAll(HttpServletRequest request, Parameters params) throws Exception;
	
}
