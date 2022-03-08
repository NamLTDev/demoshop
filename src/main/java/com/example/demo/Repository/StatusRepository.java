package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
