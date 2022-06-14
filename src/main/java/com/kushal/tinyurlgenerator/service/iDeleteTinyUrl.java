package com.kushal.tinyurlgenerator.service;

import com.kushal.tinyurlgenerator.model.Url;
import org.springframework.stereotype.Service;

@Service
public interface iDeleteTinyUrl {
    public void deleteTinyUrl(Url url);
}
