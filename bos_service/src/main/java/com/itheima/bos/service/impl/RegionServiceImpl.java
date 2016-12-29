package com.itheima.bos.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Resource
	private IRegionDao dao;
	//区域批量保存
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			dao.saveOrUpdate(region);
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Region> findAll() {
		return dao.findAll();
	}
	
	//根据q进行模糊查询
	public List<Region> findByQ(String q) {
		return dao.findListByQ(q);
	}
	
	
	/**
	 *   添加新的区域：id手动设置为uuid
	 */
	public void add(Region region) {
			String id = UUID.randomUUID().toString().replace("-", "");
			  region.setId(id);
			dao.save(region);
	}
}
