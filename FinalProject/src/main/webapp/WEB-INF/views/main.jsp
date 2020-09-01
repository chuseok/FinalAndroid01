<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>암기용-Home</title>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
</head>
<body>
  
<%@ include file="includes/header.jsp" %>

  <div class="HomeLayout">
    <div class="HomeLayout-container">
  <section class="HomeLayout-sidebar">
    <div class="sidebar-navSection">
      <div class="sidenav-menuItem-home">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
            <i class="fas fa-home"></i>홈</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
             <i class="fas fa-search"></i>다이어그램 찾아보기</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="fas fa-thumbs-up"></i>친구 추천</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="fas fa-cog"></i>설정</span></a></span>
      </div>
    </div>

    <div class="sidebar-navSection">
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
            <i class="fas fa-th-large"></i>세트</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="far fa-folder"></i>폴더</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem-extend">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="fas fa-plus"></i>폴더 만들기</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="fas fa-user-friends"></i>클래스</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem-extend">
          <a href="">
            <span class="UlmenuItem-inner">
              <i class="fas fa-plus"></i>클래스 만들기/참여하기</span></a></span>
      </div>
    </div>
    <div class="sidebar-navSection">
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="/dragon/dragonPanel">
            <span class="UlmenuItem-inner">
            <i class="fas fa-th-large"></i>용키우기</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="/shop/shop">
            <span class="UlmenuItem-inner">
              <i class="far fa-folder"></i>상점</span></a></span>
      </div>
      <div class="sidenav-menuItem">
        <span class="UlmenuItem">
          <a href="/dragon/ranking">
            <span class="UlmenuItem-inner">
              <i class="far fa-folder"></i>랭킹</span></a></span>
      </div>
    </div>
    
    
    <div class="sidebar-footer">
      <div><a href="">업그레이드</a></div>
      <div><a href="">지원센터</a></div>
      <div><a href="">개인정보 취급방침</a></div>
      <div class="social-icons">
        <i class="fab fa-twitter"></i>
        <i class="fab fa-instagram"></i>
        <i class="fab fa-facebook-f"></i>
      </div>
      </div>
  </section>
  <section class="HomeLayout-main">
    <div class="mainWrapper">
      <div class="mainContents">
        <div class="mainContents-recentFeed">
          <div class="recentFeed-header">
            <h5>최근 학습함</h5>
          </div>
          <div class="recentFeed-cards">
            <div class="recentFeed-cardItem">카드1</div>
            <div class="recentFeed-cardItem">카드2</div>
            <div class="recentFeed-cardItem">카드3</div>
            <div class="recentFeed-cardItem">카드4</div>
          </div>
        </div>
        <div class="mainContents-recommand">
          <div class="recommand-header">
            <h5>추천하는 단어장</h5>
          </div>
          <div class="recommand-cards">
            <div class="recommand-cardItem">카드1</div>
            <div class="recommand-cardItem">카드2</div>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
</div>
</body>
</html>