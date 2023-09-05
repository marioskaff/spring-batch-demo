package com.dev.community.springbatchdemo.dal.jpa.repositories;

import com.dev.community.springbatchdemo.dal.jpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
