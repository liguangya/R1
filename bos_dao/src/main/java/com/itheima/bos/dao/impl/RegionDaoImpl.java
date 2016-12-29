package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
	public List<Region> findListByQ(String q) {
		String hql = "from Region where province like ? "
				+ "or city like ? or district like ?"
				+ " or shortcode like ? or citycode like ?";
		return (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%");
	}
}
