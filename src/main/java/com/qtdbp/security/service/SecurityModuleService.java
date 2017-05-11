/**
 * 
 */
package com.qtdbp.security.service;

import com.zrhis.base.model.Message;
import com.zrhis.system.bean.SysModules;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-26 上午10:23:12
 * @version v1.0
 *
 */
public interface SecurityModuleService {

	/**
	 * 添加模块
	 * @param entity 模块实体
	 * @return
	 * @throws Exception
	 */
	public Message add(SysModules entity) throws Exception;
	
	/**
	 * 更新模块
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Message update(SysModules entity) throws Exception;
	/**
	 * 删除模块
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Message delete(SysModules entity) throws Exception;
	/**
	 * 根据ID删除模块
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public Message delete(String entityId) throws Exception;
	/**
	 * 根据父节点查询模块
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public Message query(String node) throws Exception;
	/**
	 * 启用/禁用模块
	 * @param moduleId
	 * @param enable
	 * @return
	 * @throws Exception
	 */
	public Message enable(String moduleId, boolean enable) throws Exception;
	/**
	 * 获取所有子模块
	 * @return
	 * @throws Exception
	 */
	public Message queryAllLeafNode() throws Exception;
	/**
	 * 根据用户Id获取模块
	 * @param userId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Message queryModulesByUserId(String userId, String parentId) throws Exception;
	/**
	 * 
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	public Message queryByParentAndIssys(String parent) throws Exception;
}
