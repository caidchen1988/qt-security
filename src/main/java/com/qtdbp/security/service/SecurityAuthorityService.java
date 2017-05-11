/**
 * 
 */
package com.qtdbp.security.service;

import com.zrhis.base.model.Message;
import com.zrhis.base.model.Parameters;
import com.zrhis.system.bean.SysAuthorities;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-27 上午10:14:58
 * @version v1.0
 *
 */
public interface SecurityAuthorityService {
	/**
	 * 添加权限信息
	 * @param entity 权限实体类
	 * @return
	 * @throws Exception
	 */
	public Message add(SysAuthorities entity) throws Exception;
	
	/**
	 * 更新权限信息
	 * @param entity 权限实体
	 * @return
	 * @throws Exception
	 */
	public Message update(SysAuthorities entity) throws Exception;
	
	/**
	 * 删除权限信息
	 * @param entity 权限实体
	 * @return
	 * @throws Exception
	 */
	public Message delete(SysAuthorities entity) throws Exception;
	
	/**
	 * 根据ID删除权限信息
	 * @param entityId 权限ID
	 * @return
	 * @throws Exception
	 */
	public Message delete(String entityId) throws Exception;
	
	/**
	 * 禁用启用权限
	 * @param entityId 权限ID
	 * @param enable 是否启用
	 * @return
	 * @throws Exception
	 */
	public Message enable(String entityId, boolean enable) throws Exception;
	
	/**
	 * 查询权限信息
	 * @return
	 * @throws Exception
	 */
	public Message query(Parameters params) throws Exception;
	
	public Message queryByModuleId(String moduleId);

	/**
	 * @return
	 */
	public Message queryAll();
	
	public Message queryEnabled();
	
	
}
