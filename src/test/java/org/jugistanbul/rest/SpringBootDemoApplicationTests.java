package org.jugistanbul.rest;


import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringBootDemoApplicationTests 
{   
    @LocalServerPort
    int randomServerPort;
    
    //Timeout value in milliseconds
    int timeout = 10_000;
    
    public RestTemplate restTemplate;
    
    @Before
    public void setUp() {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
    }
    
    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                          = new HttpComponentsClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(timeout);
        
        //Read timeout
        clientHttpRequestFactory.setReadTimeout(timeout);
        return clientHttpRequestFactory;
    }
    

    @Test(expected = HttpClientErrorException.class)
    public void testGetEmployeeList_success() throws URISyntaxException 
    {
        final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("employeeList"));
    }
}
