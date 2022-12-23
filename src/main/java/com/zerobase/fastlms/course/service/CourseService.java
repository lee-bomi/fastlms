package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CourseService {

    /**
     * 강좌등록
     * @param param
     * @return
     */
    boolean add(CourseInput param);

    /**
     * 강좌 리스트
     */
    List<CourseDto> list(CourseParam param);

}
