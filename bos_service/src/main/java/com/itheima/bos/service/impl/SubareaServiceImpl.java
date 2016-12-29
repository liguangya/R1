package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDao dao;
	public void save(Subarea model) {
		dao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Subarea> findAll() {
		return dao.findAll();
	}
	//查询所有未关联到定区的分区
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		dc.add(Restrictions.isNull("decidedzone"));
		return dao.findByCriteria(dc);
	}
	
	//根据定区id查询分区
	public List<Subarea> findListByDecidedzoneId(String did) {
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		dc.add(Restrictions.eq("decidedzone.id", did));
		return dao.findByCriteria(dc);
	}
	
	public List<Object[]> findSubareas() {
		return dao.findSubareas();
	}
	
	//修改分区信息
	public void update(Subarea model) {
		Subarea subarea = dao.findById(model.getId());
		subarea.setRegion(model.getRegion());
		subarea.setAddresskey(model.getAddresskey());
		subarea.setStartnum(model.getStartnum());
		subarea.setEndnum(model.getEndnum());
		subarea.setSingle(model.getSingle());
		subarea.setPosition(model.getPosition());
	}
}
