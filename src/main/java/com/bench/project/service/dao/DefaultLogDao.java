package com.bench.project.service.dao;

import com.bench.project.service.domain.LogDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DefaultLogDao implements LogDao {

    private final ArrayList<LogDto> list = new ArrayList<>();

    @Override
    public void save(LogDto dto) {
        list.add(dto);
    }

    @Override
    public List<LogDto> getAll() {
        return list;
    }
}
