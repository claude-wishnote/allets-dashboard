<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/jsp-header.jspf"%>    
<form:form method="POST" modelAttribute="emp">
<table>
<tbody>
	<tr>
		<td>Name:</td>
		<td><form:input path="ename"/></td>
	</tr>
	<tr>
		<td>Department:</td>
		<td><form:input path="deptno" value="10" readonly="true"/></td>
	</tr>
	<tr>
		<td><input value="Add" type="submit"></td>
		<td></td>
	</tr>
</tbody>
</table>
</form:form>
