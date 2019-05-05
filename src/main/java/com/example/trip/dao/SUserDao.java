package com.example.trip.dao;

import com.example.trip.bean.SUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 用户信息查询
 * @author Gu
 */
@Mapper
public interface SUserDao {
	/**
	 * 根据用户名获取用户
	 *
	 * @param name
	 * @return
	 */
	@Select(value = " SELECT su.* FROM s_user su WHERE su.name = #{name} ")
	public SUser findSUserByName(String name);
	@Select(value = " SELECT su.* FROM s_user su WHERE su.email = #{email} ")
	public SUser findSUserByEmail(String email);


	@Insert(value=" INSERT s_user (name,email,password) values (#{userName},#{email},#{password})")
	public boolean addUser(String userName,String email,String password);


}