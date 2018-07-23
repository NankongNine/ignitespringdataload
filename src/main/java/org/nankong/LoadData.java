package org.nankong;

import org.nankong.client.IgniteClient;
import org.nankong.data.model.Customer;
import org.nankong.data.model.OrgInfo;
import org.nankong.data.repository.CustRepository;
import org.nankong.data.repository.OrgInfoRepository;
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadData {
    private static OrgInfoRepository orgRepo;
    private static CustRepository custRepo;

    public static void main(String args[]){
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        orgRepo = factory.getBean(OrgInfoRepository.class);
        custRepo = factory.getBean(CustRepository.class);
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
        try{
            initData();
        }
        finally {
            client.destroy();
            System.out.println("初始化数据结束！");
        }
    }

    private static void initData() {
        initOrgData();
        initCustData();
    }
    private static void initOrgData(){
        Map<String,OrgInfo> map = new HashMap<String,OrgInfo>();
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
            for(int i =0;i<list.size();i++){
                Customer customer = new Customer(list.get(i));
                map.put(customer.getCustId(),customer);
            }
            custRepo.save(map);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

        }
    }
}
