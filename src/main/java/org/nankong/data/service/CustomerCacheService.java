package org.nankong.data.service;

import org.nankong.data.model.Customer;
import org.nankong.data.repository.CustRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("customerCacheService")
public class CustomerCacheService implements ICacheService {
    private static final int ROWS = 300000;
    @Autowired
    CustRepository custRepository;
    @Override
    public int loadData(List<String> list) {
        Map<String,Customer> map = new HashMap<String,Customer>();
        for(int i =0;i<list.size();i++){
            Customer customer = new Customer(list.get(i));
            map.put(customer.getCustId(),customer);
//                custRepo.save(customer.getCustId(),customer);
            if((i+1)%ROWS==0){
                System.out.println("第"+i/ROWS+"次提交");
                loadByTransaction(map);
                map.clear();
            }

        }
        return 0;
    }
    @Transactional
    public void loadByTransaction(Map<String,Customer> map){
        custRepository.save(map);
    }
}
