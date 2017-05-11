/**
 * 
 */
package com.qtdbp.security.service.impl;


import com.zrhis.base.model.Message;
import com.zrhis.base.model.Parameters;
import com.zrhis.system.bean.SysModules;
import com.zrhis.system.bean.SysResources;
import com.zrhis.system.dao.BaseDao;
import com.zrhis.system.repository.SysResourceRepository;
import com.zrhis.system.service.SecurityResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-30 下午3:35:21
 * @version v1.0
 *
 */
@Service
public class SecurityResourcesServiceImpl implements SecurityResourcesService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SysResourceRepository sysResourceRepository;
	
	@Autowired
	private MessageSource messageSource;

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourcesService#add(com.zrhis.system.bean.SysResources, java.lang.String)
	 */
	@Override
	public Message add(SysResources resource, String moduleId) throws Exception {
		SysModules module = null;
		if(moduleId != null && !"".equals(moduleId))
			module = this.baseDao.get(SysModules.class, moduleId);
		
		resource.setSysModules(module);

		this.sysResourceRepository.save(resource);
		
		return new Message(true,this.messageSource.getMessage("ddl.save.success", null, null));
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourcesService#update(com.zrhis.system.bean.SysResources, java.lang.String)
	 */
	@Override
	public Message update(SysResources resource, String moduleId)
			throws Exception {
		SysModules module = null;
		if(moduleId != null && !"".equals(moduleId))
			module = this.baseDao.get(SysModules.class, moduleId);
		
		resource.setSysModules(module);
		//this.baseDao.update(resource);
		this.sysResourceRepository.save(resource);
		
		return new Message(true,this.messageSource.getMessage("ddl.update.success", null, null));
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourcesService#delete(java.lang.String)
	 */
	@Override
	public Message delete(String resourceId) throws Exception {
		//this.baseDao.delete(SysResources.class, resourceId);
		this.sysResourceRepository.delete(resourceId);
		
		return new Message(true,this.messageSource.getMessage("ddl.delete.success", null, null));
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourcesService#query(com.zrhis.system.bean.SysResources)
	 */
	@Override
	public Message query(String resourceName,Parameters params) throws Exception {
		PageRequest pageRequest = new PageRequest(params.getSpringDataPage(),params.getLimit());
		Page<SysResources> page = null;
		if(resourceName == null || "".equals(resourceName)){
			page = this.sysResourceRepository.findAll(pageRequest);
		}else{
			String likeStr = "%"+resourceName+"%";
			page = this.sysResourceRepository.findByResourceNameOrResourcePath(likeStr, likeStr, pageRequest);
		}
		
		Long totalCount = page.getTotalElements();
		Message msg = new Message(totalCount.intValue(),page.getContent());
		return msg;
	}

}
