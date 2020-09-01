<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="_csrf" content="${_csrf.token}">
  <meta name="_csrf_header" content="${_csrf.headerName}">
  <title>암기용-Home</title>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="stylesheet" href="/resources/css/dragon/shop.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
<style>
/* ul {
	list-style-type: none;
}

li {
	display: inline-block;
} 

label img {
	height: 100px;
	width: 100px;
	transition-duration: 0.2s;
	transform-origin: 50% 50%;
}*/

</style>
</head>

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
    <div class="main">
		<input id="tab1" type="radio" name="tabs" value="tab1" >
		<!--디폴트 메뉴-->
		<label class="tabLabel" for="tab1">item</label> 
		<input id="tab2" type="radio" name="tabs" value="tab2"> 
		<label class="tabLabel" for="tab2">background</label>

		<input id="tab3" type="radio" name="tabs" value="tab3"> 
		<label class="tabLabel" for="tab3">eggs</label> 
		<input type="hidden" id="selectedTab" value="tab">

		<section id="content1">
			<c:forEach var="item" items="${itemList }">
				<div class="wrapper">
					<div class="container">
						<div class="top">
							<img src="${item.productImage }" width="110px">
						</div>
						<div class="bottom">
							<div class="left">
								<div class="details">
									<h1>${item.productName }</h1>
									<p>${item.price }</p>
								</div>
								<div class="goDetail" name="${item.productName }">
									<img alt="" src="../resources/images/icon/cart.svg">
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>


		</section>
		
		
		<section id="content2">
			
			<c:forEach var="item" items="${backgroundList}">
				<div class="wrapper">
					<div class="container">
						<div class="top">
							<img src="${item.productImage }" width="110px">
						</div>
						<div class="bottom">
							<div class="left">
								<div class="details">
									<h1>${item.productName }</h1>
									<p>${item.price }</p>
								</div>
								<div class="goDetail" name="${item.productName }">
									<img alt="" src="../resources/images/icon/cart.svg">
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</section>

		<section id="content3">
			<c:forEach var="item" items="${eggList}">
				<div class="wrapper">
					<div class="container">
						<div class="top">
							<img src="${item.productImage }" width="110px">
						</div>
						<div class="bottom">
							<div class="left">
								<div class="details">
									<h1>${item.productName }</h1>
									<p>${item.price }</p>
								</div>
								<div class="goDetail" name="${item.productName }">
									<img alt="" src="../resources/images/icon/cart.svg">
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</section>

<div class="btnArray">
		<input type="button" value="용키우기" id="dragonBtn"
			onclick="location.href='../dragon/dragonPanel'">
		</div>
	</div>
	<div class="modal-wrapper">
		<div class="modal">
			<div class="content">
				<div class="card">
					<nav>
						<div class="goDetail">X</div>
						<div class="coin">
							<img alt="이미지 없음" src="../resources/images/icon/coin.svg" width="30px">
							<span>${coin}</span>
						</div>
					</nav>
					<div class="photo">
						<img id="proimg_md">
					</div>
					<div class="description">
						<form action="/shop/buy" method="post">
							<h2 id="proname_md"></h2>
							<h1 id="price_md"></h1>
							<p id="description_md"></p>
							
							<div id="amountDiv">
							<h4>수량 </h4><div class="select_mate" data-mate-select="active">
								<select name="selectAmount" onchange="selectChange(this.value)"
									onclick="return false;" id="">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="">---직접입력---</option>
								</select>
								<p class="selecionado_opcion" onclick="open_select(this)"></p>
								<span onclick="open_select(this)" class="icon_select_mate"><svg
										fill="#000000" height="24" viewBox="0 0 24 24" width="24"
										xmlns="http://www.w3.org/2000/svg">
										<path d="M7.41 7.84L12 12.42l4.59-4.58L18 9.25l-6 6-6-6z" />
										<path d="M0-.75h24v24H0z" fill="none" />
										</svg> </span>
								<div class="cont_list_select_mate">
									<ul class="cont_select_int">
									</ul>
								</div>
							</div>
							<input type="text" name="buyAmount" id="buyAmount"
								style="background: #DFDFDF;" value="1"> 
								
							<h3>총 가격 : <span id="totalPrice"></span></h3>
							</div>
							<div id="alertBuy"><h3>이미 구매한 상품입니다.</h3></div>
								<input type="hidden" name="productId" value="${selectedProduct.productId}"> 
								<input type="hidden" name="productPrice" value="${selectedProduct.price}">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<button type="button" id="buy">BUY</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
  </section>
</div>
</div>
</body>
<script type="text/javascript" src="../resources/js/jquery.js"></script>
<script type="text/javascript">

