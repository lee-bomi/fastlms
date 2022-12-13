package com.zerobase.fastlms.member.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class MemberInput {

    private String userId;
    private String userName;
    private String password;
    private String phone;

}
