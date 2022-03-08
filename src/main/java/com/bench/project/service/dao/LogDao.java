package com.bench.project.service.dao;

import com.bench.project.service.domain.LogDto;

import java.util.List;

public interface LogDao {

    void save(LogDto dto);

    List<LogDto> getAll();
}
