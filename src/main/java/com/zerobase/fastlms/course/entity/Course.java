package com.zerobase.fastlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    long categoryId;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents;

    long price;
    long salePrice;
    LocalDate saleEndDt;
    LocalDateTime regDt;
    LocalDateTime udtDt;
}
