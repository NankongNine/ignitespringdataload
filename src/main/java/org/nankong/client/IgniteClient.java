package org.nankong.client;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
//扫描basePackages包下的类，让这些类能够被注解为SpringData的仓库接口
@EnableIgniteRepositories(basePackages = {"org.nankong.data.repository"})
public class IgniteClient {
    private IgniteConfiguration igniteConfiguration;
    public static Ignite ignite;
    public IgniteConfiguration getIgniteConfiguration() {
        return igniteConfiguration;
    }
    public void setIgniteConfiguration(IgniteConfiguration igniteConfiguration) {
        this.igniteConfiguration = igniteConfiguration;
    }
    //IgniteRepositoryBeanFactory指定要名为igniteInstance的Bean,可查看源代码
    @Bean
    public Ignite igniteInstance() {
        ignite = Ignition.start(igniteConfiguration);
        return ignite;
    }
    public void destroy() {
        //ignite的destroy方法必须传入数据网格名称作为参数
//        List<String> caches = new ArrayList<>();
//        caches.add("custCache");
//        ignite.destroyCaches(caches);
        ignite.close();
    }
}