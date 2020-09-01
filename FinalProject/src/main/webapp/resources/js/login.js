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
		    //한글만
			$(".form").validate({				
				rules:{
					userId:{
				    required: true, 
				    alphanumeric: true, 
				    minlength: 6, 
				    },
				    userPwd:{
						required : true, 
						minlength : 8,
						passwordCk: true
					},
					
				},

				
				messages:{
					userId: {
			                required: "아이디는 필수입력입니다.",
			                minlength: "아이디가  짧습니다! 6자리이상 입력해주세요.",
			                alphanumeric: "영문,숫자만 입력가능합니다.",
			                
			            },
			            userPwd:{
						required: "비밀번호는 필수입력입니다.",
		                minlength: "비밀번호가 너무 짧습니다! 8자리이상 입력해주세요.",
		                passwordCk: "영문+숫자+특수문자 포함 8~16자로 입력해주세요."
					},
					
				},

				
				errorElement : 'span', 	
				errorClass: 'error',	
				validClass:'vaild' 
			});
		});