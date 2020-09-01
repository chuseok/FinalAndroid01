<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="_csrf" content="${_csrf.token}">
  <meta name="_csrf_header" content="${_csrf.headerName}">
  <title>암기용-dragon</title>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="/resources/css/dragon/dragonPanel.css">
</head>
<style type="text/css">
</style>
<body>
  
<%@ include file="../includes/header.jsp" %>

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
              <i class="far fa-folder"></i>상점</span></a></span>
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
    <div id="panel">
    
		<div id="dragonBackground" style="background-image: url(${background.productImage})">
		<div id="overlay">
		<img alt="" src="../resources/images/dead.svg">
			<h2>용이 죽었습니다!</h2>
			<div class="dead_btn">
				<button class="chooseDragon">다른 용 선택</button>
				<button id="reviveDragon">부활시키기</button>
			</div>
		</div>
		<c:if test="${noDragon}">
		<div id="alertNoDragon">
		<img alt="" src="../resources/images/alertIcon.svg">
			<h2>용이 없습니다!</h2>
			<div class="noDragon_btn">
				<button class="goshop">구입하러 가기</button>
			</div>
		</div>
		</c:if>
		<div class="modal-wrapper">
		<div class="modal">
			<div class="content">
				<div class="card">
					<nav> 드래곤 선택
						<div class="chooseDragon">X</div>
					</nav>
					<form action="/dragon/delete">
					<div class="description">
						<c:forEach var="item" items="${dragonList}">
							<div class="costume-card-modal" data-id="${item.dragonId}">
								<div class="innerText">
									<img alt="" src="../resources/images/dead.svg">
								</div>
								
								<div class="test" style="background-image: url(${item.img})"
									onclick="radioCheck(${item.dragonId})"></div>
							</div>
							<input type="radio" name="chooseDragon"
							value="${item.dragonId}" hidden="hidden">
						</c:forEach>
					
					</div>
					<div class="chooseEnd">
						<button type="submit">선택</button>
					</div>
					</form>	
				</div>
			</div>
		</div>
		</div>
			<section>

				<div class="pie_progress" role="progressbar" data-goal="50"
					data-barcolor="#3daf2c" data-barsize="30" aria-valuemin="0"
					aria-valuemax="100">
					<div class="pie_progress__content">LV ${values.totalLevel}</div>
				</div>
				<div class="row-content">
					<div>
						<img alt="이미지 없음" src="../resources/images/icon/chicken.svg" width="50px">
						<progress id="hungry_pro" value="${values.foodValue}" max="100"></progress>
					</div>
					<%-- <div>
					<img alt="이미지 없음" src="images/gamepad.svg" width="20px">
					<progress id="fun_pro" value="${fun}" max="100"></progress>
				</div> --%>
					<div class="coin">

						<img alt="이미지 없음" src="../resources/images/icon/coin.svg" width="30px">
						<p>${coin}</p>
					</div>

				</div>

			</section>

			<img src="${values.img}" id="dragon" width="200px" >
		</div>
		<div id="inventory">
				<div class="btn_array">
					<c:forEach var="item" items="${item}">
						<c:if test="${item.category eq 'item'}">
							<button type="button" class="button_item" data-des='${item.description}'
								value="${item.productId}" name="${item.productName}">
								<img alt="" src="${item.productImage}" width="50px"
									height="50px">
								<p>${item.productName}</p>
								<p class="cnt">수량 : ${item.cnt }</p>
							</button>
						</c:if>
					</c:forEach>
				</div>
			
			<div id="banner_navi">
        			<button class="btn_left" data-btn="0"><</button>
        			<button class="btn_right" data-btn="1">></button>
    			</div>
		</div>
		<div id="costume">
		<div class="tab">
			<button class="tabLink" onclick="openTab(event,'inven-dg')" id="defaultOpen">
				<svg>
            		<use xlink:href="../resources/images/icon/dragon.svg#dragon">
          		</svg>
			</button>
			<button class="tabLink" onclick="openTab(event,'inven-background')">
				<svg>
            		<use xlink:href="../resources/images/icon/background.svg#background">
          		</svg>
			</button>
		</div>

			<div class="btn_array-costume" id="inven-background">
				<c:forEach var="item" items="${item}">
					<c:if test="${item.category eq 'background'}">
						<div class="costume-card" data-id="${item.productId}">
							<div class="innerText">checked</div>
							<div class="test"
								style="background-image: url(${item.productImage})"></div>
						</div>
					</c:if>
				</c:forEach>
				
			</div>
			<div id="banner_navi_bg">
        			<button class="btn_left" data-btn="0"><</button>
        			<button class="btn_right" data-btn="1">></button>
    			</div>
			<div class="btn_array-costume" id="inven-dg">
				<c:forEach var="item" items="${dragonList}">
					<%-- <c:if test="${item.category eq 'egg'}"> --%>
						<div class="costume-card" data-id="${item.dragonId}">
							<div class="innerText">checked</div>
							<div class="test"
								style="background-image: url(${item.img})"></div>
						</div>
					<%-- </c:if> --%>
				</c:forEach>
				<input type="hidden" class="selectedEgg" value="${selectedEgg}">
				
			</div>
			<div id="banner_navi_dg">
        			<button class="btn_left" data-btn="0"><</button>
        			<button class="btn_right" data-btn="1">></button>
    			</div>

		</div>
	</div>
	<nav class="tabbar">
      <div>
        <input id="button_inventory" type="radio" name="menu" checked>
        <label for="button_inventory">
          <svg>
            <use xlink:href="../resources/images/icon/inventory.svg#inventory">
          </svg>
          <span>Inventory</span>
        </label>
        <input id="button_costume" type="radio" name="menu">
        <label for="button_costume">
          <svg>
            <use xlink:href="../resources/images/icon/hanger.svg#hanger">
          </svg>
          <span>Costume</span>
        </label>
        <input id="button_shop" type="radio" name="menu">
        <label for="button_shop">
          <svg>
            <use xlink:href="../resources/images/icon/shop.svg#shop">
          </svg>
          <span>Shop</span>
        </label>
        <span></span>
      </div>
    </nav>
  </section>
