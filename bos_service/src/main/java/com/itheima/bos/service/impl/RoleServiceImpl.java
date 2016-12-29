package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Autowired
	private IRoleDao dao;
	//添加角色，同时关联权限
	public void saveOrUpdate(Role role, String functionIds) {
		dao.saveOrUpdate(role);//持久对象
		if(StringUtils.isNotBlank(functionIds)){
			String[] ids = functionIds.split(",");
			for (String functionId : ids) {
				Function function = new Function(functionId);//托管对象
				role.getFunctions().add(function);//角色关联权限
			}
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Role> findAll() {
		return dao.findAll();
	}

	/**
	 * 通过id查找当前角色
	 */
	public Role findRoleById(String id) {
		return dao.findById(id);
	}
}
