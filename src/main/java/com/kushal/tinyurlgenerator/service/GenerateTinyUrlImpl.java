package com.kushal.tinyurlgenerator.service;

import com.google.common.hash.Hashing;
import com.kushal.tinyurlgenerator.Repository.UrlRepository;
import com.kushal.tinyurlgenerator.model.Url;
import com.kushal.tinyurlgenerator.model.UrlRequestDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class GenerateTinyUrlImpl implements iGenerateTinyUrl {

    @Autowired
    private UrlRepository urlRepository;


    @Override
    public Url convertToShortUrl(UrlRequestDto request) {
        String tinyUrl = "";
        String localhostString = "localhost:8080/";
        if(request.getUrl() == "" || request.getUrl() == null)
        {
            Url url = new Url();
            url.setId(420);
            url.setShortLink(null);
            url.setOriginalUrl(null);
            return url;
        }
        if(StringUtils.isNotEmpty(request.getUrl()))
        {
            if(StringUtils.isBlank(request.getCustomTinyUrl()))
            {
                tinyUrl = encodeUrl(request.getUrl());
            }
            else
            {
                if(checkIfCustomUrlAlreadyExists(localhostString.concat(request.getCustomTinyUrl())))
                {
                    System.out.println("Url already exists");
                    Url url =  new Url();
                    url.setId(-1);
                    return url;
                }
                else
                {
                    tinyUrl = request.getCustomTinyUrl();
                }
            }

        }
        String finalUrl = localhostString.concat(tinyUrl);

        Url urlToPersist = new Url();
        setValuesForPersistUrl(urlToPersist,request, finalUrl);
        Url urlToRet = persistShortUrl(urlToPersist);
        return urlToRet;
    }

    private Url persistShortUrl(Url urlToPersist) {
        return urlRepository.save(urlToPersist);
    }

    private void setValuesForPersistUrl(Url url, UrlRequestDto request, String finalUrl) {
        url.setCreationDate(LocalDateTime.now());
        url.setOriginalUrl(request.getUrl());
        url.setShortLink(finalUrl);
        url.setExpirationDate(getExpirationDate(request.getExpirationDate(),url.getCreationDate()));
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expirationDate))
        {
            return creationDate.plusMinutes(2);
        }
        LocalDateTime expirationDateToReturn;
        try {
            expirationDateToReturn = LocalDateTime.parse(expirationDate);
        }
        catch (Exception E)
        {
            throw E;
        }

        return expirationDateToReturn;
    }

    private boolean checkIfCustomUrlAlreadyExists(String tinyUrl)
    {
        Url urlResp = urlRepository.findByShortLink(tinyUrl);
        if(urlResp != null)
            return true;
        else
            return false;
    }


    private String encodeUrl(String url) {
        String tinyUrl = "";
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("value of key in decoded form: "+ url.concat(currentTime.toString()));
        tinyUrl = Hashing.murmur3_32()
                .hashString(url.concat(currentTime.toString()), StandardCharsets.UTF_8)
                .toString();
        return tinyUrl;
    }

}
