package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.utils.PageBean;

public interface IDecidedzoneDao extends IBaseDao<Decidedzone> {

	void pageQuery(PageBean pageBean, Decidedzone decidedzone);

}
