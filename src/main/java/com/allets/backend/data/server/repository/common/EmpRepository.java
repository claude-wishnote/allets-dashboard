package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.Emp;
import org.springframework.data.repository.CrudRepository;

public interface EmpRepository extends CrudRepository<Emp, Integer>,
		EmpRepositoryCustom {

}
