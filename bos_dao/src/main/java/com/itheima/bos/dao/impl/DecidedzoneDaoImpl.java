package com.itheima.bos.dao.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;
@Repository
public class DecidedzoneDaoImpl extends BaseDaoImpl<Decidedzone> implements IDecidedzoneDao {

	public void pageQuery(PageBean pageBean, Decidedzone decidedzone) {
		// 获取当前页
				int currentPage = pageBean.getCurrentPage();
				int pageSize = pageBean.getPageSize();
				DetachedCriteria criteria = pageBean.getDetachedCriteria();
				String id=decidedzone.getId();
				if(StringUtils.isNoneBlank(id)){
					criteria.add(Restrictions.like("id","%"+id+"%"));
				}
				String name=decidedzone.getName();
				if(StringUtils.isNoneBlank(name)){
					try {
						name = new String(name.getBytes("iso-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					criteria.add(Restrictions.like("name","%"+name+"%"));
				}
				pageBean.setDetachedCriteria(criteria);
				criteria.setProjection(Projections.rowCount());
				List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(criteria);
				if (list != null && list.size() > 0) {
					pageBean.setTotal(list.get(0).intValue());
				}
				// 清空criteria的聚合函数查询条件
				criteria.setProjection(null);
				//指定hibernate封装对象，在多表关联查询时，发送多张表的sql，依然封装成root对象
				criteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
				// 分页查询
				List rows = this.getHibernateTemplate().findByCriteria(criteria, (currentPage - 1) * pageSize, pageSize);
				pageBean.setRows(rows);
	}

}
