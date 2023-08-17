package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repositories.RedisRepository.ProductDao;
import org.json.simple.JSONValue;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobImpl implements IJobImpl {

    @Autowired
    private ProductDao redisJobResponse;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(JobImpl.class);

    private final String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions";

    @Override
    public JobsResponse[] getListJobs(){
        JobsResponse[] resp = callAPIListJob(url);
        Logger.info("cek {}, ",JSONValue.toJSONString(resp));
        return resp;
    }

    @Override
    public JobsResponse getJob(String jobDetail){
        JobsResponse jobResponse = redisJobResponse.findJobResponseById(jobDetail);
        Logger.info("cek dari redis{}, ",JSONValue.toJSONString(jobResponse));
        if(jobResponse==null){
            jobResponse = callAPIJobDetail(url, jobDetail);
            Logger.info("cek dari DB{}, ",JSONValue.toJSONString(jobResponse));
            redisJobResponse.save(jobResponse);
        }
        return jobResponse;
    }

    @Override
    public CodeResponse getCode(){
        CodeResponse codeResponse = makeCodeResponseRaihan();
        return codeResponse;
    }

//    private CodeResponse makeCodeResponseRaihan() {
//        JobsResponse[] request = callAPIListJob(url);
//
//
//        List<JobsResponse> requestList = Arrays.asList(request);
//        CodeResponse codeResponse = new CodeResponse();
//        DataResponse dataResponse = new DataResponse();
//
//        HashMap<String,List<JobsResponse>> resultListResponse = new HashMap<>();
//        List<JobsResponse> jobsResponseLIst = new ArrayList<>();
//        for (JobsResponse response : requestList) {
//                if(resultListResponse.get(response.getLocation()) !=null) {
//                    System.out.println("location 2 = " + response.getLocation());
//                    List<JobsResponse> ulala = resultListResponse.get(response.getLocation());
//                    System.out.println("Valuee = " + ulala);
//                    ulala.add(response);
//                    resultListResponse.put(response.getLocation(), ulala);
//                } else {
//                    System.out.println("location 3 = " + response.getLocation());
//                    List<JobsResponse> ulala = new ArrayList<>();
//                    ulala.add(response);
//                    resultListResponse.put(response.getLocation(),ulala);
//                }
////            }
//
//        }
//
//
//        dataResponse.setResult(resultListResponse);
//
//        codeResponse.setData(dataResponse); //
//        codeResponse.setCode(0); //
//        codeResponse.setStatus("ok");  //
//
//
//        return codeResponse;
//
//    }

    private CodeResponse makeCodeResponseRaihan() {
        CodeResponse codeResponse = new CodeResponse();
        JobsResponse[] resp = callAPIListJob(url);

        List<ResultResponse> resultListResponse = new ArrayList<>();
        ResultResponse resultResponse = null;

        List<JobsResponse> convert = Arrays.asList(resp);

        List<String> doubleLocation = new ArrayList<>();
        List<JobsResponse> jobsListResponse = null;

        for (JobsResponse re2 : convert) {
            if(!doubleLocation.contains(re2.getLocation())){
                doubleLocation.add(re2.getLocation());
                jobsListResponse = new ArrayList<>();
                jobsListResponse.add(re2);
                resultResponse = new ResultResponse();

            } else{
                jobsListResponse.add(re2);
            }
            resultResponse.setLocation(re2.getLocation());
            resultResponse.setData(jobsListResponse);
            resultListResponse.add(resultResponse);
        }

        List<ResultResponse> newList = resultListResponse.stream()
                .distinct()
                .collect(Collectors.toList());



        DataResponse dataResponse = new DataResponse();
        dataResponse.setResult(newList);


        codeResponse.setData(dataResponse); //
        codeResponse.setCode(0); //
        codeResponse.setStatus("ok");  //


        return codeResponse;

    }

    @Override
    public List<Map<String, Object>> getCodeWithHashMap(List<Map<String, String>> inputList){

        Map<String, String> job1 = new HashMap<>();
        job1.put("id", "1");
        job1.put("type", "Full Time");
        job1.put("url", "https://jobs.github.com/positions/xxx");
        job1.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job1.put("location", "Remote");
        inputList.add(job1);

        Map<String, String> job2 = new HashMap<>();
        job2.put("id", "2");
        job2.put("type", "Full Time");
        job2.put("url", "https://jobs.github.com/positions/xxx");
        job2.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job2.put("location", "Remote");
        inputList.add(job2);

        Map<String, String> job3 = new HashMap<>();
        job3.put("id", "3");
        job3.put("type", "Full Time");
        job3.put("url", "https://jobs.github.com/positions/xxx");
        job3.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job3.put("location", "Belanda");
        inputList.add(job3);

        Map<String, String> job4 = new HashMap<>();
        job4.put("id", "4");
        job4.put("type", "Full Time");
        job4.put("url", "https://jobs.github.com/positions/xxx");
        job4.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job4.put("location", "Belanda");
        inputList.add(job4);

        Map<String, String> job5 = new HashMap<>();
        job5.put("id", "5");
        job5.put("type", "Full Time");
        job5.put("url", "https://jobs.github.com/positions/xxx");
        job5.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job5.put("location", "Jakarta");
        inputList.add(job5);

        Map<String, String> job6 = new HashMap<>();
        job6.put("id", "6");
        job6.put("type", "Full Time");
        job6.put("url", "https://jobs.github.com/positions/xxx");
        job6.put("created_at", "Wed May 19 00:49:17 UTC 2021");
        job6.put("location", "Jakarta");
        inputList.add(job6);


        // Create a HashMap to group data by location
        Map<String, List<JobDTO>> locationData = new HashMap<>();

        // Group data by location
        for (Map<String, String> item : inputList) {
            String location = item.get("location");
            JobDTO dto = new JobDTO(item.get("id"), item.get("type"), item.get("url"), item.get("created_at"));

            locationData.computeIfAbsent(location, k -> new ArrayList<>()).add(dto);
        }

        // Create the final output structure
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map.Entry<String, List<JobDTO>> entry : locationData.entrySet()) {
            Map<String, Object> resultItem = new HashMap<>();
            resultItem.put("location", entry.getKey());
            List<JobDTO> data = entry.getValue();
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (JobDTO dto : data) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", dto.getId());
                dataMap.put("type", dto.getType());
                dataMap.put("url", dto.getUrl());
                dataMap.put("created_at", dto.getCreated_at());
                dataList.add(dataMap);
            }
            resultItem.put("data", dataList);
            resultList.add(resultItem);
        }

        // Create the final object
        Map<String, Object> outputObject = new HashMap<>();
        outputObject.put("status", "ok");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("result", resultList);
        outputObject.put("data", dataMap);
        outputObject.put("code", 0);

        return (List<Map<String, Object>>) outputObject;
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
