/**
 * 
 */

$(function(){
	//영문숫자만
	jQuery.validator.addMethod("alphanumeric", function (value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	});
	//영문숫자특수문자
	$.validator.addMethod("passwordCk",  function( value, element ) {
		return this.optional(element) ||  /^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/.test(value);
	});

	//숫자만
	jQuery.validator.addMethod("numeric", function (value, element) {
		return this.optional(element) || /^[0-9]+$/.test(value);
	});
	//한글만
	$.validator.addMethod("hangle", function (value, element) {
		return this.optional(element) || /^[\uAC00-\uD7A3]+$/.test(value);
	}, "");
		
	$(".form").validate({
		
		submitHandler: function() {
			
			var f = confirm("회원가입을 완료하겠습니까?");
			if(f){
				return true;
			} else {
				return false;
			}
			
		},
		


		rules:{
			userId:{
				required: true, 
				alphanumeric: true, 
				minlength: 6, 
				maxlength: 12,
				
			},
			
			userPwd:{
				required : true, 
				minlength : 8,
				maxlength : 16,
				passwordCk: true
			},
			userName:{
				required : true,
				minlength : 2,
				maxlength : 5,
				hangle : true

			},
			email:{
				required : true, 
				email : true 	
			},
			birthday:{
				required : true, 
				date : true 	
			},
			phoneNo:{
				required : true, 
				minlength : 11,
				maxlength : 11,
				numeric : true
			}
		},


		messages:{
			userId: {
				required: "아이디는 필수입력입니다.",
				minlength: "아이디가  짧습니다! 6자리이상 입력해주세요.",
				maxlength: "아이디가  깁니다! 12자이하 입력해주세요",
				alphanumeric: "영문,숫자만 입력가능합니다.",
				
			},
			userPwd:{
				required: "비밀번호는 필수입력입니다.",
				minlength: "비밀번호가 너무 짧습니다! 8자리이상 입력해주세요.",
				maxlength: "비밀번호가 너무 깁니다! 16자리이하 입력해주세요.",
				passwordCk: "영문+숫자+특수문자 포함 8~16자로 입력해주세요."
			},
			userName:{
				required : "이름은 필수입력입니다.",
				minlength : "최소 2자리이상 입력해주세요.",
				maxlength : "최대 5자리이하 입력해주세요.",
				hangle : "한글로만 입력해주세요."
			},
			email: {
				required : "이메일은 필수 입력입니다.",
				email : "이메일 형식에 맞게 입력해주세요." 
			},

			birthday: {
				required : "생년월일은 필수 입력입니다.", 
			},
			phoneNo: {
				required : "전화번호는 필수 입력입니다.", 
				minlength : "11자리를 입력해주세요.",
				maxlength : "11자리를 입력해주세요.",
				numeric : "숫자만 입력가능합니다."
			}
		},


		errorElement : 'span', 	
		errorClass: 'error',	
		validClass:'vaild' 
	});
});

