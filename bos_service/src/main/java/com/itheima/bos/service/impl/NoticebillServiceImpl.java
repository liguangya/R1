package com.itheima.bos.service.impl;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.PageBean;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Autowired
	private INoticebillDao dao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	@Autowired
	private ICustomerService customerService;
	//保存一个业务通知单，并尝试自动分单
	public void save(Noticebill model, String decidedzone_id) {
		dao.save(model);
		model.setUser((User)ServletActionContext.getRequest().getSession().getAttribute("loginUser"));
		//尝试自动分单
		if(StringUtils.isNotBlank(decidedzone_id)){
			//可以自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzone_id);
			Staff staff = decidedzone.getStaff();
			//设置分单类型为：自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//业务通知单关联取派员
			model.setStaff(staff);
			//为取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//产生时间，系统当前时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);//工单类型：新单
			workbillDao.save(workbill);
			//调用短信平台为取派员发送短信
		}else{
			//不能完成自动分单，需要人工调度
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);//设置分单类型为人工分单
		}
	}
	@Override
	public void findnoassociations(PageBean pageBean) {
		dao.pageQuery(pageBean);;
	}
	/* (non-Javadoc)
	 * @see com.itheima.bos.service.INoticebillService#assignByMan(com.itheima.bos.domain.Noticebill)
	 */
	@Override
	public void assignByMan(Noticebill noticebill) {
		Noticebill model = dao.findById(noticebill.getId());
		model.setStaff(noticebill.getStaff());
		model.setUser((User)ServletActionContext.getRequest().getSession().getAttribute("loginUser"));
		//设置分单类型为：人工分单
		model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		//为取派员产生一个工单
		Workbill workbill = new Workbill();
		workbill.setAttachbilltimes(0);
		workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//产生时间，系统当前时间
		workbill.setNoticebill(model);//工单关联业务通知单
		workbill.setStaff(model.getStaff());
		workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
		workbill.setRemark(model.getRemark());//备注
		workbill.setType(Workbill.TYPE_1);//工单类型：新单
		workbillDao.save(workbill);
		//获取页面传来的客户名字
		String name = model.getCustomerName();
		//获取页面传来的电话
		String tel = model.getTelephone();
		//获取页面传来的取货地址
		String address = model.getPickaddress();
		//封装客户对象
		Customer customer = new Customer();
		customer.setName(name);
		customer.setTelephone(tel);
		customer.setAddress(address);
		customer.setDecidedzoneId("暂无数据");
		customer.setStation("暂无数据");
		//远端调用webService保存客户
		customerService.save(customer);
	}
}
