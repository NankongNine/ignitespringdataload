package org.nankong.data.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.nankong.data.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    @Query("SELECT a.* FROM Customer a,OrgInfo b WHERE a.custInsId = b.orgCode and b.orgCode =? order by custName")
    Page<Customer> getPagedCustomersBySQL(String ordCode, Pageable pageable);
    @Query("SELECT a.* FROM Customer a,OrgInfo b WHERE a.custInsId = b.orgCode and b.orgCode =? order by custName")
    List<Customer> getCustomersBySQL(String ordCode, Pageable pageable);


}
