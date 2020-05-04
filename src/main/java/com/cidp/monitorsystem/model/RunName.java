package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class RunName {
    private Integer id;
    private List<String> hrSWRunName; //系统运行的进程列表
}
