/**
 * 
 */
package com.qtdbp.security.system;

import com.qtdbp.security.base.Message;
import com.qtdbp.security.utils.ControllerTools;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能说明：登录后返回拦截前的界面
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@Deprecated
@SuppressWarnings("deprecation")
public class LoginAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	public LoginAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		boolean isAjax = ControllerTools.isAjaxRequest(request);
		boolean hasSession = ControllerTools.hasAuthentication();
		System.out.println("isAjax:"+isAjax);
		System.out.println("hasAuthentication："+hasSession);
		if(isAjax && !hasSession){
			this.transformAjaxRequest(request, response);
		}else{
			super.commence(request, response, authException);
		}
	}

	private void transformAjaxRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException{
		Message msg = new Message();
		msg.setSessionOut(true);
		msg.setMessage("Session超时，请重新登录");
		ControllerTools.print(response, msg);
	}
	
}
