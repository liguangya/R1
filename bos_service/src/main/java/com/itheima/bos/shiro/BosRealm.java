package com.itheima.bos.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

/**
 * 自定义一个realm
 * @author zhaoqx
 *
 */
public class BosRealm extends AuthorizingRealm{
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("自定义的realm认证方法执行了。。。。");
		UsernamePasswordToken mytoken = (UsernamePasswordToken)token;
		String username = mytoken.getUsername();
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("username", username));
		//根据用户名查询数据库中的密码
		List<User> list = userDao.findByCriteria(dc);
		if(list != null && list.size() > 0){
			User user = list.get(0);
			//获取数据库中查询到的密码
			String dbPassword = user.getPassword();
			//简单认证信息对象,由安全管理器负责比对数据库中查询到的密码和token中封装的密码(页面输入的密码)
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, dbPassword, this.getName());
			return info;
		}else{
			//username不存在
			return null;
		}
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//简单授权信息对象，当前用户拥有的权限通过这个对象封装
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//Subject subject = SecurityUtils.getSubject();
		//User user = (User) subject.getPrincipal();
		//获取当前登录用户对象
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		//查询数据库，获取当前用户对应的权限
		if(user.getUsername().equals("admin")){
			//当前用户是内置超级管理员账号，查询所有权限，为其授权
			list = functionDao.findAll();
		}else{
			//根据用户id查询对应的权限
			list = functionDao.findByUserId(user.getId());
		}
		for(Function function:list){
			//为用户授权
			info.addStringPermission(function.getCode());
		}
		return info;
	}
}
