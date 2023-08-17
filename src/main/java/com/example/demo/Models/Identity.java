package com.example.demo.Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Identity {
    private int id;
    private String firstName;
    private String lastName;
}