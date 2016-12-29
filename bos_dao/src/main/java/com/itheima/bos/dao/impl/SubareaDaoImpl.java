package com.itheima.bos.dao.impl;

import com.itheima.bos.domain.Subarea;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao{
	public List<Object[]> findSubareas() {
		String hql = "select r.province ,count(*) from Subarea s left outer join s.region r group by r.province";
		return (List<Object[]>) this.getHibernateTemplate().find(hql);
	}
}
