package com.example.web_projekat.repositories.role;

import com.example.web_projekat.entities.Role;
import com.example.web_projekat.repositories.dto.role.RoleDto;

import java.util.List;

public interface RoleRepository {

    Role createRole(RoleDto roleDto);

    List<Role> getAllRoles();

    Role findRoleById(int id);

    Role updateRole(int id, RoleDto roleDto);

    void deleteRole(int id);
}
