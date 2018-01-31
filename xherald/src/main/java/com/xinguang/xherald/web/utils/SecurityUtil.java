package com.xinguang.xherald.web.utils;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * ClassName: SecurityUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月22日 下午4:41:03 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
public class SecurityUtil {

	public static void getAuthoritys() {
		// 获得当前登陆用户对应的对象。
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		// 获得当前登陆用户所拥有的所有权限。
		Collection<? extends GrantedAuthority> authorities = userDetails
				.getAuthorities();
	}

	public static void getUserDetails(HttpServletRequest request) {

		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

		// 登录名
		System.out.println("Username:"
				+ securityContextImpl.getAuthentication().getName());

		// 登录密码，未加密的
		System.out.println("Credentials:"
				+ securityContextImpl.getAuthentication().getCredentials());
		WebAuthenticationDetails details = (WebAuthenticationDetails) securityContextImpl
				.getAuthentication().getDetails();

		// 获得访问地址
		System.out.println("RemoteAddress" + details.getRemoteAddress());

		// 获得sessionid
		System.out.println("SessionId" + details.getSessionId());

		// 获得当前用户所拥有的权限
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl
				.getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println("Authority" + grantedAuthority.getAuthority());
		}
	}

	public static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		if (principal instanceof Principal) {
			return ((Principal) principal).getName();
		}
		return String.valueOf(principal);
	}

}
