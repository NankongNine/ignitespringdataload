package org.nankong;

import org.nankong.client.IgniteClient;
import org.nankong.data.model.OrgInfo;
import org.nankong.data.repository.OrgInfoRepository;
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadOrgData {
    private static OrgInfoRepository orgRepo;
    public static void main(String args[]) {
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        orgRepo = factory.getBean(OrgInfoRepository.class);
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
        try{
            initOrgData();
        }
        finally {
//            client.destroy();
            ((ClassPathXmlApplicationContext) factory).close();
            System.out.println("初始化数据结束！");
        }
    }

    private static void initOrgData() {
        long startTime=System.currentTimeMillis();   //获取开始时间
        Map<String,OrgInfo> map = new HashMap<String,OrgInfo>();
        orgRepo.deleteAll();
        try {
            List<String> list = FileUtils.fileReader("/home/nankong/works/ignite/org.dat");
            System.out.println(list.size()+" loaded! size is ");
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
        long endTime=System.currentTimeMillis();   //获取结束时间
        System.out.println("加载耗时："+(endTime-startTime)+"ms");
    }
}
