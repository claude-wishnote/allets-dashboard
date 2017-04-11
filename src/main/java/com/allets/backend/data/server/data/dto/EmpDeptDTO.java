package com.allets.backend.data.server.data.dto;

import com.allets.backend.data.server.entity.common.Dept;
import com.allets.backend.data.server.entity.common.Emp;

/**
 * Instantiates a new emp dept dto.
 */
public class EmpDeptDTO {

	Emp emp;

	Dept dept;

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

}
