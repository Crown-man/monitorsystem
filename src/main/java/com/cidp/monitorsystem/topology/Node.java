package com.cidp.monitorsystem.topology;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/1012:42
 **/
@Data
public class Node {
    private String ip;
    private Integer id;
    private Integer parentId;
}
