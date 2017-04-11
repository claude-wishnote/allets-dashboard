package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import com.allets.backend.data.server.entity.common.QDept;
import com.allets.backend.data.server.entity.common.QEmp;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmpRepositoryImpl implements EmpRepositoryCustom {

	@PersistenceContext(unitName = "commonUnit")
	EntityManager entityManager;

	@Override
	public Page<EmpDeptResult> findListByDeptno() throws Exception {

		JPAQuery countQuery = new JPAQuery(entityManager);
		JPAQuery listQuery = new JPAQuery(entityManager);

		QEmp emp = QEmp.emp;
		QDept dept = QDept.dept;

		int offset = 0;
		int limit = 100;

		// count query
		countQuery.from(emp).innerJoin(emp.dept, dept);

		Long count = 0L;
		count = countQuery.count();

		// list query
		listQuery.from(emp).innerJoin(emp.dept, dept).orderBy(emp.empno.desc())
				.offset(offset).limit(limit);

		List<EmpDeptResult> results = listQuery.list(Projections.fields(
				EmpDeptResult.class, emp.empno.as("empno"), emp.ename,
				dept.deptno, dept.dname));
		Pageable pageable = new PageRequest(offset, limit);

		return new PageImpl<EmpDeptResult>(results, pageable, count);
	}

	@Transactional(value = "commonTxManager")
	@Override
	public void updateName(Emp emp) throws Exception {

		QEmp qEmp = QEmp.emp;
		JPAUpdateClause empUpdate = new JPAUpdateClause(entityManager, qEmp);

		empUpdate
		.where(qEmp.empno.eq(emp.getEmpno()))
		.set(qEmp.ename, emp.getEname()).execute();
	}

}
