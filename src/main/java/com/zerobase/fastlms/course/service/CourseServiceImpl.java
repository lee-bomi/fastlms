package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput param) {

        Course course = Course.builder()
                .subject(param.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);
        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam param) {
        long totalCount = courseMapper.selectListCount(param);
        List<CourseDto> list = courseMapper.selectList(param);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }

    @Override
    public boolean set(CourseInput parameter) {

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }
        Course course = optionalCourse.get();
        course.setSubject(parameter.getSubject());
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }
}
