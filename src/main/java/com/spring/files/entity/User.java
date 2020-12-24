package com.spring.files.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
//Accessor的中文含义是存取器
// @Accessors用于配置getter和setter方法的生成结果
// chain的中文含义是链式的，设置为true，则setter方法返回当前对象。
public class User {
    private int id;
    private String username;
    private String password;
}
