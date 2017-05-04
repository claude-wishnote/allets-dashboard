package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>,
        UserRepositoryCustom {

}
