package com.example.demo.Services;

import com.example.demo.Models.JobsResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class JobImpl implements IJobImpl {

    private final String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions";

    @Override
    public JobsResponse[] getListJobs(){
        JobsResponse[] resp = callAPIListJob(url);
        return resp;
    }

    @Override
    public JobsResponse getJob(String jobDetail){
        JobsResponse callAPIJob = callAPIJobDetail(url, jobDetail);
        return callAPIJob;
    }

    private JobsResponse[] callAPIListJob(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Host", "<calculated when request is sent>");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JobsResponse[]> responseEntity = restTemplate.exchange(url+".json", HttpMethod.GET, entity, JobsResponse[].class);
        JobsResponse[] resp = responseEntity.getBody();
        return resp;
    }

    private JobsResponse callAPIJobDetail(String url, String jobDetail){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Host", "<calculated when request is sent>");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JobsResponse> responseEntity = restTemplate.exchange(url+"/"+jobDetail, HttpMethod.GET, entity, JobsResponse.class);
        JobsResponse resp = responseEntity.getBody();
        return resp;
    }

}
