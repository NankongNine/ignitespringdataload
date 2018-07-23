package org.nankong;

import org.apache.lucene.analysis.ckb.SoraniStemFilter;
import org.nankong.client.IgniteClient;
import org.nankong.data.model.Customer;
import org.nankong.data.model.OrgInfo;
import org.nankong.data.repository.CustRepository;
import org.nankong.data.repository.OrgInfoRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    /** Ignite Spring Data repository. */
    private static CustRepository repo;
    private static OrgInfoRepository orgRepo;

    public static void main( String[] args )
    {
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        repo = factory.getBean(CustRepository.class);
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
        orgRepo = factory.getBean(OrgInfoRepository.class);
//        client.igniteInstance();
        initData();
        try{
            int count = repo.getAllBySQL("Endless");
            System.out.println("yigong you "+count+"个！");
        List<Customer> customerList = repo.getAllByCustName("bestcrandt");
        System.out.println("size is "+customerList.size());
        if(customerList!=null&&customerList.size()>0){
            long startTime=System.currentTimeMillis();   //获取开始时间
            System.out.println("name is "+customerList.get(0).toP9String());
            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println(endTime-startTime +"ms");
        }else{
            System.out.println("query failed!");
        }
            long startTime=System.currentTimeMillis();   //获取开始时间
//            Customer customer = repo.getCustomerByCustId("0000999998");
//            Customer customer = repo.getCustomerByNameSQL("南宫九");
//            Customer customer = repo.getCustomerBySQL("0000999998");
            List<Customer> customerList2 = repo.getCustomersBySQL("344368061");
            System.out.println("查询了"+customerList2.size());
            Customer customer = customerList2.get(0);
            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println(customer.toP9String()+"\n查询耗时："+(endTime-startTime)+"ms");
            OrgInfo orgInfo = orgRepo.queryAllByOrgCode("344368061");
            System.out.println(orgInfo.toP9String());

        }

        finally {
            client.destroy();
        }

    }
    private static void initData(){

    }
}
