package com.infraops.infraops_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "servers")
public class Server {

    @Id
    private String id;
    
    private String name;
    private String ip;
    private String os;
    
    // Live metrics
    private String status; // UP, DOWN, WARNING
    private int cpu;
    private int memory;
    private int disk;
    private String uptime;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
