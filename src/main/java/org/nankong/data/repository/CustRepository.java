package org.nankong.data.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.nankong.data.model.Customer;

import java.util.List;
@RepositoryConfig(cacheName="custCache")
public interface CustRepository extends IgniteRepository<Customer,String> {
    List<Customer> getCustomersByCustName(String custName);
    List<Customer> getAllByCustName(String custName);
    @Query("SELECT * FROM Customer WHERE custId = ?")
    Customer getCustomerBySQL(String custId);
    Customer getCustomerByCustId(String custId);
    Customer getCustomerByCustName(String custName);
    @Query("SELECT * FROM Customer WHERE custName = ?")
    Customer getCustomerByNameSQL(String custName);
    @Query("SELECT COUNT(*) FROM Customer WHERE custName != ?")
    int getAllBySQL(String custName);
    @Query("SELECT a.* FROM Customer a,OrgInfo b WHERE a.custInsId = b.orgCode and b.orgCode =?")
    List<Customer> getCustomersBySQL(String ordCode);
}
