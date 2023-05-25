package com.example.web_projekat.services;

import com.example.web_projekat.entities.Role;
import com.example.web_projekat.repositories.dto.role.RoleDto;
import com.example.web_projekat.repositories.role.RoleRepository;

import javax.inject.Inject;
import java.util.List;

public class RoleService {

    @Inject
    private RoleRepository roleRepository;

    public Role createRole(RoleDto roleDto){
        return roleRepository.createRole(roleDto);
    }

    public List<Role> getAllRoles(){
        return roleRepository.getAllRoles();
    }

    public Role findRoleById(int id){
        return roleRepository.findRoleById(id);
    }

    public Role updateRole(int id, RoleDto roleDto){
        return roleRepository.updateRole(id, roleDto);
    }

    public void deleteRole(int id){
        roleRepository.deleteRole(id);
    }
}
