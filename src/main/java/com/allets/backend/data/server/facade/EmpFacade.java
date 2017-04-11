package com.allets.backend.data.server.facade;


import com.allets.backend.data.server.data.dto.EmpDeptDTO;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import org.springframework.data.domain.Page;

/**
 * 사원/부서 관리 서비스.
 */
public interface EmpFacade {

	
	/**
	 * 사원/부서 정보를 조회 한다.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	Page<EmpDeptResult> findEmpDeptAll() throws Exception;
	
	
	/**
	 * 사원 상세 정보를 조회 한다.
	 *
	 * @param empno the empno
	 * @return the emp
	 * @throws Exception the exception
	 */
	Emp findEmpByEmpno(int empno) throws Exception ;
	
	/**
	 * 사원 정보 저장.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	void createEmp(EmpDeptDTO empDeptDTO) throws Exception ;

	/**
	 * 사원 정보 수정.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	void modifyEmp(EmpDeptDTO empDeptDTO) throws Exception;

	/**
	 * Removes the emp.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	void removeEmp(Integer empno) throws Exception;
	
}
