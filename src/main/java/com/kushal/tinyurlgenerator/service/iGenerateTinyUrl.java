package com.kushal.tinyurlgenerator.service;

import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface iGenerateTinyUrl {
    Url convertToShortUrl(UrlRequestDto request);
}