package com.zerobase.fastlms.log.service;

import com.zerobase.fastlms.log.entity.Log;
import com.zerobase.fastlms.log.model.AccessParam;
import com.zerobase.fastlms.log.model.LogParam;
import com.zerobase.fastlms.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;


    @Override
    public AccessParam checkData(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String agent = request.getHeader("User-Agent");
        return AccessParam.builder()
                .accessIp(ip)
                .accessUserAgent(agent)
                .build();
    }

    @Override
    public void save(LogParam logParam) {
        Log log = Log.builder()
                .loginId(logParam.getId())
                .loginDt(LocalDateTime.now())
                .accessIp(logParam.getAccessIp())
                .accessUserAgent(logParam.getAccessUserAgent())
                .build();
        logRepository.save(log);
    }

    @Override
    public Log getLogData(String id) {
        Optional<Log> byId = logRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        System.out.println("^^^^^^^^^^");
        System.out.println("byid ': " + byId);
        return byId.get();
    }
}
