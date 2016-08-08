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
	
	<h2>Employee Details</h2>
	
	<table >
		<tr>
			<td>Employee ID</td>
			<td>${empl.id}</td>
		</tr>
		<tr>
			<td>First Name</td>
			<td>${empl.fName}</td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td>${empl.lName}</td>
		</tr>
		<tr>
			<td>Department</td>
			<td>${empl.department.depName}</td>
		</tr>
		<tr>
			<td>Birth Date</td>
			<fmt:formatDate value='${empl.birthDate}' var="formattedDate"  type="date" pattern="MM-dd-yyyy" />
			<td>${formattedDate}</td>
		</tr>
		<tr>
			<td>Hire Date</td>
			<fmt:formatDate value="${empl.hireDate}" var="formattedDate2" 
 								type="date" pattern="MM-dd-yyyy" />
			<td>${formattedDate2}</td>
	   </tr>
	</table>
	<a href="/MVCSimulator/">Back</a>
</body>
</html>
