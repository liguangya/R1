package com.itheima.bos.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Autowired
	private IWorkordermanageDao dao;
	//保存工作单
	public void save(Workordermanage model) {
		dao.save(model);
	}
	//工作单分页查询
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	/**
	 * 批量保存工作单
	 */
	public void saveBatch(ArrayList<Workordermanage> list) {
		for (Workordermanage workordermanage : list) {
			dao.save(workordermanage);
		}
	}
}
