package com.example.demo.Services;

import com.example.demo.Models.JobsResponse;

public interface IJobImpl {
    JobsResponse[] getListJobs();

    JobsResponse getJob(String jobDetail);
}
