
package com.allets.backend.data.server.entity.common;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 부서 샘플 엔티티.
 */

@Entity
@Table(name = "DEPT")
public class Dept implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "deptno", unique = true, nullable = false)
	Integer deptno;

	@Column(name = "dname", length = 20)
	String dname;

	@Column(name = "loc", length = 20)
	String loc;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = false, mappedBy = "dept")
	@JsonManagedReference
	private Collection<Emp> emps = new ArrayList<Emp>();

	public Dept() {
	}

	public Dept(String dname, String loc) {
		this.dname = dname;
		this.loc = loc;
	}

	public Integer getDeptno() {
		return this.deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Collection<Emp> getEmps() {
		return emps;
	}

	public void setEmps(Collection<Emp> emps) {
		this.emps = emps;
	}

	// JPA Object override equals
	public boolean equals(Object object) {

		if (object == null)
			return false;

		if (!(object instanceof Dept))
			return false;

		Dept dept = (Dept) object;

		if (getDeptno() != dept.getDeptno())
			return false;

		return true;
	}
}
