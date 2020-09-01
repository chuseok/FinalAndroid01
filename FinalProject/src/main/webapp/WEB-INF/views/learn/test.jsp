<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="/resources/css/main.css">
 <link rel="stylesheet" href="/resources/css/test.css">
 
  
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">

  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script src="/resources/js/learn.js"></script>
	
<title>암기용 - Test</title>

<style>
.answerField div{
	padding-bottom: 4px;
}
.feedback-label {
	color: #769fcd;
	font-size: 14px;
}

.feedback-incorrectAns {
	color: #ff725b;
	font-size: 16px;
}

.feedback-incorrectAns i {
	padding: 0 4px;
}

.feedback-correctAns {
	color: #23b26d;
	font-size: 16px;
}

.feedback-correctAns i {
	font-size: 14px;
	padding: 0 4px;
}

/* sidebar-result */
.sideBar-resultBox {
	width: 180px;
	display: flex;
	flex-direction: column;
}

.sideBar-resultBox .result-score {
	width:100%;
	text-align:center;
	font-size: 30px;
	font-weight: 600px;
}

/* 테스트 만들기 버튼 추가 - 이혜정*/
#testRestartBtn{
	display: none;
}
</style>
</head>
<body>

<%@ include file="../includes/header.jsp"%>


	<div class="HomeLayout">
		<div class="HomeLayout-container-box">

			<div class="learn-sideBar">
				<div class="sideBar-backBtnBox">
					<i class="fas fa-angle-left"></i>세트로 돌아가기
				</div>

				<div class="sideBar-progressBox">
					<div class="progress-label">
						<label><i class="fas fa-scroll"></i>테스트</label>
					</div>

					<!-- /.progress -->
				</div>
				<!-- /.sidebar -->

			</div>
			<!-- 문제출력 -->
			<div class="test-mainContent">
			
			<section class="testSection">
			<!-- 주관식 -->				
				<div class="subjectiveQuestion" id="subjectiveQuestion"></div>
			<!-- /.subjectiveQuestion -->
					
			<!-- 객관식 -->
				<div class="multipleQuestion" id="multipleQuestion"></div>				
				<!-- /.multipleQuestion -->
				
			<!-- 단답형 -->
				<div class="shortQuestion" id="shortQuestion"></div>				
				<!-- /.shortQuestion -->					
					
			<div class="testCheckButtonDiv">
				<button type="button" class="testCheckBtn" id="testCheckBtn"><span>정답 확인</span></button>
				<button type="button" class="testCheckBtn" id="testRestartBtn"><span>테스트 만들기</span></button>
			</div>			
			</section>	
		</div>
		<!-- /.learn-mainContent -->		
	</div>
