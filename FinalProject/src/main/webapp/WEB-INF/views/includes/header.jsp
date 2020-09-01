<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>
<script src="/resources/jquery/jquery.validate.min.js"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap"
	rel="stylesheet">
</head>
<style>

.header__notification {
  width: 30px;
  /* height: 20px; */
  margin: 0 10px;
  display: -webkit-box;
  display: flex;
  justify-content: space-around;
}
.header__notification .bell-icon {
  position: relative;
  cursor: pointer;
}
.header__notification .bell-icon:hover {
  color: #eee;
}
.header__notification .bell-icon .notification-number {
  font-size: 0.6em;
  border-radius: 50%;
  background-color: #00BFBE;
  border: 2px solid #00BFBE;
  color: #FFFFFF;
}
.header__notification .bell-icon .bell-number {
  position: absolute;
  top: -50%;
  width: 15px;
  height: 15px;
  text-align: center;
  line-height: 15px;
  transform: translate(-30%, 70%);
}
.header__notification .bell-icon .notification__list {
  position: absolute;
  background: rgb(255 232 232);
  width: 230px;
  list-style-type: none;
  text-align: center;
  left: -90px;
  top: 50px;
  border-radius: 7px;
  -webkit-animation: fadeIn 1s;
  animation: fadeIn 1s;
  z-index: 30;
  border: 1px solid transparent;
}
.header__notification .bell-icon .notification__list:before {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-bottom-color: #fff;
  left: 48.5%;
  top: -20px;
  -webkit-transform: translate(-50%);
          transform: translate(-50%);
}
.header__notification .bell-icon .notification__list__name {
  background: #fff;
  color: #9BB7C5;
  padding: 10px 0;
  border-top-left-radius: 7px;
  border-top-right-radius: 7px;
  
  -webkit-transition: all 1s ease-in-out;
  transition: all 1s ease-in-out;
  font-size: 15px;
}
.header__notification .bell-icon .list__item {
  border-top: 1px solid rgb(214 156 156);
  -webkit-transition: all 1s ease-in-out;
  transition: all 1s ease-in-out;
}
.header__notification .bell-icon .list__item .user-image {
  width: 40px;
  height: 40px;
  border-radius: 50px;
}
.header__notification .bell-icon .list__item .messages {
  padding-left: 10px;
  color: #909DA8;
}
.header__notification .bell-icon .list__item--link {
  display: -webkit-box;
  display: flex;
  padding: 10px;
  text-decoration: none;
  text-align: left;
  font-size: 0.6em;
  opacity: 0.8;
  -webkit-box-align: center;
          align-items: center;
}
.header__notification .bell-icon .list__item--link:hover {
  opacity: 1;
}

.small-icon {
  font-size: 1.5em;
  fill: #D0DADF;
}
.hide {
  display: none;
}
.clicked {
  background-color: white;
}

@-webkit-keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/*무경씨꺼 코드 */
.menu a {
	cursor: pointer;
}

.menu .hide {
	display: none;
}
</style>
<body>

<header>
    <nav class="header-navbar">
      <div class="header-logo">
        <a href="">암기용</a>        
      </div>
      <div class="header-transitionGroup-Wrapper">

        <div class="header-searchSection">
          <button><i class="fas fa-search"></i> 찾아보기</button>
        </div>

        <div class="header-createSection">
          <a href="">
            <i class="fas fa-folder-plus"></i>만들기
          </a>
        </div>
        
        <div class="header__notification">
      		<div class="far fa-bell bell-icon small-icon">
        		<span class="notification-number bell-number">0</span>
        		<ul class="notification__list dropdown hide">
          			<h3 class="notification__list__name">Notifications</h3>
        		</ul>
      		</div> <!-- bell-icon-->
  		</div> <!--  header__notification -->
        
        <div class="header-userSection">
          <div class="userInfo">
            <span class="user-avatar"></span>
            <span class="user-name">
              <sec:authorize access="isAnonymous()">
               <a href="/customLogin">로그인</a>
              </sec:authorize>
            </span>
            
              <sec:authorize access="isAnonymous()">
               <a href="../main/join">회원가입</a>
              </sec:authorize>
            
            
              <sec:authorize access="isAuthenticated()" var="loginSuccess">
              <sec:authentication property="principal.member.userName"/>님
              </sec:authorize>
            
            
              <sec:authorize access="isAuthenticated()">
                 <ul>
                 <a><li class="menu"><i class="fas fa-bars"></i>메뉴</a>
				<ul class="hide">
				<li><a href="../main/update">회원정보변경</a></li>
				<li>친구추천</li>
				<li><a href="/withdrawal">회원탈퇴</a></li>
				<li><form action="/customLogout" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				<button>로그아웃</button>
				</form></li>
				</ul>
				</li>
				</ul>
              </sec:authorize>
            
          </div>
        </div>
      </div>
    </nav>
  </header>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js" 
integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g==" crossorigin="anonymous"></script>
<script type="text/javascript">
var bellIcon = document.querySelector('.bell-icon');
var dropdownMenu = document.querySelector('.dropdown');
var listItem = document.querySelector('.list__item');
var notificationNumber = document.getElementsByClassName('notification-number');
var notificationInterval = setInterval(checkNumber, 0);
var size = document.getElementsByClassName('list__item').length;
var text = '<c:out value="${text}"/>';
var hungry = '<c:out value="${hungry}"/>';
var clicked = '<c:out value="${clicked}"/>';

//sessionStorage.setItem("clickedItem", clicked);



var stompClient = null;

function bellCheck(event){
  var isClickInside = bellIcon.contains(event.target);
  
    if (isClickInside) {
      dropdownMenu.classList.toggle('hide');
      if(dropdownMenu.classList.contains('hide')){
    	var arr = document.querySelectorAll('.list__item');
    	for(var i=0;i<arr.length;i++){
    		arr[i].classList.add('clicked');
    	}
    	
        clearInterval(notificationInterval);
        notificationInterval = setInterval(checkNumber, 0);  
        sessionStorage.setItem("clickedItem", "clicked");
      }
      else{
      for(var i = 0; i < notificationNumber.length;i++){
         notificationNumber[i].textContent = size;
         clearInterval(notificationInterval);
      }
    }
    }
    else {
      dropdownMenu.classList.add('hide');
      clearInterval(notificationInterval);
      notificationInterval = setInterval(checkNumber, 5000);
    }
}
function checkNumber() {
	size = document.getElementsByClassName('list__item').length - 
	document.getElementsByClassName('clicked').length;
	for(var i = 0; i < notificationNumber.length;i++){
        notificationNumber[i].textContent = size;
	}
	
	
	
}
function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);  
    stompClient.connect({}, function(frame) {
        
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}
function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    
    console.log("Disconnected");
}

function sendMessage() {
	
    stompClient.send("/app/chat", {}, 
      JSON.stringify({'text':text,
      'clicked':sessionStorage.getItem("clickedItem")}));
    if(hungry!=""){
    	stompClient.send("/app/chat", {}, 
      	      JSON.stringify({'text':hungry,
      	      'clicked':sessionStorage.getItem("clickedItem")}));
    }
    
}

function showMessageOutput(messageOutput) {
    var response = document.getElementsByClassName('notification__list');
    //var li = document.createElement('li');
    for(var i=0;i<response.length;i++){
    	var str = '<li class="list__item '+messageOutput.clicked+'">'
    	    +'<a href="#" class="list__item--link">'
    	    +'<img src="https://cdn.tutsplus.com/net/uploads/legacy/213_chris/ChrisThumbnail.jpg" alt="" class="user-image" />'
    	    +'<span class="messages">'
    	    +messageOutput.text+' ('+messageOutput.time+')'
    	    +'</span></a></li>';
    	response.item(i).innerHTML +=str;
    }
    
}
window.addEventListener('click', bellCheck);

if(<c:out value="${loginSuccess}"/> == true&&size==0){
	connect();
	setTimeout(sendMessage, 1000);
	
}
$(document).ready(function(){
    // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
    $(".menu>a").click(function(){
        var submenu = $(this).next("ul");

        // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
        if( submenu.is(":visible") ){
            submenu.slideUp();
        }else{
            submenu.slideDown();
        }
    });
});
</script>
</html>