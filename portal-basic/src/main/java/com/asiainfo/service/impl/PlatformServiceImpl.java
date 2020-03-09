package com.asiainfo.service.impl;

import com.asiainfo.dao.PlatformDao;
import com.asiainfo.entity.Platform;
import com.asiainfo.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformDao platformDao;

    @Override
    public Platform save(Platform platform) {
        return platformDao.save(platform);
    }

    @Override
    public int update(Platform platform) {
        return platformDao.updatePlatform(platform);
    }

    @Override
    public void delete(Long id) {
        platformDao.deleteById(id);
    }

    @Override
    public Platform getPlatformById(Long id) {
        Optional<Platform> byId = platformDao.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public List<Platform> findAll() {
        Sort sort = Sort.by("opTime").descending();
        return (List<Platform>) platformDao.findAll(sort);
    }
}
