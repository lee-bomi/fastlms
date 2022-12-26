package com.zerobase.fastlms.log.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogParam {

    String id;
    LocalDateTime loginDt;
    String accessIp;
    String accessUserAgent;
}
