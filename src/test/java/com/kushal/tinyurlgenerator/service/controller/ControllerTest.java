package com.kushal.tinyurlgenerator.service.controller;

import com.kushal.tinyurlgenerator.controller.TinyUrlController;
import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlErrorResponseDto;
import com.kushal.tinyurlgenerator.model.UrlRequestDto;
import com.kushal.tinyurlgenerator.model.UrlResponseDto;
import com.kushal.tinyurlgenerator.service.GenerateTinyUrlImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    GenerateTinyUrlImpl urlService;

    @InjectMocks
    TinyUrlController tinyUrlController;

    @Test
    public void checkTheHTTPResponseWhenCustomUrlAlreadyExists()
    {
        UrlRequestDto request = new UrlRequestDto("https://www.youtube.com/watch?v=ReS_JQIqB_I&ab_channel=Slyfer2812", "helloWorld", null);
        Url url = new Url();
        url.setId(-1);
        when(urlService.convertToShortUrl(request)).thenReturn(url);
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setError("The custom url already , Fret not my fren");
        urlErrorResponseDto.setStatus("420 xD");
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCodeValue(),208);
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCode(),HttpStatus.ALREADY_REPORTED);
        System.out.println(tinyUrlController.convertToShortUrl(request).getBody());
    }

    @Test
    public void checkTheHTTPResponseWhenUrlEnteredIsNullOrEmpty()
    {
        UrlRequestDto request = new UrlRequestDto("", "helloWorld", null);
        Url url = new Url();
        url.setId(420);
        url.setShortLink(null);
        url.setOriginalUrl(null);
        when(urlService.convertToShortUrl(request)).thenReturn(url);
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCodeValue(),400);
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCode(),HttpStatus.BAD_REQUEST);
        System.out.println(tinyUrlController.convertToShortUrl(request).getBody());
    }

    @Test
    public void checkTheHTTPResponseWhenUrlEnteredIsOK()
    {
        UrlRequestDto request = new UrlRequestDto("google.com", "helloWorld", null);
        Url url = new Url();
        url.setId(1);
        url.setShortLink("helloWorld");
        when(urlService.convertToShortUrl(request)).thenReturn(url);
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCodeValue(),200);
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCode(),HttpStatus.OK);
        System.out.println(tinyUrlController.convertToShortUrl(request).getBody());
    }

    @Test
    public void checkTheHTTPResponseWhenServiceIsDown()
    {
        UrlRequestDto request = new UrlRequestDto("google.com", "helloWorld", null);
        when(urlService.convertToShortUrl(request)).thenReturn(null);
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCodeValue(),503);
        Assert.assertEquals(tinyUrlController.convertToShortUrl(request).getStatusCode(),HttpStatus.SERVICE_UNAVAILABLE);
        System.out.println(tinyUrlController.convertToShortUrl(request).getBody());
    }


}
