package com.xinguang.xusercenter.mapper;

import com.xinguang.xusercenter.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yangsh
 */
public interface UserMapper {

	/**
	 * 用户名是否存在
	 * @param username 用户名
	 * @return
	 */
	@Select("SELECT count(id) FROM t_user WHERE username=#{0}")
	boolean isExistByUsername(String username);

	/**
	 * 手机是否存在
	 * @param phone 手机
	 * @return
	 */
	@Select("SELECT count(id) FROM t_user WHERE phone=#{0}")
	boolean isExistByPhone(String phone);

	/**
	 * 邮箱是否存在
	 * @param email 邮箱
	 * @return
	 */
	@Select("SELECT count(id) FROM t_user WHERE email=#{0}")
	boolean isExistByEmail(String email);

	/**
	 * 新增用户
	 * @param user 用户
	 */
	void addUser(User user);

	/**
	 * 通过 ID 获取用户
	 * @param id 用户名
	 * @return
	 */
	User getUserById(String id);

	/**
	 * 通过 username 获取用户
	 * @param username 用户名
	 * @return
	 */
	User getUserByUsername(String username);

	/**
	 * 获取用户
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	User getUserByUsernameAndPassword(String username, String password);

	/**
	 * 修改用户
	 * @param user 用户
	 */
	void updateUser(User user);
	
}
