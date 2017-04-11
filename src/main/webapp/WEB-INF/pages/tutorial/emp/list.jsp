<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/jsp-header.jspf"%>    
<table border="1px" cellpadding="0" cellspacing="0">
<thead>
<tr>
<th width="10%">empno</th><th width="15%">name</th><th width="10%">Department</th><th width="10%">actions</th>
</tr>
</thead>
<tbody>

<c:forEach items="${results.content}" var="item">
	<tr>
		<td>${item.empno}</td>
		<td>${item.ename}</td>
		<td>${item.dname}</td>
		<td>
		<a href="<c:url value="/tutorial/emp/edit"/>/${item.empno}">Edit</a><br>
		<a href="<c:url value="/tutorial/emp/delete"/>/${item.empno}">Delete</a><br>
		</td>	
	</tr>
</c:forEach>

</tbody>
</table>
<br/>
<br/>
<a href='<c:url value="/tutorial/emp/add"/>'>Create</a><br>