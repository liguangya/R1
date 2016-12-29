package com.itheima.bos.service;

import java.util.ArrayList;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.utils.PageBean;

public interface IWorkordermanageService {
	public void save(Workordermanage model);
	public void pageQuery(PageBean pageBean);
	public void saveBatch(ArrayList<Workordermanage> list);
}
