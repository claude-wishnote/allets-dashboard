package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.entity.common.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>,
        UserRepositoryCustom {

}
