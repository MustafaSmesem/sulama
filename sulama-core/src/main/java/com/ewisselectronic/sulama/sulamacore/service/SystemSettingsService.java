package com.ewisselectronic.sulama.sulamacore.service;

import com.ewisselectronic.sulama.sulamacore.annotation.AspectLogged;
import com.ewisselectronic.sulama.sulamacore.model.SystemSettings;
import com.ewisselectronic.sulama.sulamacore.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AspectLogged
public class SystemSettingsService {

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    public SystemSettings serviceGet(String tag) {
        return systemSettingsRepository.findByTag(tag);
    }

    public SystemSettings get(String tag) {
        return systemSettingsRepository.findByTag(tag);
    }

    public boolean save(SystemSettings systemSettings) {
        systemSettingsRepository.save(systemSettings);
        return true;
    }

    public boolean update(SystemSettings systemSettings) {
        systemSettingsRepository.save(systemSettings);
        return true;
    }

    public Page<SystemSettings> getAll(Integer pageNo, Integer pageSize, String sortBy, String direction, String search) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.valueOf(direction), sortBy));
        if(search == null || search == "") {
            return systemSettingsRepository.findAllBy(paging);
        } else {
            search = "%" + search + "%";
            return systemSettingsRepository.findByAnyColumnContaining(search, paging);
        }
    }

}
