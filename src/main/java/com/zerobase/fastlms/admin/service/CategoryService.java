package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {
    /**
     * 카테고리 신규 추가
     * @param category
     * @return
     */
    boolean add(String category);

    /**
     * 카테고리 수정
     * @param parameter
     * @return
     */
    boolean update(CategoryInput parameter);

    /**
     * 카테고리 삭제
     * @param id
     * @return
     */
    boolean del(long id);

    /*
    * 카테고리 리스트 가져오기
    * */
    List<CategoryDto> list();
}
