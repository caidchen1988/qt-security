package com.qtdbp.security.service.impl;

import com.qtdbp.security.base.model.Message;
import com.qtdbp.security.model.SysRoles;
import com.qtdbp.security.repository.SysRoleRepository;
import com.qtdbp.security.repository.SysRolesAuthoritiesRepository;
import com.qtdbp.security.repository.SysRolesModulesRepository;
import com.qtdbp.security.service.SecurityRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 类功能说明：角色管理业务层
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2014-1-2 下午5:06:53
 * @version v1.0
 *
 */
@Service
public class SecurityRoleServiceImpl implements SecurityRoleService {
	
	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	private SysRolesModulesRepository sysRolesModulesRepository;
	
	@Autowired
	private SysRolesAuthoritiesRepository sysRolesAuthoritiesRepository;
	
	@Autowired
	private MessageFactory messageFactory;
	
	@Autowired
	private UserSessionContext userSessionContext;

	/*
	 * (non-Javadoc)
	 * @see com.zrhis.system.service.SysRoleService#add(com.zrhis.system.bean.SysRoles)
	 */
	@Override
	public Message add(SysRoles entity) {
		entity.setUserId(this.userSessionContext.getUserId());
		this.sysRoleRepository.save(entity);
		Message msg = this.messageFactory.save(entity);
		return msg;
	}

	@Override
	public Message update(SysRoles entity) {
		entity.setUserId(this.userSessionContext.getUserId());
		this.sysRoleRepository.save(entity);
		Message msg = this.messageFactory.update(entity);
		return msg;
	}

	@Override
	public Message delete(SysRoles entity) {
		this.sysRoleRepository.delete(entity);
		Message msg = this.messageFactory.delete();
		return msg;
	}

	@Override
	public Message delete(String roleId) {
		this.sysRoleRepository.delete(roleId);
		Message msg = this.messageFactory.delete();
		return msg;
	}
	
