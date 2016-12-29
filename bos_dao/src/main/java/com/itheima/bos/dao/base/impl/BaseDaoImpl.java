package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用实现类
 * 
 * @author zhaoqx
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> entityClass = null;// 代表当前操作的实体类型

	@Resource // 目的是让spring框架调用当前方法，注入SessionFactory对象
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	// 在构造方法中动态获得当前操作的实体类型
	public BaseDaoImpl() {
		// 获得父类类型----BaseDaoImpl<T>
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获得父类上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "from " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	// 执行任意更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		// 根据命名查询语句获得一个Query对象
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
	}

	//持久层通用分页实现
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//修改hibernate发送的sql形式：select count(*) from xxx...
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().
				findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		//查询total
		pageBean.setTotal(count.intValue());
		//修改回sql:select * from xxxx
		detachedCriteria.setProjection(null);
		
		//指定hibernate如果将sql查询结果封装为对象
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		//查询rows数据集合
		List rows = this.getHibernateTemplate().
				findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCriteria(DetachedCriteria dc) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}
}
