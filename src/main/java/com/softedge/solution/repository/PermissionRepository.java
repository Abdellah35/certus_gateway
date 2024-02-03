package com.softedge.solution.repository;

import com.softedge.solution.models.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {
}
