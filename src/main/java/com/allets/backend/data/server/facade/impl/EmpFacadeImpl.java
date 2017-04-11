package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.data.dto.EmpDeptDTO;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import com.allets.backend.data.server.facade.EmpFacade;
import com.allets.backend.data.server.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사원/부서 관리 서비스.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class EmpFacadeImpl  implements EmpFacade {

	
	/** 사원 레파지토리. */
	@Autowired
    EmpService empService;
	
	/**
	 * 사원/부서 정보를 조회 한다.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public Page<EmpDeptResult> findEmpDeptAll() throws Exception {
		return empService.selectByDeptnoList();
	}
	
	
	/**
	 * 사원 상세 정보를 조회 한다.
	 *
	 * @param empno the empno
	 * @return the emp
	 * @throws Exception the exception
	 */
	public Emp findEmpByEmpno (int empno) throws Exception  {
		return empService.selectByEmpno(empno);
	}
	
	/**
	 * 사원 정보 저장.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	@Transactional(value = "commonTxManager")
	public void createEmp (EmpDeptDTO empDeptDTO) throws Exception {
		empService.insertEmp(empDeptDTO.getEmp());
	}

	/**
	 * 사원 정보 수정.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	@Transactional(value = "commonTxManager")
	public void modifyEmp (EmpDeptDTO empDeptDTO) throws Exception {
		empService.updateEmp(empDeptDTO.getEmp());
	}

	/**
	 * Removes the emp.
	 *
	 * @param empDeptDTO the emp dept dto
	 * @throws Exception the exception
	 */
	@Transactional(value = "commonTxManager")
	public void removeEmp (Integer empno) throws Exception {
		empService.deleteEmp(empno);
	}
	
}
