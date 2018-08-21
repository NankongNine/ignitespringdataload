package org.nankong;

import org.nankong.client.IgniteClient;
import org.nankong.data.model.Customer;
import org.nankong.data.repository.CustRepository;
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadCustData {
    private static CustRepository custRepo;
    public static void main(String args[]) {
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        custRepo = factory.getBean(CustRepository.class);
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
        try{
            initCustData();
        }
        finally {
//            client.destroy();
            ((ClassPathXmlApplicationContext) factory).close();
            System.out.println("初始化数据结束！");
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
//                custRepo.save(customer.getCustId(),customer);
                if(i%500000==0){
                    System.out.println("第"+i/200000+"次提交");
                    custRepo.save(map);
                    map.clear();
                }

            }
            custRepo.save(map);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

        }
    }
}
