package com.ewisselectronic.sulama.sulamaservice.controller;

import com.ewisselectronic.sulama.sulamacore.model.SystemSettings;
import com.ewisselectronic.sulama.sulamacore.service.SystemSettingsService;
import com.ewisselectronic.sulama.sulamaservice.model.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@PreAuthorize("hasAuthority('ADMIN_USER')")
public class SystemSettingsController {

    @Autowired
    private SystemSettingsService systemSettingsService;

    @GetMapping(value = "/api/systemSettings/getAll", produces = "application/json")
    public ResponseEntity<?> getAll(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                         @RequestParam(value = "pageNumber", required = false) Optional<Integer> pageNumber,
                                         @RequestParam(value = "sortBy", required = false) Optional<String> sortBy,
                                         @RequestParam(value = "direction", required = false) Optional<String> direction,
                                         @RequestParam(value = "search", required = false) Optional<String> search) {
        Page<SystemSettings> systemSettingsPage = systemSettingsService.getAll(pageNumber.orElse(0), pageSize.orElse(10),
                sortBy.orElse("creationTime"), direction.orElse("DESC"), search.orElse(""));
        return ResponseEntity.ok(systemSettingsPage);
    }

    @GetMapping(value = "/api/systemSettings/get/{tag}", produces = "application/json")
    public SystemSettings get(@PathVariable("tag") String tag) {
        SystemSettings systemSettings = systemSettingsService.get(tag);
        return systemSettings;
    }

    @PostMapping(value = "/api/systemSettings/save", produces = "application/json")
    public SaveResponse save(@RequestBody SystemSettings systemSettings) {
        try {
            SystemSettings currentSettings = systemSettingsService.get(systemSettings.getTag());
            currentSettings.setValue(systemSettings.getValue());
            systemSettingsService.save(currentSettings);
            return new SaveResponse(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SaveResponse(false, e.getMessage(), null);
        }
    }

}
