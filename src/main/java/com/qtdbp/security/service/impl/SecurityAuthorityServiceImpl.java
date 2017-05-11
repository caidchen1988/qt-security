/**
 * 
 */
package com.qtdbp.security.service.impl;

import com.zrhis.base.MessageFactory;
import com.zrhis.base.model.Message;
import com.zrhis.base.model.Parameters;
import com.zrhis.system.bean.SysAuthorities;
import com.zrhis.system.dao.BaseDao;
import com.zrhis.system.repository.SysAuthorityRepository;
import com.zrhis.system.repository.SysRolesAuthoritiesRepository;
import com.zrhis.system.service.SecurityAuthorityService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 类功能说明：权限管理服务层
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-27 上午10:15:17
 * @version v1.0
 *
 */
@Service
public class SecurityAuthorityServiceImpl implements SecurityAuthorityService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SysAuthorityRepository sysAuthorityRepository;
	
	@Autowired
	private SysRolesAuthoritiesRepository sysRolesAuthoritiesRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private MessageFactory messageFactory;

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#add(com.zrhis.system.bean.SysAuthorities)
	 */
	@Override
	public Message add(SysAuthorities entity) throws Exception {
		this.baseDao.save(entity);
		
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setMessage(this.messageSource.getMessage("ddl.save.success", null, null));
		
		logger.info("\n添加权限:"+JSONObject.fromObject(entity));
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#update(com.zrhis.system.bean.SysAuthorities)
	 */
	@Override
	public Message update(SysAuthorities entity) throws Exception {
		
		this.baseDao.update(entity);
		
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setMessage(this.messageSource.getMessage("ddl.update.success", null, null));
		
		logger.info("\n修改权限:"+JSONObject.fromObject(entity));
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#delete(com.zrhis.system.bean.SysAuthorities)
	 */
	@Override
	public Message delete(SysAuthorities entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#delete(java.lang.String)
	 */
	@Override
	public Message delete(String entityId) throws Exception {
		this.baseDao.delete(SysAuthorities.class, entityId);
		
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setMessage(this.messageSource.getMessage("ddl.delete.success", null, null));
		
		logger.info("\n删除权限:"+entityId);
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#query()
	 */
	@Override
	public Message query(Parameters params) throws Exception {
		/*ResultModel rm = this.baseDao.queryByJPQL(SysAuthorities.class, null, params.getStart(), params.getLimit());
		Message msg = new Message(rm.getTotalCount(), rm.getList());
		return msg;*/
		Order order1 = new Order("moduleId");
		Order order2 = new Order("enable");
		Sort sort = new Sort(order1,order2);
		PageRequest pageRequest = new PageRequest(params.getSpringDataPage(),params.getLimit(),sort);
		Page<SysAuthorities> page = this.sysAuthorityRepository.findAll(pageRequest);
		return this.messageFactory.query(page);
	}
	
	public Message queryAll(){
		List<SysAuthorities> list = this.sysAuthorityRepository.findAll();
		return new Message(list);
	}
	
	public Message queryEnabled(){
		List<SysAuthorities> list = this.sysRolesAuthoritiesRepository.findByEnabled();
		return new Message(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityAuthorityService#enable(java.lang.String, boolean)
	 */
	@Override
	public Message enable(String entityId, boolean enable) throws Exception {
		
		SysAuthorities bean = (SysAuthorities) this.baseDao.get(SysAuthorities.class, entityId);
		bean.setEnable(enable);
		String msgPropertyName = enable?"ddl.enabled":"ddl.disabled";
		Message msg = new Message(true,this.messageSource.getMessage(msgPropertyName, null, null));
		
		logger.info("ID为："+entityId+"的权限项"+msg.getMessage());
		
		return msg;
	}
	
	/**
	 * 根据ModuleId获取权限集合，如果有moduleId则根据moduleId获取，如果没有就获取moduleId是null的权限
	 * @param moudleId
	 * @return
	 */
	public Message queryByModuleId(String moduleId){
		List<SysAuthorities> list = null;
		if(StringUtils.isEmpty(moduleId)){
			list = this.sysAuthorityRepository.findByModuleIdIsNull();
		}else{
			list = this.sysAuthorityRepository.findByModuleId(moduleId);
		}
		
		return new Message(list);
	}
	
}
