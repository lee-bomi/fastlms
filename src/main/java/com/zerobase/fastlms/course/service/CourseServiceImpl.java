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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public boolean add(CourseInput param) {

        LocalDate saleEndDt = getLocalDate(param.getSaleEndDt());


        Course course = Course.builder()
                .subject(param.getSubject())
                .categoryId(param.getCategoryId())
                .keyword(param.getKeyword())
                .summary(param.getSummary())
                .contents(param.getContents())
                .price(param.getPrice())
                .salePrice(param.getSalePrice())
                .saleEndDt(saleEndDt)
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
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDt());
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }
        Course course = optionalCourse.get();
        course.setSubject(parameter.getSubject());
        course.setCategoryId(parameter.getCategoryId());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(saleEndDt);
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }
}
