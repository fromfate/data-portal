package com.asiainfo.service;

import com.asiainfo.entity.Platform;

import java.util.List;

public interface PlatformService {

    Platform save(Platform platform);

    int update(Platform platform);

    void delete(Long id);

    Platform getPlatformById(Long id);

    List<Platform> findAll();
}
