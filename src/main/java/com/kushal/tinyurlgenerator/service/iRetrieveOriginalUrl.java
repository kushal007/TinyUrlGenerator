package com.kushal.tinyurlgenerator.service;

import com.kushal.tinyurlgenerator.model.Url;
import org.springframework.stereotype.Service;

@Service
public interface iRetrieveOriginalUrl {
    public Url retrieveOriginalUrl(String tinyUrl);
}
