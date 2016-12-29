package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用方法声明
 * @author zhaoqx
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public void executeUpdate(String queryName,Object...objects);
	public T findById(Serializable id);
	public List<T> findAll();
	public void pageQuery(PageBean pageBean);
	public List<T> findByCriteria(DetachedCriteria dc);
}
