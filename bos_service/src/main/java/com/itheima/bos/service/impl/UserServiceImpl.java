package com.itheima.bos.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	//注入Dao
	@Autowired
	private IUserDao dao;
	public User login(User user) {
		String password = user.getPassword();//明文密码
		password = MD5Utils.md5(password);//md5加密
		return dao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	
	//根据id修改用户密码
	public void editPassword(String password, String id) {
		password = MD5Utils.md5(password);//md5加密
		dao.executeUpdate("user.editPassword", password,id);
	}

	//添加用户，同时关联角色
	public void save(User user, String[] roleIds) {
		//密码使用Md5加密
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		dao.save(user);//持久对象
		if(roleIds != null && roleIds.length > 0){
			for (String roleId : roleIds) {
				Role role = new Role(roleId);//构造一个托管对象
				user.getRoles().add(role);//用户关联角色
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
}
