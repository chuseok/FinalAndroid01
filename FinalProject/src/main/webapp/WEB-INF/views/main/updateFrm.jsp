<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

		
	 	
	 	
<title>회원 정보 변경</title>
</head>
<style>
.home {
	margin-left: 400px;
	margin-right: 400px;
}

.hea2{
font-size: x-large;
float: left;
}
.a{
float: left;
}
</style>
<body>
	<jsp:include page="menu.jsp" />
	<div class="home">
	<h2>닉네임 변경</h2>
		<section id="container">
			<form role="form" method="post" id="updateFrm" autocomplete="off" action="/main/updateFrm">
<input class="form-control" type="hidden" id="userId" name="userId" value="<sec:authentication property="principal.member.userId" />" readonly="readonly"/>

				<div class="form-group has-feedback">
					
					<label class="control-label" >현재 닉네임</label>
					<input class="form-control" type="text" id="username" name="username" value="<sec:authentication property="principal.member.userName" />" readonly="readonly"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" >변경 할 닉네임</label>
					<input class="form-control" type="text" id="userName" name="userName" placeholder="닉네임"/>
				</div>
				<div class="form-group has-feedback">
							<button class="nameCheck" type="button" id="nameCheck" name="nameCheck"onclick="fn_nameCheck();" value="N">중복확인</button>
				</div>
			<div class="form-group has-feedback">
				<button class="btn btn-success" type="submit" class="button">변경하기</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			</form>
		</section>
		
		<br><br>
		<h2>비밀번호 변경</h2>

		<section id="container">
			<form role="form" method="post" id="updateFrm2" autocomplete="off" action="/main/updateFrm2">
					<input class="form-control" type="hidden" id="userId" name="userId" value="<sec:authentication property="principal.member.userId" />" readonly="readonly"/>

				<div class="form-group has-feedback">
					<label class="control-label" >새 비밀번호</label>
					<input class="form-control" type="password" id="userPwd" name="userPwd" placeholder="새 비밀번호"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" >새 비밀번호 확인</label>
					<input class="form-control" type="password" id="userPwdChk" name="userPwdChk" placeholder="새 비밀번호 확인"/>
				</div>
				
			<div class="form-group has-feedback">
				<button class="btn btn-success" type="submit" class="button">변경하기</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			</form>
		</section>
		
		<br><br>
		<h2>핸드폰번호 변경</h2>

		<section id="container">
			<form role="form" method="post" id="updateFrm3" autocomplete="off" action="/main/updateFrm3">
<input class="form-control" type="hidden" id="userId" name="userId" value="<sec:authentication property="principal.member.userId" />" readonly="readonly"/>

				<div class="form-group has-feedback">
					<label class="control-label" >현재 핸드폰번호</label>
					<input class="form-control" type="text" id="phoneNo1" name="phoneNo1" value="<sec:authentication property="principal.member.phoneNo" />" readonly="readonly"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" >새 핸드폰번호</label>
					<input class="form-control" type="text" id="phoneNo" name="phoneNo" placeholder="새 핸드폰번호"/>
				</div>
				
			<div class="form-group has-feedback">
				<button class="btn btn-success" type="submit" class="button">변경하기</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			</form>
		</section>
		
		<br><br>
		<h2>이메일 변경</h2>

		<section id="container">
			<form role="form" method="post" id="updateFrm4" autocomplete="off" action="/main/updateFrm4">
<input class="form-control" type="hidden" id="userId" name="userId" value="<sec:authentication property="principal.member.userId" />" readonly="readonly"/>

				<div class="form-group has-feedback">
					<label class="control-label" >현재 이메일주소</label>
					<input class="form-control" type="text" id="email1" name="email1" value="<sec:authentication property="principal.member.email" />" readonly="readonly"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" >새 이메일주소</label>
					<input class="form-control" type="text" id="email" name="email" placeholder="새 이메일주소"/>
				</div>
				
			<div class="form-group has-feedback">
				<button class="btn btn-success" type="submit" class="button">변경하기</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			</form>
		</section>
		
	
	     <script type="text/javascript">
		$(document).ready(function(){
						
			$(".button").on("click", function(){
				
				var nameChkVal = $("#nameCheck").val();
				if(nameChkVal == "N"){
					alert("중복확인 버튼을 눌러주세요.");
					return false;
				}
			});
			$('#userPwdChk').blur(function(){
				   if($('#userPwd').val() != $('#userPwdChk').val()){
				    	if($('#userPwdChk').val()!=''){
					    alert("비밀번호가 일치하지 않습니다.");
				    	    $('#userPwdChk').val('');
				          $('#userPwdChk').focus();
				       }
				    }
				})  	
		})
		
		function fn_nameCheck(){
			$.ajax({
				url : "/main/nameCheck",
				type : "post",
				dataType : "json",
				data : {"userName" : $("#userName").val()},
				success : function(data){
					if(data == 1){
						alert("중복된 닉네임입니다.");
					}else if(data == 0){
						$("#nameCheck").attr("value", "Y");
						alert("사용가능한 닉네임입니다.");
					}
				}
			})
			
			
		}
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader( "${_csrf.headerName}", "${_csrf.token}" );
			});	
	</script>
</body>
</html>