function callJqueryAjax(name) {

	$.ajax({
		url : '/product/get/'+name,
		method : 'GET',
		dataType:'json',
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			console.log(data.productName);
			$('#proname_md').text(data.productName);
			$('#price_md').text(data.price);
			$('#description_md').text(data.description);
			$('#proimg_md').attr("src", data.productImage);
			$('#totalPrice').text(data.price);
			$('input[name="productId"]').val(data.productId);
			$('input[name="productPrice"]').val(data.price);
			
			crear_select();
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});
}
function checkBuy(name){
	$.ajax({
		url : '/product/check/'+name,
		method : 'GET',
		dataType:'json',
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var check = $('input:radio[name=tabs]:checked').val();
			if(data&&(check=='tab3'||check=='tab2')){
				
				$('#alertBuy h3').css('display','block');
				$('#buy').prop("disabled", true);
			}
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});
}
function selectChange(value) {
	var amount=0;
	if (value != "") {
		document.getElementById("buyAmount").value = value;
		document.getElementById("buyAmount").readOnly = true;
		document.getElementById("buyAmount").style.background = "#DFDFDF";
		amount = value;
	} else {
		document.getElementById("buyAmount").value = "";
		document.getElementById("buyAmount").readOnly = false;
		document.getElementById("buyAmount").style.background = "#FFFFFF";
		document.getElementById("buyAmount").focus();
		amount = 1;
	}
	var price = $('#price_md').text();
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");//security 설정
	
	$.ajax({
		url : '/product/total/'+price+"/"+amount,
		method : 'post',
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data, textStatus, jqXHR) {
			$('#totalPrice').text(data);
			$('#totalPrice').removeClass('warn');
			if(data>$('.coin span').text()){
				$('#totalPrice').addClass('warn');
			}
		},
		error : function(jqXHR, exception) {
			console.log('Error occured!!');
		}
	});
}
//select js

	function isMobileDevice() {
	    return (typeof window.orientation !== "undefined") || (navigator.userAgent.indexOf('IEMobile') !== -1);
	};

	 
	var li = new Array();
	function crear_select(){
	var div_cont_select = document.querySelectorAll("[data-mate-select='active']");
	var select_ = '';
	for (var e = 0; e < div_cont_select.length; e++) {
	div_cont_select[e].setAttribute('data-indx-select',e);
	div_cont_select[e].setAttribute('data-selec-open','false');
	var ul_cont = document.querySelectorAll("[data-indx-select='"+e+"'] > .cont_list_select_mate > ul");
	 select_ = document.querySelectorAll("[data-indx-select='"+e+"'] >select")[0];
	 if (isMobileDevice()) { 
	select_.addEventListener('change', function () {
	 _select_option(select_.selectedIndex,e);
	});
	 }
	var select_optiones = select_.options;
	document.querySelectorAll("[data-indx-select='"+e+"']  > .selecionado_opcion ")[0].setAttribute('data-n-select',e);
	document.querySelectorAll("[data-indx-select='"+e+"']  > .icon_select_mate ")[0].setAttribute('data-n-select',e);
	for (var i = 0; i < select_optiones.length; i++) {
	li[i] = document.createElement('li');
	if (select_optiones[i].selected == true || select_.value == select_optiones[i].innerHTML ) {
	li[i].className = 'active';
	document.querySelector("[data-indx-select='"+e+"']  > .selecionado_opcion ").innerHTML = select_optiones[i].innerHTML;
	};
	li[i].setAttribute('data-index',i);
	li[i].setAttribute('data-selec-index',e);
	// funcion click al selecionar 
	li[i].addEventListener( 'click', function(){  _select_option(this.getAttribute('data-index'),this.getAttribute('data-selec-index')); });

	li[i].innerHTML = select_optiones[i].innerHTML;
	ul_cont[0].appendChild(li[i]);

	    }; // Fin For select_optiones
	  }; // fin for divs_cont_select
	  
	} // Fin Function 



	var cont_slc = 0;
	function open_select(idx){
	var idx1 =  idx.getAttribute('data-n-select');
	  var ul_cont_li = document.querySelectorAll("[data-indx-select='"+idx1+"'] .cont_select_int > li");
	var hg = 0;
	var slect_open = document.querySelectorAll("[data-indx-select='"+idx1+"']")[0].getAttribute('data-selec-open');
	var slect_element_open = document.querySelectorAll("[data-indx-select='"+idx1+"'] select")[0];
	 if (isMobileDevice()) { 
	  if (window.document.createEvent) { // All
	  var evt = window.document.createEvent("MouseEvents");
	  evt.initMouseEvent("mousedown", false, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
		slect_element_open.dispatchEvent(evt);
	} else if (slect_element_open.fireEvent) { // IE
	  slect_element_open.fireEvent("onmousedown");
	}else {
	  slect_element_open.click();
	}
	}else {

	  
	  for (var i = 0; i < ul_cont_li.length; i++) {
	hg += ul_cont_li[i].offsetHeight;
	}; 
	 if (slect_open == 'false') {  
	 document.querySelectorAll("[data-indx-select='"+idx1+"']")[0].setAttribute('data-selec-open','true');
	 document.querySelectorAll("[data-indx-select='"+idx1+"'] > .cont_list_select_mate > ul")[0].style.height = hg+"px";
	 document.querySelectorAll("[data-indx-select='"+idx1+"'] > .icon_select_mate")[0].style.transform = 'rotate(180deg)';
	}else{
	 document.querySelectorAll("[data-indx-select='"+idx1+"']")[0].setAttribute('data-selec-open','false');
	 document.querySelectorAll("[data-indx-select='"+idx1+"'] > .icon_select_mate")[0].style.transform = 'rotate(0deg)';
	 document.querySelectorAll("[data-indx-select='"+idx1+"'] > .cont_list_select_mate > ul")[0].style.height = "0px";
	 }
	}

	} // fin function open_select

	function salir_select(indx){
	var select_ = document.querySelectorAll("[data-indx-select='"+indx+"'] > select")[0];
	 document.querySelectorAll("[data-indx-select='"+indx+"'] > .cont_list_select_mate > ul")[0].style.height = "0px";
	document.querySelector("[data-indx-select='"+indx+"'] > .icon_select_mate").style.transform = 'rotate(0deg)';
	 document.querySelectorAll("[data-indx-select='"+indx+"']")[0].setAttribute('data-selec-open','false');
	}


	function _select_option(indx,selc){
	 if (isMobileDevice()) { 
	selc = selc -1;
	}
	    var select_ = document.querySelectorAll("[data-indx-select='"+selc+"'] > select")[0];

	  var li_s = document.querySelectorAll("[data-indx-select='"+selc+"'] .cont_select_int > li");
	  var p_act = document.querySelectorAll("[data-indx-select='"+selc+"'] > .selecionado_opcion")[0].innerHTML = li_s[indx].innerHTML;
	var select_optiones = document.querySelectorAll("[data-indx-select='"+selc+"'] > select > option");
	for (var i = 0; i < li_s.length; i++) {
	if (li_s[i].className == 'active') {
	li_s[i].className = '';
	};
	li_s[indx].className = 'active';

	};
	select_optiones[indx].selected = true;
	  select_.selectedIndex = indx;
	  select_.onchange();
	  salir_select(selc); 
	}
	//select js end
$(document).ready(function() {
	if('<c:out value="${alert}"/>'){//로그아웃상태일시 차단
		document.location.href="/main";
		alert('로그인이 필요합니다!');
		return false;
	}
	
	var selectedTab = localStorage.getItem("option");//탭값을 localStorage에 저장
	if(selectedTab==null){
		$('#tab1').attr('checked',true);//기본은 tab1
	}else{
		$('#'+selectedTab).attr('checked',true);//선택한 탭 유지
	}
	$(document).on('click','.goDetail', function(event) {
		var select = $('input:radio[name=tabs]:checked').val();
		
		$('.modal-wrapper').toggleClass('open');
		$('.page-wrapper').toggleClass('blur-it');

		if(select=='tab3'||select=='tab2'){
			$('#amountDiv').css('display','none');
		}

		var name = $(this).attr('name');
		$('input[name=productName]').val(name);
		if(name!=null){
			checkBuy(name);
			callJqueryAjax(name);
			
			
		}else{
			var select = $('input:radio[name=tabs]:checked').val();//탭값 유지위해..
			localStorage.setItem("option",select);
			location.reload();
			
		}
		
		
	});
	

	$('#buyAmount').keyup(function() {
		$(this).val( $(this).val().replace(/[^0-9]/gi,"") );//숫자만 입력가능
		var typed_length = $(this).val().length;
		if(typed_length<1){
			return;
		}
		var price = $('#price_md').text();
		var amount = $(this).val();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");//security 설정
		$.ajax({
			url : '/product/total/'+price+"/"+amount,
			method : 'post',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data, textStatus, jqXHR) {
				$('#totalPrice').text(data);
				$('#totalPrice').removeClass('warn');
				if(data>$('.coin span').text()){
					$('#totalPrice').addClass('warn');
				}
				
			},
			error : function(jqXHR, exception) {
				console.log('Error occured!!');
			}
		});
	});
	
	
	$('#buy').on('click',function(){
		var coin = $('.coin span').text();
		var totalPrice = $('#totalPrice').text();
		if(confirm("이 상품을 구매하시겠습니까?")){
			if(parseInt(coin)<parseInt(totalPrice)){
				alert("금액이 부족합니다.");
				return false;
			}
			this.form.submit();
		}else{
			return false;
		}
	});
	
});
</script>
</html>