</div>
<script>
$(document).ready(function(){
	
	var WordJsonArray;
	
	var WordCardArray;
	var QuizDataArray = new Array();
	
	var SubjCorrectArray = new Array();
	var MultiCorrectArray = new Array();
	var shortCorrectArray = new Array();
	var shortFeedbackArray = new Array();
	
	var cardIndex;
	var cardListLength;
	var quizDataLength;
	
	var meanArray = new Array();
	var optionArray = new Array();
	var option;
	var random;
	var answer;
	
	var subNum;
	var multiNum;
	var shortNum;
	
	var correctNum;
	var incorrectNum;
	
	//초기화
	function initData() 
	{		
		WordJsonArray =  JSON.parse('${WordDTO}');
		WordCardArray = WordJsonArray[0]["item"];
		
		cardListLength = WordCardArray.length;
		cardIndex = 0;
		correctNum = 0;
		incorrectNum = 0;
		
		for(i in WordCardArray)
		{
			x= WordCardArray[i].meaning;
			//console.log(x);
			meanArray.push(x);
		}
			console.log("meanArray : " + meanArray);
			
		initTestData();
		quizDataLength = QuizDataArray.length;
		
		console.log(quizDataLength);
	};
	
	//문제 List Array 뽑기
	function initTestData()
	{
		//debugger;
		if(cardListLength >= 15)
		{//단어가 15개 이상인 경우
			for(var i=0; i<15; i++)
			{
				getRandomQuiz();
			}
			console.log(QuizDataArray);
		}
		else
		{//단어가 15개 미만인 경우
			for(var i=0; i<cardListLength; i++)
			{
				getRandomQuiz();
			}				
		}
			console.log(QuizDataArray);	
	} 
	
	initData();
	console.log("퀴즈 데이터");
	console.log(QuizDataArray);
	
	
	function sortQuiz(){
		console.log("sortQuiz data length: " + quizDataLength);
		if(quizDataLength == 15){
			var quizNum = parseInt(quizDataLength / 3);
			subNum = multiNum = shortNum = quizNum;
		}
		else
		{//
			var quizNum = parseInt(quizDataLength / 3);
			var quizNumTail = quizDataLength % 3;
			//console.log(quizNum);
			//console.log(quizNumTail);
			
			if(quizNumTail == 0)
			{
				subNum = multiNum = shortNum = quizNum;
			}
			else if(quizNumTail == 1)
			{
				subNum = quizNum +1;
				multiNum = shortNum = quizNum
			}
			else
			{
				subNum = multiNum = quizNum +1;
				shortNum = quizNum;
			}
		}
		showSubjQuestion(subNum);
		showMultipleQustion(multiNum);
		showShortQustion(shortNum);
	}
	
	//주관식 문제 출력
	function showSubjQuestion(quizNum)
	{
		//console.log("주관식 개수 : "+ quizNum)
		var txt = '<div class="quizHeadLabel"> 주관식 문제 '+quizNum +'개</div>';
		$('.subjectiveQuestion').append(txt);
		
			for(var i= 0; i<quizNum; i++)
			{
				//debugger;
				var question = QuizDataArray[i].meaning;
				//console.log(question);
				//주관식정답 array;
				SubjCorrectArray.push(QuizDataArray[i].word);
				//console.log("주관식 정답: "+SubjCorrectArray);
				var quizIndex = i+1;
				var txt = '<div class="quizDiv"><div class="question-prompt">';
				txt += '<div class="question-num">'+quizIndex+'. '+'</div>';
				txt += '<div class="question-text">'+question+'</div></div>';
				txt += '<div class="question-answer"><form class="answerField" id="sub'+quizIndex+'"><div class="answerField-input">';
				txt += '<textarea rows="1" class="subjective-answer" autofocus></textarea></div></form>';				
				txt += '</div></div>';
				
				$('.subjectiveQuestion').append(txt);				
			}		
		
	};//end show subjective Question
	
	
	//객관식 문제 출력
	function showMultipleQustion(quizNum)
	{
		if(quizNum == 0)
		{
			$('.multipleQuestion').remove(); 
		}
		else
		{
			console.log("객관식 개수 : "+ quizNum)
			var txt = '<div class="quizHeadLabel">객관식 문제 '+quizNum +'개</div>';
			$('.multipleQuestion').append(txt);
			
			var startNum = subNum;
			var lastNum = startNum + quizNum;
			console.log('객관식 시작번호'+startNum)
			var index = 1;
				for(var i= startNum; i<lastNum; i++)
				{
					//debugger;
					var question = QuizDataArray[i].word;
					//console.log(question);
					MultiCorrectArray.push(QuizDataArray[i].meaning);
					//보기출력
					optionArray = [];
					optionArray.push(QuizDataArray[i].meaning);
					getRandomOption();
					
					var txt = '<div class="quizDiv"><div class="question-prompt">';
					txt += '<div class="question-num">'+index+'. '+'</div>';
					txt += '<div class="question-text" id="text-eng">'+question+'</div></div>';
					txt += '<div class="multiRadios" id="multipleAnswers'+index+'">';
					txt += '<div class="oneOfRadio"><input type="radio" name="multi'+(index)+'" value="'+optionArray[0]+'"><span>'+optionArray[0]+'</span></div>';
					txt += '<div class="oneOfRadio"><input type="radio" name="multi'+(index)+'" value="'+optionArray[1]+'"><span>'+optionArray[1]+'</span></div>';				
					txt += '<div class="oneOfRadio"><input type="radio" name="multi'+(index)+'" value="'+optionArray[2]+'"><span>'+optionArray[2]+'</span></div>';				
					txt += '<div class="oneOfRadio"><input type="radio" name="multi'+(index)+'" value="'+optionArray[3]+'"><span>'+optionArray[3]+'</span></div>';				
					txt += '</div></div>';
					
					$('.multipleQuestion').append(txt);
					$('input[value="undefined"]').parent('div').remove();
					index +=1;
				}
		}
	}//end showMultipleQustion
	
	
	//단답형 문제 출력
	function showShortQustion(quizNum){
		if(quizNum == 0)
		{
			$('.shortQuestion').remove(); 
		}
		else
		{
			console.log("단답형 개수 : "+ quizNum)
			var txt = '<div class="quizHeadLabel">단답형 문제 '+quizNum +'개</div>';
			$('.shortQuestion').append(txt);
			
			//console.log("sub: "+subNum+", multi: "+multiNum);
			var startNum = subNum + multiNum;
			var lastNum = startNum + shortNum;
			var index = 1;
				for(var i= startNum; i<lastNum; i++)
				{
					//debugger;
					var shortWord = QuizDataArray[i].word;
					var shortMeaning =QuizDataArray[i].meaning;

					shortFeedbackArray.push(QuizDataArray[i].meaning);
					
					option = meanArray[Math.floor(Math.random() * meanArray.length)]
					
					var shortQuizArray = [shortMeaning,option];
					
					var randomIndex = Math.floor(Math.random() * shortQuizArray.length);
					var question = shortQuizArray[randomIndex];
					
					if(randomIndex == 0)
					{
						shortCorrectArray.push(true);
					} else
					{
						shortCorrectArray.push(false);
					}
					
					var txt = '<div class="quizDiv"><div class="question-prompt">';
					txt += '<div class="question-num">'+(index)+'. '+'</div>';
					txt += '<div class="question-text" id="text-eng">'+shortWord+
					'<i class="fas fa-long-arrow-alt-right"></i>'+question+'</div></div>';
					txt += '<div class="multiRadios" id="multipleAnswers"><div id="radioBox'+(index)+'">';
					txt += '<div class="oneOfRadio"><input type="radio" name="short'+(index)+'" value="true">맞음</div>';
					txt += '<div class="oneOfRadio"><input type="radio" name="short'+(index)+'" value="false">틀림</div>';				
					txt += '</div></div></div>';
					
					$('.shortQuestion').append(txt);	
					index +=1;
				}			
		}
		
	}
	
	//Array의 prototype을 지정해주고, shuffle이라는 이름을 가진 함수를 생성
	Array.prototype.shuffle = function () 
	{
	    var length = this.length;
	    
	    // 아래에서 length 후위 감소 연산자를 사용하면서 결국 0이된다.
	    // 프로그래밍에서 0은 false를 의미하기에 0이되면 종료.
	    while (length) {
	 
	        // 랜덤한 배열 index 추출
	        var index = Math.floor((length--) * Math.random());
	 
	        // 배열의 끝에서부터 0번째 아이템을 순차적으로 대입
	        var temp = this[length];
	 
	        // 랜덤한 위치의 값을 맨뒤(this[length])부터 셋팅
	        this[length] = this[index];
	 
	        // 랜덤한 위치에 위에 설정한 temp값 셋팅
	        this[index] = temp;
	    }
	 
	    // 배열을 리턴해준다.
	    return this;
	};
	
	
	//랜덤으로 나머지 보기 3개 옵션 생성
	function getRandomOption()
	{
		if(meanArray.length < 4 ){
			optionArray = meanArray;		
		}else {
			var i = 0;
			while(i<3){
				var option = meanArray[Math.floor(Math.random() * meanArray.length)];
				if(! sameOption(option)){
					optionArray.push(option);
					i++;
				}
			}	
		}
		optionArray.shuffle();
		optionArray.shuffle();
		//console.log("셔플 후 optionArray : " + optionArray);
	}

	//3개 optionArray 중복 체크
	function sameOption(option)
	{
		for(var i = 0; i < optionArray.length; i++){
			if(option == optionArray[i])
				return true;
		}
		return false;
	}	
	

	//문제 List Array 추가 함수
	function addQuizDataArray(word, meaning){
		QuizDataArray.push({
			word : word,
			meaning : meaning			
		});
	};
	
	
	//random 문제  뽑기 함수
	function getRandomQuiz()
	{
		cardIndex = Math.floor(Math.random() * WordCardArray.length);
		random = WordCardArray[cardIndex];
		addQuizDataArray(random.word, random.meaning);
		WordCardArray.splice(cardIndex,1);
	}	
	
	sortQuiz();
	
	
	//정답확인 버튼
	$(document).on("click","#testCheckBtn",function(e){
		e.preventDefault();
		checkSubjAns();
		checkMultiAns();
		checkShortAns();
		showScore();
		$(this).css("display","none");
		$("#testRestartBtn").css("display","block");
	});
	
	//테스트 재시작 버튼
	$(document).on("click","#testRestartBtn",function(e){
		e.preventDefault();
		location.reload();
	});
	
	$(document).on("click",".oneOfRadio",function(e){
		e.preventDefault();
		$(this).children('input').prop('checked', true);
	});
	
	
	//주관식 정답확인
	function checkSubjAns(){
		var index = 0;
		var quizIndex;
		$("textarea.subjective-answer").each(function(){	
			quizIndex = index+1;
			var text = $(this).val();
			var ans = SubjCorrectArray[index];
				if(text == ans){
					//console.log(quizIndex+"번 정답");
					showSubjFeedback(true,text,ans,quizIndex);
					correctNum += 1;
				}
				else{
					//console.log(quizIndex+"번 오답");
					showSubjFeedback(false,text,ans,quizIndex);
					incorrectNum += 1;
				}
				index += 1;
				
			});			
		};
	
		//주관식 피드백출력
		function showSubjFeedback(result,text,ans,quizIndex){
			var str ="";
			//debugger;
			
			if(result == true){
				str = '<div class="feedback-label"><span>정답</span></div>';
				str += '<div class="feedback-correctAns"><i class="fas fa-check"></i><span>'+text+'</span></div>';
								
				$('.answerField-input').remove();
				$('#sub'+quizIndex).append(str);
			}
			else{
				str = '<div class="feedback-label"><span>오답</span></div>';
				str += '<div class="feedback-incorrectAns"><i class="fas fa-times"></i><span>'+text+'</span></div>';
				str += '<div class="feedback-label"><span>정답</span></div>';
				str += '<div class="feedback-correctAns"><span>'+ans+'</span></div>';
				
				$('.answerField-input').remove();
				$('#sub'+quizIndex).append(str);
			}
			
		};//end showSubjFeedback
		
		//객관식 정답확인
		function checkMultiAns(){
			var index = 0;
			var quizIndex;
			$('input[name*="multi"]:checked').each(function(){
				quizIndex = index+1;
				var text = $(this).val();
				var ans = MultiCorrectArray[index];
				
				if(text == ans){
					//console.log(quizIndex+"번 정답");
					showMultiFeedback(true,text,ans,quizIndex);
					correctNum += 1;
				}
				else{
					//console.log(quizIndex+"번 오답");
					showMultiFeedback(false,text,ans,quizIndex);
					incorrectNum += 1;
				}
				index += 1;			
			});
		};
		
		//객관식 피드백 출력
		function showMultiFeedback(result,text,ans,quizIndex){
			//debugger;
			$('.oneOfRadio').css('color','#d3d4d8');
			$('input').attr("disabled",true);
			if(result == true){
				var str = '<i class="fas fa-check">';
				$('#multipleAnswers'+quizIndex).children('div').children('input[value="'+text+'"]').parent('div').append(str);
				$('#multipleAnswers'+quizIndex).children('div').children('input[value="'+ans+'"]').next('span').css('color','#1b262c');
			}
			else{
				var str = '<i class="fas fa-check">';
				$('#multipleAnswers'+quizIndex).children('div').children('input[value="'+ans+'"]').parent('div').append(str);
				$('#multipleAnswers'+quizIndex).children('div').children('input[value="'+ans+'"]').next('span').css('color','#1b262c');
				str = '<i class="fas fa-times">'
				$('#multipleAnswers'+quizIndex).children('div').children('input[value="'+text+'"]').parent('div').append(str);	
			}			
			$('.fa-check').css('color','#23b26d');
			$('.fa-times').css('color','#ff725b');
		};
		
		//단답형 정답 확인
		function checkShortAns(){
			var index = 0;
			var quizIndex;
			$('input[name*="short"]:checked').each(function(){
				quizIndex = index+1;
				var input = $(this).val();
				var ans = shortCorrectArray[index];
				//console.log("선택한 답: "+ input);
				
				if(input == String(ans)){
					//console.log(quizIndex+"번 정답");
					showShortFeedback(true,input,ans,quizIndex);
					correctNum += 1;
				}
				else{
					//console.log(quizIndex+"번 오답");
					showShortFeedback(false,input,ans,quizIndex);
					incorrectNum += 1;
				}
				index += 1;	
				
			});
		 
		};
		
		//단답형 피드백 출력
		function showShortFeedback(result,input,ans,quizIndex)
		{
			if(result == true){
				str = '<div class="feedback-label"><span>정답</span></div>';
				str += '<div class="feedback-correctAns"><i class="fas fa-check"></i><span>'+input+'</span></div>';
								
				$('#radioBox'+quizIndex).children('.oneOfRadio').remove();
				$('#radioBox'+quizIndex).append(str);
			}
			else{
				str = '<div class="feedback-label"><span>오답</span></div>';
				str += '<div class="feedback-incorrectAns"><i class="fas fa-times"></i><span>'+input+'</span></div>';
				str += '<div class="feedback-label"><span>정답</span></div>';
				str += '<div class="feedback-correctAns"><span>'+ans+'</span>';
				str += '<br><span>정답 : '+shortFeedbackArray[(quizIndex-1)];
				str += '</div>';
				
				$('#radioBox'+quizIndex).children('.oneOfRadio').remove();
				$('#radioBox'+quizIndex).append(str);
			}			
		};
		
		function showScore()
		{
			var allQuestionNum = 0;
			var score = 0;
			allQuestionNum = correctNum + incorrectNum;
			console.log(allQuestionNum);
			
			score = (Math.floor((correctNum / allQuestionNum)* 100)) + "%";
			//console.log(score);
			str =  '<div class="sideBar-resultBox"><div class="result-label">';
			str += '<span>점수</span></div><div class="result-score">'
			str += '<span>'+score+'</span></div></div>';
			
			$('.learn-sideBar').append(str);
		};
		
		
		
		
});//end doc.ready 
</script>
</body>


</html>