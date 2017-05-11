/**
 * 
 */
package com.qtdbp.security.service;

import com.qtdbp.security.base.model.Message;
import com.qtdbp.security.model.SysRoles;

import java.util.Map;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2014-1-2 下午5:04:59
 * @version v1.0
 *
 */
public interface SecurityRoleService {

	public Message add(SysRoles entity);
	
	public Message update(SysRoles entity);
	
	public Message delete(SysRoles entity);
	
	public Message delete(String roleId);
	/**
	 * 根据Id获取角色实体
	 * @param roleId
	 * @return
	 */
	public Message getById(String roleId);
	/**
	 * 分页查询所有角色信息
	 * @param params
	 * @return
	 */
	public Message queryByPage(Parameters params);
	/**
	 * 分页查询所有有效的角色信息
	 * @param params
	 * @return
	 */
	public Message queryAllEnabled(Parameters params);
	/**
	 * 查询所有有效的角色信息
	 * @return
	 */
	public Message queryAllEnabled();
	/**
	 * 根据角色名查询所有已启用的角色
	 * @param roleName
	 * @param params
	 * @return
	 */
	public Message findByRoleNameLikeAndEnableed(String roleName, Parameters params)  throws Exception;
	/**
	 * 启用/禁用角色
	 * @param enable
	 * @return
	 */
	public Message enabled(String roleId, boolean enable);
	
	/**
	 * 根据roldId获取对应模块
	 * @param roleId
	 * @return
	 */
	public Message getModulesByRoleId(String roleId);
	
	
	public Message saveRoleModules(String roleId, String[] adds, String[] removes);
	
	public Message saveRoleAuthorities(String roleId, String[] sysAuthorities);
	
	public Message getLeafModulesByRoleId(String roleId);
	/**
	 * 根据roleId获取到可以分配的权限项
	 * @param roleId
	 * @return
	 */
	public Message getAuthorityByRoleId(String roleId);
	/**
	 * 根据roleId获取到当前角色已分配的所有权限
	 * @param roleId
	 * @return
	 */
	public Message queryByRoleId(String roleId);
	
	/**
	 * 根据角色名查找
	 * @param roleName
	 * @param params
	 * @return
	 */
	public Message findByRoleNameLike(String roleName, Parameters params)  throws Exception;
	
	public Message findByUserId(String userId) throws Exception;
	
	public Message findByUserId(boolean enable, String userid);
	
	public Message findByIssys();
	
	public Message saveModuleAndAuthority(String roleId, Map<?, ?> sysModules, String[] sysAuthorities);
	
}
