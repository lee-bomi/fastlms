package com.zerobase.fastlms.log.service;

import com.zerobase.fastlms.log.entity.Log;
import com.zerobase.fastlms.log.model.AccessParam;
import com.zerobase.fastlms.log.model.LogParam;
import com.zerobase.fastlms.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LogService {

    /**
     * login데이터 수집하기
     */
    AccessParam checkData(HttpServletRequest request);

    /**
     * log 저장하기
     */
    void save(LogParam logParam);

    /**
     * log 리스트 가져오기
     */
    Log getLogData(String id);
}
