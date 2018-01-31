package com.xinguang.xusercenter.shiro.realm;

import com.xinguang.xusercenter.common.context.SpringContext;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
public class AuthRealm extends AuthorizingRealm {

	/**
	 * 认证回调方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken at) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) at;
		String username = token.getUsername();
		UserMapper userMapper = SpringContext.getBean("userMapper");
		User user = userMapper.getUserByUsername(username);
		if (user != null) {
			return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
		} else {
			return null;
		}
	}
	
	/**
	 * 授权回调方法 (进行授权但缓存中无用户的授权信息时调用, 在配有缓存的情况下, 只调用一次)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		return new SimpleAuthorizationInfo();
	}
	
}
