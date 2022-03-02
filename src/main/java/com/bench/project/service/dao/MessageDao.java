package com.bench.project.service.dao;

import com.bench.project.service.domain.MessageDto;

public interface MessageDao {

    void save(MessageDto message);
}
