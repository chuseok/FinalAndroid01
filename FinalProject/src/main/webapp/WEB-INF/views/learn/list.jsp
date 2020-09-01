<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
  <title>암기용-Learn</title>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <link rel="stylesheet" href="/resources/css/main.css">
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>	
</head>
<body>

<%@ include file="../includes/header.jsp" %>

	<div class="HomeLayout">
		<div class="HomeLayout-container">
			<section class="HomeLayout-main">
			<h2>학습하기</h2>
				<div class="mainWrappesr">
					<div class="mainContents">
						<div class="mainContents-recentFeed">
							<div class="recentFeed-header">
								<h5>최근 학습한 단어장</h5>
							</div>
							<div class="recentFeed-cards">							

								<c:forEach items="${list }" var="word" varStatus="vs">
								<div class="recentFeed-cardItem">

									<div class="recentFeed-cardItem-inner">
										<span class="wordTitle"><c:out value="${word.title }" /></span>
										<span class="wordId"><c:out value="${word.id }" /></span>
									</div>
									
									<div class="recenFeed-cardItem-LinkBox">
										<a class='move-wordList' href='get?id=<c:out value="${word.id }" />&title=<c:out value="${word.title }" />'></a>
										<%-- <a class='move-wordList' href='<c:out value="${vs.index }" />'></a> --%>
									</div>
									</div>
								</c:forEach>
							
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	
<script>

	$(document).ready(function(){
		
		history.replaceState({},null,null)
		
	
	}); //document.ready.end

</script>

</body>
</html>