package com.zerobase.fastlms.log.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessParam {
    private String accessIp;
    private String accessUserAgent;
}
