package com.correctin.demo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BaseResponse {

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private Boolean status;
}
