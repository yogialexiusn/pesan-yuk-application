package com.example.demo.Services;

import com.example.demo.Models.CodeResponse;
import com.example.demo.Models.JobsResponse;

import java.util.List;
import java.util.Map;

public interface IJobImpl {
    JobsResponse[] getListJobs();

    JobsResponse getJob(String jobDetail);

    CodeResponse getCode();

    List<Map<String, Object>> getCodeWithHashMap(List<Map<String, String>> inputList);
}
