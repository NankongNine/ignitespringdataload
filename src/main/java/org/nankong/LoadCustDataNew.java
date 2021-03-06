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
import org.nankong.file.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoadCustDataNew {
    public static void main(String args[]){



        //Failed to connect to any address from IP finder
        BeanFactory factory = new ClassPathXmlApplicationContext("application.xml");
        IgniteClient client = factory.getBean("igniteClient",IgniteClient.class);
//        final Ignite ignite = getIgnite();
        final Ignite ignite = client.igniteInstance();
        final IgniteDataStreamer streamer = ignite.dataStreamer("custCache");
        try {
//            initialData("/home/nankong/works/ignite/cust.dat",cache);
            initialDataNew("f:/cust.dat",streamer);
        }
        finally {
            streamer.close();
            ignite.close();

        }
    }
    private static Ignite getIgnite() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);

        Ignite ignite = Ignition.getOrStart(cfg);
        return ignite;
    }
    private static void initialDataNew(String filePath,IgniteDataStreamer<String,Customer> streamer){
        try {
            List<String> list = FileUtils.fileReader(filePath);
            for(int i=0;i<list.size();i++){
                Customer cust = new Customer(list.get(i));
                streamer.addData(cust.getCustId(),cust);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