</div>
</div>
<input type="hidden" value="${values.userId }" name="userId"> 
<input type="hidden" value="${values.foodValue }" name="hungryValue"> 
<input type="hidden" value="${values.levelValue}" name="levelValue"> 
<input type="hidden" value="${values.totalLevel}" name="totalLevelValue">
<input type="hidden" name="dragonId" value="${values.dragonId }">

</body>
<script type="text/javascript" src="../resources/js/jquery.js"></script>
<script type="text/javascript" src="../resources/js/dragon/jquery-asPieProgress.js"></script>
<script type="text/javascript" src="../resources/js/dragon/slider.js"></script>
<script type="text/javascript">



function radioCheck(id) {//modal에서 드래곤 선택 시 radio 자동 체크
	$("input[value="+id+"]").prop("checked", true);
	$("div").find(".costume-card-modal").css({"border": "none"});
	$("div").find("[data-id="+id+"]").css({"border": "3px solid red"});
}

function callJqueryAjax(value) {//아이템 사용 ajax 처리
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");//security 설정
	
	$.ajax({
		url : '/product/use/'+value,
		method : 'post',
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data, textStatus, jqXHR) {
			//var strArray = data.description.split(" ");
			//$("input[name=description]").html(data.description);
			//var val = $("input[name=description]").val();
			//$('#inventory').load(location.href+ " .btn_array,#banner_navi");//page 값 갱신
			$('button[value='+value+'] .cnt').text("수량 : "+data.cnt);
			if(data.cnt==0){
				$('button[value='+value+']').css('display','none');
			}
			//init();
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});

}
function openTab(evt, menu) {//탭 이동 처리(Costume 버튼 안)
	  var i, tabcontent, tablinks;
	  tabcontent = document.getElementsByClassName("btn_array-costume");
	  for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tabLink");
	  for (i = 0; i < tablinks.length; i++) {
	    tablinks[i].className = tablinks[i].className.replace(" active", "");
	  }
	  document.getElementById(menu).style.display = "flex";
	  evt.currentTarget.className += " active";
	  if(menu=="inven-dg"){
		  $('#banner_navi_bg').css('display','none');
		  $('#banner_navi_dg').css('display','block');
	  }else if(menu=="inven-background"){
		  $('#banner_navi_dg').css('display','none');
		  $('#banner_navi_bg').css('display','block');
	  }
	}

	// Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();

function equip(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");//security 설정
	$.ajax({
		url : '/dragon/equip',
		method : 'post',
		data:{"dragonId" : id, "lastId":$('.selectedEgg').val()},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			//$('.selectedEgg').val(data.productId);
			//$('.selectedEgg').load(location.href+ " .selectedEgg");
			//$('#dragon').attr("src",data.productImage);
			location.reload();
			/* $('.pie_progress__content').val(data.totalLevel);
			$('.pie_progress__content').load(location.href+ " .pie_progress__content");
			$('#hungry_pro').val(data.foodValue);
			alert(data.foodValue);
			$('#hungry_pro').load(location.href+ " #hungry_pro"); */
			
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});
}

function equipBackground(id) {//배경 변경 처리
	var data = {backgroundId : id,
			dragonId : $("input[name=dragonId]").val()};
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");//security 설정
	$.ajax({
		url : '/dragon/equip/background',
		method : 'post',
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8;",
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			$('#dragonBackground').css({"background-image":"url("+data.productImage+")"});
			
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});
}

