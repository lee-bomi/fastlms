package com.zerobase.fastlms.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="login_history")
public class Log {
    @Id
    private String loginId;
    private LocalDateTime loginDt;
    private String accessIp;
    private String accessUserAgent;

}
