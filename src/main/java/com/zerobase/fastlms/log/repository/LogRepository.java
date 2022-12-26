package com.zerobase.fastlms.log.repository;

import com.zerobase.fastlms.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, String> {

}
