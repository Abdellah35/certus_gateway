package com.softedge.solution.service;


import com.softedge.solution.models.Permission;
import com.softedge.solution.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public void savePermission(Permission permission){
        permissionRepository.save(permission);
    }
}
