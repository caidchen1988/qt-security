/**
 * 
 */
package com.qtdbp.security.service.impl;

import com.zrhis.base.MessageFactory;
import com.zrhis.base.model.Message;
import com.zrhis.system.bean.SysModules;
import com.zrhis.system.dao.BaseDao;
import com.zrhis.system.repository.SysModulesRepository;
import com.zrhis.system.service.SecurityModuleService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-26 上午10:24:24
 * @version v1.0
 *
 */
@Service
public class SecurityModuleServiceImpl implements SecurityModuleService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String[] ORDERS = {"priority"};
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SysModulesRepository sysModulesRepository;
	
	@Autowired
	private MessageFactory messageFactory;
	
	@Autowired
	private MessageSource messageSource;
	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#add(com.zrhis.system.bean.SysModules)
	 */
	@Override
	public Message add(SysModules entity) throws Exception {
		this.baseDao.save(entity);
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setMessage(this.messageSource.getMessage("ddl.save.success", null, null));
		
		logger.info("\n添加模块:"+JSONObject.fromObject(entity));
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#update(com.zrhis.system.bean.SysModules)
	 */
	@Override
	public Message update(SysModules entity) throws Exception {
		this.baseDao.update(entity);
		Message msg = new Message(true,this.messageSource.getMessage("ddl.update.success", null, null));
		
		logger.info("\n更新模块:"+JSONObject.fromObject(entity));
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#delete(com.zrhis.system.bean.SysModules)
	 */
	@Override
	public Message delete(SysModules entity) throws Exception {
		this.baseDao.delete(entity);
		Message msg = new Message(true,this.messageSource.getMessage("ddl.delete.success", null, null));
		
		logger.info("\n删除模块:"+JSONObject.fromObject(entity));
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#delete(java.lang.String)
	 */
	@Override
	public Message delete(String entityId) throws Exception {
		this.baseDao.delete(SysModules.class, entityId);
		Message msg = new Message(true,this.messageSource.getMessage("ddl.delete.success", null, null));
		
		logger.info("\n删除ID为: "+entityId+" 的模块");
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#query()
	 */
	@Override
	public Message query(String nodeId) throws Exception {
		List<SysModules> list = null;
		if("root".equals(nodeId)){
			list = this.baseDao.query(SysModules.class, "ILevel", 1,ORDERS);
		}else{
			list = this.baseDao.query(SysModules.class, "parent", nodeId,ORDERS);
		}
		
		logger.info("\n查询模块:node="+nodeId);
		
		return new Message(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#enable(java.lang.String, boolean)
	 */
	@Override
	public Message enable(String moduleId, boolean enable) throws Exception {
		SysModules bean = (SysModules) this.baseDao.get(SysModules.class, moduleId);
		bean.setEnable(enable);
		String msgPropertyName = enable?"ddl.enabled":"ddl.disabled";
		Message msg = new Message(true,this.messageSource.getMessage(msgPropertyName, null, null));
		
		logger.info("\nID为："+moduleId+"的模块"+msg.getMessage());
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#queryAllLeafNode()
	 */
	@Override
	public Message queryAllLeafNode() throws Exception {
		List<SysModules> list = this.baseDao.query(SysModules.class, "leaf", true, ORDERS);
		Message msg = new Message(list);
		
		logger.info("\n查询模块所有子模块");
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#queryModulesByUserId(java.lang.String, java.lang.String)
	 */
	@Override
	public Message queryModulesByUserId(String userId, String parentId)
			throws Exception {
		List<SysModules> list = this.sysModulesRepository.getModulesByUserId(userId, parentId);
		return this.messageFactory.query(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityModuleService#queryByParentAndIssys(java.lang.String)
	 */
	@Override
	public Message queryByParentAndIssys(String parent) throws Exception {
		List<SysModules> list = this.sysModulesRepository.findByParentAndIssys(parent);
		return this.messageFactory.query(list);
	}

}
