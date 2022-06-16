package com.kushal.tinyurlgenerator.service;

import com.kushal.tinyurlgenerator.Repository.UrlRepository;
import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlRequestDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateTinyUrlImplTest {

    @Mock
    UrlRepository mockRepository;

    @InjectMocks
    GenerateTinyUrlImpl urlService;
    @Test
    public void retrieveUrlTest()
    {
        UrlRequestDto request = new UrlRequestDto("https://www.youtube.com/watch?v=ReS_JQIqB_I&ab_channel=Slyfer2812", "helloWorld", null);
        Url url = new Url();
        url.setExpirationDate(null);
        url.setOriginalUrl(request.getUrl());
        url.setShortLink("helloWorld");
        when(mockRepository.save(any(Url.class))).thenReturn(url);
        Assert.assertEquals(urlService.convertToShortUrl(request).getOriginalUrl(),request.getUrl());
        Assert.assertEquals(urlService.convertToShortUrl(request).getShortLink(),request.getCustomTinyUrl());
    }

    @Test
    public void convertToShortUrlTest()
    {
        UrlRequestDto request = new UrlRequestDto("https://www.youtube.com/watch?v=ReS_JQIqB_I&ab_channel=Slyfer2812", "helloWorld", null);
        Url url = new Url();
        url.setExpirationDate(null);
        url.setOriginalUrl(request.getUrl());
        url.setShortLink("helloWorld");
        when(mockRepository.save(any(Url.class))).thenReturn(url);
        Assert.assertEquals(urlService.convertToShortUrl(request).getShortLink(),request.getCustomTinyUrl());
    }
}
