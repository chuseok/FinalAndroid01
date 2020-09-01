<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/css/main02.css" />" rel="stylesheet">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<h1>Drag and Drop List </h1>
<p id="json"></p>
<form id="wordForm" method="post">
<p>단어장 제목 : <input type="text" name="word-title"></p>
<ul>
  <li id="1">
    <div> <span class = "up"></span><span class = "down"></span> </div>
    <input type="text" name="word" style="width:200px;"/>
    <input type="text" name="meaning" style="width:200px;"/>
  </li>
  <li id="2">
    <div> <span class = "up"></span><span class = "down"></span> </div>
    <input type="text" name="word" style="width:200px;"/>
    <input type="text" name="meaning" style="width:200px;"/>
  </li>
  <li id="3">
    <div> <span class = "up"></span><span class = "down"></span> </div>
    Item 3
  </li>
  <li id="4">
    <div> <span class = "up"></span><span class = "down"></span> </div>
    Item 4
  </li>
  <li id="5">
    <div> <span class = "up"></span><span class = "down"></span> </div>
    Item 5
  </li>
</ul>
</form>
<button data-oper="save">save</button>
<script src="<c:url value="/resources/js/sortable2.js" />"></script>
</body>
</html>