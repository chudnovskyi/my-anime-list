package com.myanimelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {

	Role findByName(String name);
}
