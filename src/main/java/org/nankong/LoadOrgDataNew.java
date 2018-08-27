package org.nankong;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.nankong.client.IgniteClient;
import org.nankong.data.model.Customer;
import org.nankong.data.model.OrgInfo;
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoadOrgDataNew {
    public static void main(String args[]){


        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
//        final Ignite ignite = getIgnite();
        final Ignite ignite = client.igniteInstance();
        final IgniteDataStreamer streamer = ignite.dataStreamer("orgCache");
        try {
            initialDataNew("f:/org.dat",streamer);
        }
        finally {
            streamer.close();
            ignite.close();

        }
    }
    private static void initialDataNew(String filePath,IgniteDataStreamer<String,OrgInfo> streamer){
        try {
            List<String> list = FileUtils.fileReader(filePath);
            for(int i=0;i<list.size();i++){
                OrgInfo orgInfo = new OrgInfo(list.get(i));
                streamer.addData(orgInfo.getOrgCode(),orgInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
