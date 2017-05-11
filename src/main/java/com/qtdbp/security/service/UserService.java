/**
 * 
 */
package com.qtdbp.security.service;

import com.zrhis.base.model.Message;
import com.zrhis.system.bean.SysUser;

/**
 * 类功能说明：用户管理
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-13 下午1:34:20
 * @version v1.0
 *
 */
public interface UserService {
	
	/**
	 * 添加用户、在添加时判断用户是否已存在
	 * @param user
	 * @return
	 */
	public Message add(SysUser user) throws Exception;
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public Message delete(SysUser user) throws Exception;
	
	/**
	 * 根据Id删除用户
	 * @param userId
	 * @return
	 */
	public Message delete(String userId) throws Exception;
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public Message update(SysUser user) throws Exception;
	
	/**
	 * 重置用户密码
	 * @param user
	 * @return
	 */
	public Message resetPassword(SysUser user) throws Exception;
	
	/**
	 * 根据用户Id重置用户密码
	 * @param userId
	 * @return
	 */
	public Message resetPassword(String userId) throws Exception;
	
	/**
	 * 根据用户名检查重复
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean checkRepeat(String username) throws Exception;
}
