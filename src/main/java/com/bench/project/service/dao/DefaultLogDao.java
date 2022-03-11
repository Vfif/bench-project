package com.bench.project.service.dao;

import com.bench.project.service.domain.LogDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DefaultLogDao implements LogDao {

    private final Map<String, List<LogDto>> storage = new HashMap<>();

    @Override
    public void save(LogDto dto) {
        if (storage.containsKey(dto.id())) {
            storage.get(dto.id()).add(dto);
            return;
        }

        storage.put(dto.id(), List.of(dto));
    }

    @Override
    public List<LogDto> getAll() {
        return storage.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<LogDto> getByRequestId(String requestId) {
        return storage.get(requestId);
    }

    @Override
    public LogDto getById(String requestId) {
        return storage.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(log -> log.id().equals(requestId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}
