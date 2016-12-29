package bos_utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itheima.crm.ICustomerService;

public class CRMTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ICustomerService proxy = (ICustomerService) ctx.getBean("crmClient");
		List<Integer> list = new ArrayList<>();
		list.add(4);
		list.add(8);
		proxy.assignCustomersToDecidedzone("8a7e844e58ebf6da0158ebf82a160000", list );
	}
}
