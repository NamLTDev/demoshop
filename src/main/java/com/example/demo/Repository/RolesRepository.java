package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
	@Query("SELECT r FROM Roles r WHERE r.roleName = ?1")
	Roles findRoleByRoleName(String RoleName);
}
