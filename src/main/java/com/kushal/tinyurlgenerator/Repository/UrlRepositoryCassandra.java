package com.kushal.tinyurlgenerator.Repository;

import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlCassandra;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepositoryCassandra extends CassandraRepository<Url,Long> {

    @AllowFiltering
    Url findByShortLink(String shortLink);
}
