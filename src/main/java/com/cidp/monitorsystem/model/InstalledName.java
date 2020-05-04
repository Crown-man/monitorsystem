package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class InstalledName {
    private String id;//所属服务器
    private List<String> hrSWInstalledName;//系统安装的软件列表

}
