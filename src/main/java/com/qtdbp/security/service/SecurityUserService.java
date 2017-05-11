/**
 * 
 */
package com.qtdbp.security.service;

import com.zrhis.base.model.Message;
import com.zrhis.base.model.Parameters;
import com.zrhis.system.bean.SysUsers;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2014-1-13 上午9:34:54
 * @version v1.0
 *
 */
public interface SecurityUserService {
	/**
	 * 添加用户
	 * @param user 用户实体
	 * @return
	 */
	public Message add(SysUsers user);
	
	/**
	 * 更新用户,仅可以更新用户名、姓名、是否可用三个字段，其他字段不可通过这个方法更新
	 * @param user 用户实体
	 * @return
	 */
	public Message update(SysUsers user);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public Message modifyPassword(String userId, String oldPassword, String password);
	
	/**
	 * 删除用户
	 * @param userId 用户ID
	 * @return
	 */
	public Message delete(String userId);
	
	/**
	 * 查询用户
	 * @param username 用户名
	 * @param params 参数集合
	 * @return
	 */
	public Message query(String username, Parameters params);
	
	/**
	 * 根据用户Id查找权限
	 * @param userId
	 * @return
	 */
	public Message findRoleByUserId(String userId);
	
	/**
	 * 为用户分配权限
	 * @param usreId
	 * @param roles
	 * @return
	 */
	public Message assignRoles(String userId, String[] roles);
	
	/**
	 * 根据机构ID查询用户
	 * @param jgid
	 * @return
	 */
	public Message queryByJgid(String jgid, Parameters param);
	
}
