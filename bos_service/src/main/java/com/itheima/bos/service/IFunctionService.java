package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public List<Function> findTopList();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu(User user);

	public Role findRoleById(String roleId);

	public void edit(Function model);

}
