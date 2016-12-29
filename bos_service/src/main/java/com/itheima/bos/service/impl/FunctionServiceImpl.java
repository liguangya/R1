package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao dao;
	public List<Function> findAll() {
		return dao.findAll();
	}
	
	
	//查询顶级权限
	public List<Function> findTopList() {
		DetachedCriteria dc = DetachedCriteria.forClass(Function.class);
		//添加过滤条件，只需要顶级权限，parentFunction为null
		dc.add(Restrictions.isNull("parentFunction"));
		//添加排序
		dc.addOrder(Order.desc("zindex"));
		return dao.findByCriteria(dc);
	}
	
	public void save(Function model) {
		if(model.getParentFunction() != null && model.getParentFunction().getId().equals("")){
			model.setParentFunction(null);
		}
		dao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	
	//根据用户查询对应的菜单数据
	public List<Function> findMenu(User user) {
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			DetachedCriteria dc = DetachedCriteria.forClass(Function.class);
			//添加过滤条件，generatemenu为1表示是菜单
			dc.add(Restrictions.eq("generatemenu", "1"));
			dc.addOrder(Order.asc("zindex"));
			//超级管理员，查询所有菜单
			list = dao.findByCriteria(dc);
		}else{
			//根据用户id查询菜单
			list = dao.findMenu(user.getId());
		}
		return list;
	}


	/**
	 * 通过id查找当前要修改的角色
	 */
	public Role findRoleById(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//修改权限
	public void edit(Function model) {
		Function function = dao.findById(model.getId());
		function.setCode(model.getCode());
		function.setDescription(model.getDescription());
		function.setGeneratemenu(model.getGeneratemenu());
		function.setName(model.getName());
		function.setPage(model.getPage());
		function.setZindex(model.getZindex());
	}

}