jQuery(function($) {
	
	if('<c:out value="${alert}"/>'){//로그아웃상태일시 차단
		document.location.href="/main";
		alert('로그인이 필요합니다!');
		return false;
	}
	
	var i = $("input[name=levelValue]").val();
	
	$('.pie_progress').asPieProgress({
		namespace : 'pie_progress'

	});
	$('.pie_progress').asPieProgress('go', parseInt(i));

	//포만감 수치 0이 될 경우
	if($("input[name=hungryValue]").val()==0&&!<c:out value="${noDragon}"/>){
		$('#overlay').css('display','block');
		$('#button_inventory').prop('disabled', true);//하단 3개 버튼 비활성화 처리
		$('#button_costume').prop('disabled', true);
		$('#button_shop').prop('disabled', true);
	}
	
	//egg 선택 첫화면
	
	var selectedEgg = $('.selectedEgg').val();
	$("div[data-id='"+selectedEgg+"']").children('.innerText').css('visibility','visible');
	$("div[data-id='<c:out value="${background.productId}"/>']").children('.innerText').css('visibility','visible');
	
	

	$(document).on('click', '.button_item', function() {
		var targetDiv = $("div[data-id='"+selectedEgg+"'] .test");
		callJqueryAjax($(this).attr('value'));
		var description = $(this).data("des");
		var strArray = description.split(" ");
		switch (strArray[0]) {
		case "포만감":
			var expression = $("input[name=hungryValue]").val()+strArray[1];
			$("input[name=hungryValue]").val(eval(expression));
			break;
		case "경험치":
			var expression = $("input[name=levelValue]").val()+strArray[1];
			$("input[name=levelValue]").val(eval(expression));
		default:
			break;
		}
		var level = $("input[name=totalLevelValue]").val();
		var hungry = $("input[name=hungryValue]").val();
		if($("input[name=levelValue]").val()>=100){
			$("input[name=levelValue]").val(0);
			$("input[name=totalLevelValue]").val(parseInt(level) + 1);
			$('.pie_progress').asPieProgress('reset');
		}
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");//security 설정
		
		$.ajax({
			url : '/dragon/dragonPanel',
			method : 'post',
			data: {userId:$("input[name=userId]").val(),
				totalLevel : $("input[name=totalLevelValue]").val(),
				foodValue : $("input[name=hungryValue]").val(),
				levelValue : $("input[name=levelValue]").val(),
				coin : $('.coin p').text(),
				dragonId : $("input[name=dragonId]").val(),
				equip : true},
			dataType: "json",
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data, textStatus, jqXHR) {
				document.getElementById("hungry_pro").value = data.foodValue;//포만감 갱신
				$('.pie_progress').asPieProgress('go', data.levelValue);//레벨값 갱신
				$("input[name=totalLevelValue]").val(data.totalLevel);//총레벨값 갱신
				//$('.pie_progress__content').load(location.href+ " .pie_progress__content");//페이지 로드
				$('.pie_progress__content').html("LV "+data.totalLevel);
				$('#dragon').attr("src",data.img);//드래곤 이미지 갱신
				targetDiv.css({"background-image":"url("+data.img+")"}); //탭 안의 이미지 갱신
				
			},
			error : function(jqXHR, exception) {
				console.log('Error occured!!');
			}
		});
		
			$('#dragon').animate({'top':'-=20px'},'fast');
			$('#dragon').animate({'top':'+=20px'},'fast');
		
			
	});

	$(document).on('click', '#button_shop', function() { //shop 탭 버튼
		location.href='../shop/shop';
	});

	$('#button_inventory').on('click', function() {//inventory 탭 버튼
		var chk = $('#costume').attr('style') === "display: block;"
		if (chk) {
			$('#costume').slideToggle();
		}
		$('#inventory').slideToggle();
	});
	$('#button_costume').on('click', function() {//custome 탭 버튼
		var chk = $('#inventory').attr('style') === "display: block;"
		if (chk) {
			$('#inventory').slideToggle();
		}
		$('#costume').slideToggle();
	});
	$('#inven-dg .costume-card').on('click', function() {//costume 선택 이벤트
		
		$('#inven-dg .costume-card').children('.innerText').css('visibility','hidden');
		$(this).children('.innerText').css('visibility','visible');
		equip($(this).data('id'));
	});
	$('#inven-background .costume-card').on('click', function() {//costume 선택 이벤트
		
		$('#inven-background .costume-card').children('.innerText').css('visibility','hidden');
		$(this).children('.innerText').css('visibility','visible');
		equipBackground($(this).data('id'));
	});
	$('#reviveDragon').on('click', function() {
		if(confirm("20000원으로 용을 부활시키겠습니까?")){
			if("<c:out value='${coin}'/>">20000){
				location.href = "/dragon/revive";
			}else{
				alert("금액이 부족합니다.")
			}
		}else{
			
			return;
		}
	});
	$('.chooseDragon').on('click', function() {
		$('.modal-wrapper').toggleClass('open');
		$('.page-wrapper').toggleClass('blur-it');

	});
	$('.goshop').on('click', function() {
		location.href='../shop/shop';
	});
	
	
	//이미지 슬라이더 부분
	
	$(document).on('click','#banner_navi .btn_left',itemSlider);
	$(document).on('click','#banner_navi .btn_right',itemSlider);
	$('#banner_navi_bg .btn_left').on('click',backgroundSlider);
	$('#banner_navi_bg .btn_right').on('click',backgroundSlider);
	$('#banner_navi_dg .btn_left').on('click',dragonListSlider);
	$('#banner_navi_dg .btn_right').on('click',dragonListSlider);

});

</script>
</html>