package com.allets.backend.data.server.entity.common;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "EMP")
public class Emp implements java.io.Serializable {

	private static final long serialVersionUID = -4420012506729107357L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "empno", unique = true, nullable = false)
	Integer empno;

	@Column(name = "ename", length = 20)
	String ename;
	@Column(name = "job", length = 20)
	String job;

	@Column(name = "mgr")
	Short mgr;

	@Temporal(TemporalType.DATE)
	@Column(name = "hiredate", length = 10)
	Date hiredate;

	@Column(name = "sal", precision = 7)
	BigDecimal sal;

	@Column(name = "comm", precision = 7)
	BigDecimal comm;

	@Column(name = "deptno")
	Integer deptno;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "deptno", nullable = false, insertable = false, updatable = false)
	@JsonBackReference
	private Dept dept;

	public Emp() {
	}

	public Emp(String ename, String job, Short mgr, Date hiredate,
			BigDecimal sal, BigDecimal comm, Integer deptno) {
		this.ename = ename;
		this.job = job;
		this.mgr = mgr;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
	}

	public Integer getEmpno() {
		return this.empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Short getMgr() {
		return this.mgr;
	}

	public void setMgr(Short mgr) {
		this.mgr = mgr;
	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public BigDecimal getSal() {
		return this.sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}

	public BigDecimal getComm() {
		return this.comm;
	}

	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}

	public Integer getDeptno() {
		return this.deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	// JPA Object override equals
	public boolean equals(Object object) {

		if (object == null)
			return false;

		if (!(object instanceof Emp))
			return false;

		Emp emp = (Emp) object;

		if (getEmpno() != emp.getEmpno())
			return false;

		return true;
	}

}
