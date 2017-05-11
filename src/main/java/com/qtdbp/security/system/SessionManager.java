/**
 * 
 */
package com.qtdbp.security.system;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public class SessionManager implements SessionAuthenticationStrategy {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.session.SessionAuthenticationStrategy#onAuthentication(org.springframework.security.core.Authentication, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onAuthentication(Authentication authentication,
			HttpServletRequest request, HttpServletResponse response)
			throws SessionAuthenticationException {
		// TODO Auto-generated method stub

	}

}
