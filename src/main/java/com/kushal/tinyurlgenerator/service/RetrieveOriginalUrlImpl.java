package com.kushal.tinyurlgenerator.service;

//import com.kushal.tinyurlgenerator.Repository.UrlRepository;
import com.kushal.tinyurlgenerator.Repository.UrlRepositoryCassandra;
import com.kushal.tinyurlgenerator.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrieveOriginalUrlImpl implements iRetrieveOriginalUrl{

    @Autowired
    UrlRepositoryCassandra urlRepository;

    @Override
    public Url retrieveOriginalUrl(String tinyUrl) {
        Url UrlToRet = urlRepository.findByShortLink(tinyUrl);
        return UrlToRet;
    }
}
