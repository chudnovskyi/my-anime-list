package com.myanimelist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {

	Optional<Role> findByName(String name);
}
