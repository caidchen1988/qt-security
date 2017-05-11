/**
 * 
 */
package com.qtdbp.security.service.impl;

import com.zrhis.base.MessageFactory;
import com.zrhis.base.model.Message;
import com.zrhis.base.model.Parameters;
import com.zrhis.base.session.UserSessionContext;
import com.zrhis.system.bean.SysRoles;
import com.zrhis.system.bean.SysUsers;
import com.zrhis.system.bean.SysUsersRoles;
import com.zrhis.system.repository.SysUsersRepository;
import com.zrhis.system.repository.SysUsersRolesRepository;
import com.zrhis.system.service.SecurityUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2014-1-13 上午9:36:59
 * @version v1.0
 *
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SysUsersRepository sysUsersRepository;
	
	@Autowired
	private SysUsersRolesRepository sysUsersRolesRepository;
	
	@Autowired
	private MessageFactory messageFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserSessionContext userSessionContext;
	
	private final String USER_EXIST = "UserDetails.AlreadyExists";

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#add(com.zrhis.system.bean.SysUsers)
	 */
	@Override
	public Message add(SysUsers user) {
		String username = user.getUsername();
		
		SysUsers exist = this.sysUsersRepository.getByUsername(username);
		if(exist != null)return this.messageFactory.getMessageObject(USER_EXIST);
		
		String password = user.getPassword();
		password = this.passwordEncoder.encodePassword(password, user.getUsername());
		user.setPassword(password);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		
		this.sysUsersRepository.save(user);
		
		return this.messageFactory.save(user);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#update(com.zrhis.system.bean.SysUsers)
	 */
	@Override
	public Message update(SysUsers user) {
		//this.sysUsersRepository.save(user);
		String userId = user.getUserId();
		SysUsers bean = this.sysUsersRepository.findOne(userId);
		bean.setEnabled(user.isEnabled());
		bean.setUsername(user.getUsername());
		bean.setName(user.getName());
		return this.messageFactory.update(bean);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#modifyPassword(java.lang.String, java.lang.String)
	 */
	public Message modifyPassword(String userId,String oldPassword,String password){
		SysUsers user = this.sysUsersRepository.findOne(userId);
		String encodeOld = this.passwordEncoder.encodePassword(oldPassword, user.getUsername());
		Message msg;
		if(user.getPassword().equals(encodeOld)){
			String encode = this.passwordEncoder.encodePassword(password, user.getUsername());
			user.setPassword(encode);
			msg = this.messageFactory.update();
			msg.setMessage("密码修改成功");
		}else{
			msg = this.messageFactory.update();
			msg.setFlag(false);
			msg.setMessage("密码输入错误");
		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#delete(java.lang.String)
	 */
	@Override
	public Message delete(String userId) {
		this.sysUsersRepository.delete(userId);
		return this.messageFactory.delete();
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#query(java.lang.String, com.zrhis.base.model.Parameters)
	 */
	@Override
	public Message query(String username, Parameters params) {
		Page<SysUsers> users = null;
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit());
		if(StringUtils.isEmpty(username)){
			users = this.sysUsersRepository.findAll(pageable);
		}else{
			username = "%"+username+"%";
			users = this.sysUsersRepository.findByUsernameLike(username, pageable);
		}
		return this.messageFactory.query(users);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#findRoleByUserId(java.lang.String)
	 */
	@Override
	public Message findRoleByUserId(String userId) {
		List<SysUsersRoles> list = this.sysUsersRolesRepository.findByUserId(userId);
		
		String[] sysRoles = new String[list.size()];
		
		for(int i=0;i<list.size();i++){
			SysUsersRoles bean = list.get(i);
			sysRoles[i] = bean.getRoleId();
		}
		
		return this.messageFactory.getObject(sysRoles);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#assginRoles(java.lang.String, java.lang.String[])
	 */
	@Override
	public Message assignRoles(String userId, String[] roles) {
		this.sysUsersRolesRepository.deleteByUserId(userId,this.userSessionContext.getUserId());
		
		if(!ObjectUtils.isEmpty(roles)){
			List<SysUsersRoles> list = new ArrayList<SysUsersRoles>();
			for(int i=0;i<roles.length;i++){
				String roleId = roles[i];
				SysUsersRoles entity = new SysUsersRoles();
				entity.setSysUsers(new SysUsers(userId));
				entity.setSysRoles(new SysRoles(roleId));
				entity.setCzybh(this.userSessionContext.getUserId());
				list.add(entity);
			}
			this.sysUsersRolesRepository.save(list);
		}
		
		return this.messageFactory.save();
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityUserService#queryByJgid(java.lang.String)
	 */
	@Override
	public Message queryByJgid(String jgid,Parameters params) {
		PageRequest pageable = new PageRequest(params.getSpringDataPage(), params.getLimit());
		Page<SysUsers> users = this.sysUsersRepository.findByVQzjgid(jgid, pageable);
		return this.messageFactory.query(users);
	}

}
