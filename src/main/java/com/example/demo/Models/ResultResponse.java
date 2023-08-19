package com.example.demo.Models;

import java.util.List;

public class ResultResponse {

 private String location;
 private List<JobsResponse> data;

 public String getLocation() {
  return location;
 }

 public void setLocation(String location) {
  this.location = location;
 }

 public List<JobsResponse> getData() {
  return data;
 }

 public void setData(List<JobsResponse> data) {
  this.data = data;
 }
}
