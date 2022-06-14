package com.kushal.tinyurlgenerator.controller;


import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlErrorResponseDto;
import com.kushal.tinyurlgenerator.model.UrlRequestDto;
import com.kushal.tinyurlgenerator.model.UrlResponseDto;
import com.kushal.tinyurlgenerator.service.iDeleteTinyUrl;
import com.kushal.tinyurlgenerator.service.iGenerateTinyUrl;
import com.kushal.tinyurlgenerator.service.iRetrieveOriginalUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class TinyUrlController {

    @Qualifier("generateTinyUrlImpl")
    @Autowired
    iGenerateTinyUrl generateTinyUrl;

    @Autowired
    iRetrieveOriginalUrl iRetrieveOriginalUrl;

    @Qualifier("deleteTinyUrlImpl")
    @Autowired
    iDeleteTinyUrl deleteTinyUrl;

    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("/generateShortUrl")
    public ResponseEntity<?>  convertToShortUrl(@RequestBody UrlRequestDto request) {
        Url urlResp = generateTinyUrl.convertToShortUrl(request);
        if(urlResp!= null )
        {
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setExpirationDate(urlResp.getExpirationDate());
            urlResponseDto.setOriginalLink(urlResp.getOriginalUrl());
            urlResponseDto.setShortLink(urlResp.getShortLink());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        if(urlResp == null)
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("The custom url already , Fret not my fren");
            urlErrorResponseDto.setStatus("420 xD");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setError("I am sorry my friend, the url generation was not possible but fret not my friend. Try it again.");
        urlErrorResponseDto.setStatus("404");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
    }

    @GetMapping("/val")
    public String getUrl()
    {
        return "Hi";
    }

    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "/{tinyUrl}")
    public ResponseEntity<?> getAndRedirect(@PathVariable String tinyUrl, HttpServletResponse response) throws IOException
    {
        String localhostString = "localhost:8080/";
        if(StringUtils.isEmpty(tinyUrl))
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Invalid Url");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }

        Url originalUrlObject = iRetrieveOriginalUrl.retrieveOriginalUrl(localhostString.concat(tinyUrl));

        if(originalUrlObject == null)
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }

        if(originalUrlObject.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            deleteTinyUrl.deleteTinyUrl(originalUrlObject);
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
            urlErrorResponseDto.setStatus("200");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }
        System.out.println("Value of Original url: "+ originalUrlObject.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrlObject.getOriginalUrl()))
                .build();
    }


}

