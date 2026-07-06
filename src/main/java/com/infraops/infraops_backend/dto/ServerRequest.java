package com.infraops.infraops_backend.dto;

import lombok.Data;

@Data
public class ServerRequest {
    private String name;
    private String ip;
    private String os;
}
