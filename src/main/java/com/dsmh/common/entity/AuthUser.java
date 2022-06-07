package com.dsmh.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * 用户实体
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    private Long id;

    private String userNo;

    private String username;

    private String nickname;

    private String realname;

    private String avatar;

    private String password;

    private String phone;

    private Integer age;

    private String email;

    private Boolean delFlag;


    /**
     * 备注
     */
    private String remarks;

    private Instant createTime;

    private Instant updateTime;



}
