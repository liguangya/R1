package com.itheima.bos.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Resource
	private IStaffDao dao;
	//添加取派员
	public void save(Staff staff) {
		dao.save(staff);
	}
	
	
	//分页查询
	public void pageQuery(PageBean pageBean,Staff staff) {
		dao.pageQuery(pageBean,staff);
	}

	//批量删除取派员（逻辑删除）
	public void deleteBatch(String ids) {
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			dao.executeUpdate("staff.delete", id);
		}
	}

	//根据id查询取派员
	public Staff findById(String id) {
		return dao.findById(id);
	}

	//根据id修改取派员信息
	public void update(Staff staff) {
		//持久对象---根据id查询数据库，获得原始数据
		Staff dbstaff = dao.findById(staff.getId());
		dbstaff.setName(staff.getName());
		dbstaff.setTelephone(staff.getTelephone());
		dbstaff.setHaspda(staff.getHaspda());
		dbstaff.setStandard(staff.getStandard());
		dbstaff.setStation(staff.getStation());
	}

	//查询所有未删除的取派员
	public List<Staff> findListNotDelete() {
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件，deltag为0表示未删除
		dc.add(Restrictions.ne("deltag", "1"));
		return dao.findByCriteria(dc);
	}
	
	//批量还原取派员
	public void restoreBatch(String ids) {
		String[] id = ids.split(",");
		for (String string : id) {
			dao.executeUpdate("staff.restore",string);
		}
	}
}
