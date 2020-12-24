package com.spring.files.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)

public class UserFile {
    private int id;
    private int userId;
    private String oldFileName;
    private String newFileName;
    private String ext;
    private String path;
    private long size;
    private String type;
    private boolean isImg;
    private int downCounts;
    private Date uploadTime;

}
