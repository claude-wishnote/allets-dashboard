package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import org.springframework.data.domain.Page;

public interface EmpRepositoryCustom {

	/**
	 * 사원 과 부서 정보 조인 리스트 .
	 *
	 * @param deptno the deptno
	 * @return the list
	 * @throws Exception the exception
	 */
	Page<EmpDeptResult> findListByDeptno() throws Exception;
	
	void updateName(Emp emp) throws Exception;
	
}
