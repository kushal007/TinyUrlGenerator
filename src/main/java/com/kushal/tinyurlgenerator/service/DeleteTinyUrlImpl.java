package com.kushal.tinyurlgenerator.service;

//import com.kushal.tinyurlgenerator.Repository.UrlRepository;
import com.kushal.tinyurlgenerator.Repository.UrlRepositoryCassandra;
import com.kushal.tinyurlgenerator.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteTinyUrlImpl implements iDeleteTinyUrl {
    @Autowired
    UrlRepositoryCassandra urlRepository;

    @Override
    public void deleteTinyUrl(Url url) {
        urlRepository.delete(url);
    }
}
