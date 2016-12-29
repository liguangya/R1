package com.itheima.bos.service;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.utils.PageBean;

public interface INoticebillService {

	public void save(Noticebill model, String decidedzone_id);

	public void findnoassociations(PageBean pageBean);

	public void assignByMan(Noticebill noticebill);

}
