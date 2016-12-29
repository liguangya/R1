package com.itheima.bos.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.User;
/**
 * 持久层DAO
 * @author zhaoqx
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	//根据账号和密码查询用户
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "from User where username = ? and password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
