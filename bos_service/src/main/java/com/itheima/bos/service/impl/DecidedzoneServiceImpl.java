package com.itheima.bos.service.impl;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	@Autowired
	//@Qualifier("")
	private IDecidedzoneDao dao;
	public void save(Decidedzone decidedzone, String[] subareaid) {
		dao.save(decidedzone);
		for (String sid : subareaid) {
			dao.executeUpdate("subarea.update", decidedzone,sid);
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public void pageQuery(PageBean pageBean, Decidedzone decidedzone) {
		dao.pageQuery(pageBean,decidedzone);
	}
}
