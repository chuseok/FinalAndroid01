<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="menu.jsp"/>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<c:forEach items="${home1}" var="main">
	<table>
							<tr>
								<td><c:out value="${main.userId}" /></td>
								<td><c:out value="${main.userPwd}" /></td>
								<td><c:out value="${main.userName}" /></td>
								<td><c:out value="${main.birthday}" /></td>
								<td><c:out value="${main.phoneNo}" /></td>
								<td><c:out value="${main.email}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${main.lastConn}" /></td>
							</tr>
						</c:forEach>
   </table>
</body>
</html>