	/*
	 * @see com.zrhis.system.service.SysRoleService#queryByPage(com.zrhis.base.model.Parameters)
	 */
	@Override
	public Message queryByPage(Parameters params) {
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit());
		Page<SysRoles> page = this.sysRoleRepository.findAll(pageable);
		Long totalCount = page.getTotalElements();
		Message msg = new Message(totalCount.intValue(),page.getContent());
		return msg;
	}
	
	/*
	 * @see com.zrhis.system.service.SysRoleService#queryAllEnabled(Parameters)
	 */
	@Override
	public Message queryAllEnabled(Parameters params){
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit());
		Page<SysRoles> page = this.sysRoleRepository.findAllEnabeld(pageable);
		Long totalCount = page.getTotalElements();
		Message msg = new Message(totalCount.intValue(),page.getContent());
		return msg;
	}
	
	public Message findByRoleNameLikeAndEnableed(String roleName,Parameters params) throws Exception{
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit());
		Page<SysRoles> page = null;
		if(roleName == null){
			page = this.sysRoleRepository.findAllEnabeld(pageable);
		}else{
			roleName = "%"+ServletUtils.transcoding(roleName)+"%";
			page = this.sysRoleRepository.findByRoleNameLikeAndEnabled(roleName, pageable);
		}
		//Message msg = new Message(totalCount.intValue(),page.getContent());
		Message msg = this.messageFactory.query(page);
		return msg;
	}
	/*
	 * @see com.zrhis.system.service.SysRoleService#getById(String)
	 */
	@Override
	public Message getById(String roleId) {
		SysRoles entity = this.sysRoleRepository.findOne(roleId);
		Message msg = new Message();
		msg.setData(entity);
		msg.setSuccess(true);
		return msg;
	}

	/* 
	 * @see com.zrhis.system.service.SysRoleService#enabled(boolean)
	 */
	@Override
	public Message enabled(String roleId,boolean enable) {
		SysRoles entity = this.sysRoleRepository.findOne(roleId);
		entity.setEnable(enable);
		return null;
	}
	/*
	 * @see com.zrhis.system.service.SysRoleService#getModulesByRoleId(java.lang.String)
	 */
	@Override
	public Message getModulesByRoleId(String roleId){
		List<SysRolesModules> list = this.sysRolesModulesRepository.findByRoleId(roleId);
		String[] moduleIds = new String[list.size()];
		for(int i=0;i<list.size();i++){
			SysRolesModules bean = list.get(i);
			moduleIds[i] = bean.getModuleId();
		}
		Message msg = new Message(moduleIds);
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#saveRoleModules(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Message saveRoleModules(String roleId, String[] adds, String[] removes) {
		//批量删除已撤销的模块
		if(!ObjectUtils.isEmpty(removes)){
			Collection<String> modules = ArrayUtils.toList(removes);
			this.sysRolesModulesRepository.deleteByRoleIdAndModuleId(roleId, modules);
		}
		
		List<SysRolesModules> list = new ArrayList<SysRolesModules>();
		//批量添加新添加的模块
		if(!ObjectUtils.isEmpty(adds)){
			for(int i=0;i<adds.length;i++){
				String moduleId = adds[i];
				SysRolesModules bean = new SysRolesModules();
				bean.setModuleId(moduleId);
				bean.setRoleId(roleId);
				bean.setSysRoles(new SysRoles(roleId));
				bean.setSysModules(new SysModules(moduleId));
				list.add(bean);
			}
			this.sysRolesModulesRepository.save(list);
		}
		
		return this.messageFactory.save();
	}
	
	public Message saveRoleAuthorities(String roleId,String[] sysAuthorities){
		this.sysRolesAuthoritiesRepository.deleteByRoleId(roleId);
		
		List<SysRolesAuthorities> list = new ArrayList<SysRolesAuthorities>();
		
		if(!ObjectUtils.isEmpty(sysAuthorities)){
			for(int i=0;i<sysAuthorities.length;i++){
				String authorityId = sysAuthorities[i];
				SysRolesAuthorities bean = new SysRolesAuthorities();
				bean.setRoleId(roleId);
				bean.setAuthorityId(authorityId);
				bean.setSysRoles(new SysRoles(roleId));
				bean.setSysAuthorities(new SysAuthorities(authorityId));
				list.add(bean);
			}
			this.sysRolesAuthoritiesRepository.save(list);
		}
		
		return this.messageFactory.save();
	}
	
	public Message getLeafModulesByRoleId(String roleId){
		
		List<SysModules> list = this.sysRolesModulesRepository.getModulesByRoleId(roleId);
		
		return this.messageFactory.query(list);
	}
	
	/*
	 * @see com.zrhis.system.service.SecurityRoleService#getAuthorityByRoleId(java.lang.String)
	 */
	public Message getAuthorityByRoleId(String roleId){
		
		List<SysAuthorities> list = this.sysRolesAuthoritiesRepository.getAssignationAuthority(roleId);
		
		return this.messageFactory.query(list);
	}
	
	public Message queryByRoleId(String roleId){
		
		List<SysRolesAuthorities> list = this.sysRolesAuthoritiesRepository.findByRoleId(roleId);
		
		String[] idPools = new String[list.size()];
		
		int index = 0;
		
		for(SysRolesAuthorities bean : list){
			idPools[index] = bean.getAuthorityId();
			index++;
		}
		
		return this.messageFactory.getObject(idPools);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#findByRoleNameLike(java.lang.String, com.zrhis.base.model.Parameters)
	 */
	@Override
	public Message findByRoleNameLike(String roleName, Parameters params) throws Exception {
		Order order = new Order("issys");
		Sort sort = new Sort(order);
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit(),sort);
		//pageable.
		Page<SysRoles> page = null;
		if(roleName == null){
			page = this.sysRoleRepository.findAll(pageable);
		}else{
			roleName = "%"+ServletUtils.transcoding(roleName)+"%";
			page = this.sysRoleRepository.findByRoleNameLike(roleName, pageable);
		}
		Message msg = this.messageFactory.query(page);
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#queryAllEnabled()
	 */
	@Override
	public Message queryAllEnabled() {
		List<SysRoles> list = this.sysRoleRepository.findAllEnabeld(true);
		Message msg = new Message(list);
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#findByUserId(java.lang.String)
	 */
	@Override
	public Message findByUserId(String userId) throws Exception {
		List<SysRoles> list = this.sysRoleRepository.findByUserId(userId);
		return this.messageFactory.query(list);
	}
	
	@Override
	public Message findByUserId(boolean enable,String userId){
		List<SysRoles> list = this.sysRoleRepository.findByUserId(enable,userId);
		return this.messageFactory.query(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#findByIssys()
	 */
	@Override
	public Message findByIssys() {
		List<SysAuthorities> list = this.sysRolesAuthoritiesRepository.findByIssys(false);
		return this.messageFactory.query(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityRoleService#saveModuleAndAuthority(java.lang.String, java.util.Map, java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Message saveModuleAndAuthority(String roleId, Map<?, ?> sysModules,
			String[] sysAuthorities) {
		//String[] adds = ;
		//this.saveRoleModules(roleId, adds, removes);
		List<String> addList = (List<String>)sysModules.get("add");
		List<String> removeList = (List<String>)sysModules.get("remove");
		
		String[] adds = new String[addList.size()];
		addList.toArray(adds);
		String[] removes = new String[removeList.size()];
		removeList.toArray(removes);
		
		this.saveRoleModules(roleId, adds, removes);
		this.saveRoleAuthorities(roleId, sysAuthorities);
		
		return this.messageFactory.save();
	}

}
