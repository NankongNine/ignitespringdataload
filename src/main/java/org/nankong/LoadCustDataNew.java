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
import org.nankong.data.model.Customer;
import org.nankong.file.FileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoadCustDataNew {
    public static void main(String args[]){




//        final Ignite ignite = getIgnite();
        final Ignite ignite = getIgnite();
        try {
            CacheConfiguration cacheCfg = new CacheConfiguration();
            cacheCfg.setName("custCache");
            cacheCfg.setCacheMode(CacheMode.REPLICATED);
            cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cacheCfg.setBackups(1);

            final IgniteCache<String, Customer> cache = ignite.getOrCreateCache(cacheCfg);
            final IgniteDataStreamer streamer = ignite.dataStreamer("custCache");
//            initialData("/home/nankong/works/ignite/cust.dat",cache);
            initialDataNew("f:/cust.dat",streamer);
            streamer.close();
            Customer cust  = new Customer("0000000010|@|hqokhvbogo|@|2009-03-13|@|1|@|7994357478006225|@|0|@|CN000|@|0.5274209|@|139955173|@|\n");
            cache.put(cust.getCustId(),cust);
            System.out.println(cache.get(cust.getCustId()).toP9String());
        }
        finally {
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
    private static void initialData(String filePath,IgniteCache<String,Customer> cache){
        try {
            List<String> list = FileUtils.fileReader(filePath);
            for(int i=0;i<list.size();i++){
                putCustByKey(list.get(i),cache);
                if((i+1)%300000==0){
                    System.out.println("第"+(i+1)%300000+"次");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void putCustByKey(String p9Str,IgniteCache<String,Customer> cache){
        Customer cust = new Customer(p9Str);
        cache.put(cust.getCustId(),cust);
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
