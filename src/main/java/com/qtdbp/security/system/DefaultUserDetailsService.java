/**
 * 
 */
package com.qtdbp.security.system;

import com.qtdbp.security.model.SysUsers;
import com.qtdbp.security.repository.SysUsersRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 类功能说明：根据用户名获取用户信息及权限信息
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @date 2014-1-16 上午10:23:38
 * @version v1.0
 *
 */
public class DefaultUserDetailsService implements UserDetailsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SysUsersRepository sysUsersRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserCache userCache;
	
	private boolean useCache = false;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		SysUsers user = null;
		if(this.useCache){
			user = (SysUsers) this.userCache.getUserFromCache(username);
		}
		if(user == null){
			user = this.sysUsersRepository.getByUsername(username);
			if(user == null)
				throw new UsernameNotFoundException(this.messageSource.getMessage(
						"UserDetailsService.userNotFount", new Object[]{username}, null));
			//得到用户的权限
			auths = this.sysUsersRepository.loadUserAuthorities( username );
			
			user.setAuthorities(auths);
		}
		
		logger.info("*********************"+username+"***********************");
		logger.info(user.getAuthorities());
		logger.info("********************************************************");
		
		this.userCache.putUserInCache(user);
		
		return user;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
}
