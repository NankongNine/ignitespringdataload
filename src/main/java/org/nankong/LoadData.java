package org.nankong;

import org.nankong.client.IgniteClient;
import org.nankong.data.model.Customer;
import org.nankong.data.model.OrgInfo;
import org.nankong.data.repository.CustRepository;
import org.nankong.data.repository.OrgInfoRepository;
import org.nankong.data.service.CustomerCacheService;
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadData {
    private static OrgInfoRepository orgRepo;
    private static CustRepository custRepo;
    private static CustomerCacheService custService;

    public static void main(String args[]){
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        orgRepo = factory.getBean(OrgInfoRepository.class);
        custRepo = factory.getBean(CustRepository.class);
        custService = factory.getBean("customerCacheService",CustomerCacheService.class);
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
        try{
            initData();
        }
        finally {
//            client.destroy();
            ((ClassPathXmlApplicationContext) factory).close();
            System.out.println("初始化数据结束！");
        }
    }

    private static void initData() {
        long startTime=System.currentTimeMillis();   //获取开始时间
        initOrgData();
        long endTime=System.currentTimeMillis();   //获取结束时间
        System.out.println("加载耗时："+(endTime-startTime)+"ms");
        startTime=System.currentTimeMillis();   //获取开始时间
        initCustData();
        endTime=System.currentTimeMillis();   //获取结束时间
        System.out.println("加载耗时："+(endTime-startTime)+"ms");
    }
    private static void initOrgData(){
        Map<String,OrgInfo> map = new HashMap<String,OrgInfo>();
        orgRepo.deleteAll();
        try {
            List<String> list = FileUtils.fileReader("/home/nankong/works/ignite/org.dat");
            System.out.println(list.size()+" loaded!");
            for(int i =0;i<list.size();i++){
                OrgInfo orginfo = new OrgInfo(list.get(i));
                map.put(orginfo.getOrgCode(),orginfo);
            }
            orgRepo.save(map);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

        }
    }
    private static void initCustData(){
        Map<String,Customer> map = new HashMap<String,Customer>();
        try {
            List<String> list = FileUtils.fileReader("/home/nankong/works/ignite/cust.dat");
            System.out.println(list.size()+" loaded!");
            custRepo.deleteAll();
            custService.loadData(list);
    //            for(int i =0;i<list.size();i++){
//                Customer customer = new Customer(list.get(i));
//                map.put(customer.getCustId(),customer);
////                custRepo.save(customer.getCustId(),customer);
//                if(i%300000==0){
//                    System.out.println("第"+i/30000+"次提交");
//                    custRepo.save(map);
//                    map.clear();
//                }
//
//            }
//            custRepo.save(map);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

        }
    }
}
