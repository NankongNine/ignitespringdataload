package org.nankong.data.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.nankong.data.model.OrgInfo;

@RepositoryConfig(cacheName="orgCache")
public interface OrgInfoRepository extends IgniteRepository<OrgInfo,String> {
    OrgInfo queryAllByOrgCode(String orgCode);
}
