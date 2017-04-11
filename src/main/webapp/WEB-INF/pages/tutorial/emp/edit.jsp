<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/jsp-header.jspf"%>    
<form:form method="POST" modelAttribute="emp" action="/tutorial/emp/edit/${emp.empno}">
<table>
<tbody>
	<tr>
		<td>No:</td>
		<td>${emp.empno}</td>
	</tr>
	<tr>
		<td>Name:<form:input path="ename"/></td>
		<td></td>
	</tr>
	<tr>
		<td><input value="Edit" type="submit"></td>
		<td></td>
	</tr>
</tbody>
</table>
<form:hidden path="deptno" />
</form:form>
