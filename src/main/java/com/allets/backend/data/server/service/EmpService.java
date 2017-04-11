package com.allets.backend.data.server.service;

import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * The interface Emp dept service.
 */
@Service
public interface EmpService {

	/**
	 * 사원/부서 조회.
	 *
	 * @return the list
	 * @throws Exception the exception
     */
	Page<EmpDeptResult> selectByDeptnoList() throws Exception;

	/**
	 * 사원 상세 조회.
	 *
	 * @param empno the empno
	 * @return the emp
	 * @throws Exception the exception
     */
	Emp selectByEmpno(int empno) throws Exception;

	/**
	 * 사원 저장.
	 *
	 * @param emp the emp
	 * @throws Exception the exception
     */
	Emp insertEmp(Emp emp) throws Exception ;

	/**
	 * 사원 수정.
	 *
	 * @param emp the emp
	 * @throws Exception the exception
     */
	void updateEmp(Emp emp) throws Exception;

	/**
	 * 사원 삭제.
	 *
	 * @param empno the empno
	 * @throws Exception the exception
     */
	void deleteEmp(int empno) throws Exception ;

	
}
