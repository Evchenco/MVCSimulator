<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<style>
		td{
		    padding: 4px;
	   		border: solid 1px;
   		}
	</style>
</head>
<body>
	
	<h2>All Employees</h2>
	
	<table >
		<tr>
			<th></th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Department</th>
			<th>Birth Date</th>
			<th>Hire Date</th>
			<th></th>
	   </tr>
		<c:forEach var="empl" items="${allEmp}" varStatus="captionId">
			<tr>
				<td>${captionId.index+1}</td>
				<td>${empl.fName}</td>
				<td>${empl.lName}</td>
				<td>${empl.department.depName}</td>
				
				<fmt:formatDate value='${empl.birthDate}' var="formattedDate"  type="date" pattern="MM-dd-yyyy" />
				<td>${formattedDate}</td>
				
				<fmt:formatDate value="${empl.hireDate}" var="formattedDate2" 
 								type="date" pattern="MM-dd-yyyy" />
				<td>${formattedDate2}</td>
				<td><a href="/MVCSimulator/empl/${empl.id}">DETAILS</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
