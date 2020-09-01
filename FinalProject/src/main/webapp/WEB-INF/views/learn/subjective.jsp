<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="stylesheet" href="/resources/css/subjective.css">
  
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">

  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script src="/resources/js/learn.js"></script>
<title>암기용 - 주관식</title>

<style>

/*result페이지*/
.feedback-box{
	padding: 30px 0;
	border-bottom: 1px solid #f6f6f6;
	display: flex;
	flex-direction: row;
	font-weight: 600;
}

.feedback-box .feedback-description {
	flex: 1;
}

.feedback-box .feedback-count {
	padding: 0 40px;
}

.feedback-box .feedback-percentage {
	padding-left: 20px;
}

#rightResult {
	color: #1fab89;
}

#wrongResult{
	color: #ff6464;
}

.continueBtnBox {
	display: flex;
	padding-top: 40px;
	justify-content: center;
	
}

.continueBtnBox button {
	width: 70%;
	padding: 10px 0;
	border: none;
	border-radius: 0.25rem;
	background-color: #11d3bc;
	color: #fff;
	font-weight: 500;
	font-size: 14px;
}

/*오답 피드백*/
.wrongAns-feedback-box{
	padding: 30px 0;
	border-bottom: 1px solid #f6f6f6;
	display: flex;
	flex-direction: column;
}

.feedback-header {
	font-size: 18px;
}

.feedback-header i {
	color: #005792;
	font-size: 22px;
	padding-right: 8px;
}

.feedback-label {
	font-size: 13px;
	color: #4d606e;
}

.feedback-content {
	font-size: 19px;
	padding: 8px 0;	
}

#content-input {
	color: #ff6464;
}

#content-correct {
	color: #23b26d;
}

/*최종 결과*/
.resetBtn {
	display: flex;
	padding-top: 40px;
	justify-content: center;
	
}
.resetBtn button {
	width: 70%;
	padding: 10px 0;
	border: none;
	border-radius: 0.25rem;
	background-color: #11d3bc;
	color: #fff;
	font-weight: 500;
	font-size: 14px;
}

.resultBox{
	padding: 10px 0;
	border-bottom: 1px solid #f6f6f6;
	display: flex;
	flex-direction: row;
	font-weight: 600;
	justify-content: space-between;
}
</style>
</head>
<body>

<%@ include file="../includes/header.jsp" %>


	<div class="HomeLayout">
		<div class="HomeLayout-container-box">

			<div class="learn-sideBar">
				<div class="sideBar-backBtnBox">
					<i class="fas fa-angle-left"></i> 뒤로
				</div>

				<div class="sideBar-progressBox">
					<div class="progress-label">
						<label><i class="fas fa-pencil-alt"></i>주관식</label>
					</div>

					<div class="progress-contentWrapper">

						<div class="progress-contentBox">
							<div class="progressBar"></div>
							<div class="progressBar-label">
								<div class="label-description">남은카드</div>
								<div class="label-countNum" id="progress-left"></div>
							</div>
						</div>

						<div class="progress-contentBox">
							<div class="progressBar"></div>
							<div class="progressBar-label">
								<div class="label-description">오답</div>
								<div class="label-countNum" id="progress-wrong"></div>
							</div>
						</div>
						<div class="progress-contentBox">
							<div class="progressBar"></div>
							<div class="progressBar-label">
								<div class="label-description">정답</div>
								<div class="label-countNum" id="progress-right"></div>
							</div>
						</div>

					</div>
					<!-- /.progress -->
				</div>
				<!-- /.sidebar -->

			</div>
			<div class="learn-mainContent">
			
				<div class="subjectiveQuestion" id="subjectiveQuestion">
					<div class="quizDiv">
						<div class="question-prompt">
							<div class="question-text"></div>
							<div class="question-dontKnow">
								<button type="button" id="dontKnowBtn"><span>모르겠어요</span></button>
							</div>
						</div>
						<div class="question-answer">
							<form class="answerField">
								<div class="answerField-input">
									<textarea rows="1" class="subjective-answerArea" autofocus></textarea>
								</div>
								<div class="answerField-action">								
									<button type="submit" class="subjective-submitBtn">
										<span>정답</span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
			</div><!-- /.learn-mainContent -->

		</div>
		
	</div>

	<script>
$(document).ready(function(){
		
		var WordJsonArray;
		
		var WordCardArray;
		var RightCardArray = new Array();
		var WrongCardArray = new Array();

		var cardIndex;
		
		var cardListLength;
		var rightCardLength;
		var wrongCardLength;
		
		var optionArray;
		var option;
		var random;
		
		function initData() 
		{
			
			WordJsonArray =  JSON.parse('${WordDTO}');
			WordCardArray = WordJsonArray[0]["item"];
			
			cardIndex = 0;
			cardListLength = WordCardArray.length;
			rightCardLength = 0;
			wrongCardLength = 0;
			
			//console.log(WordJsonArray);
			//console.log(WordCardArray);
			
		}
		
		initData();
		
		
		//progress bar
		function showProgress() 
		{
			//남은카드		
			cardListLength = WordCardArray.length;
			console.log("남은카드: " + cardListLength);
			
			rightCardLength = RightCardArray.length;
			wrongCardLength = WrongCardArray.length;

			
			$('#progress-left').empty();
			$('#progress-left').append(cardListLength);
			
			$('#progress-right').empty();
			$('#progress-right').append(rightCardLength);			
			
			$('#progress-wrong').empty();
			$('#progress-wrong').append(wrongCardLength);
										

		};//showProgress end
		
		console.log(WordCardArray);
		
		
		//주관식 문제 출력
		function showQuestion()		
		{
			$('.subjective-answerArea').focus();
			showProgress();
			//console.log("문제출제...");
			//console.log(cardListLength)
			if(cardListLength !== 0)
			{
				cardIndex = Math.floor(Math.random() * WordCardArray.length);
				console.log(cardIndex);
				
				random = WordCardArray[cardIndex];
				var question = random.word;

				/* console.log("단어: "+ cardWord); */

				$('.question-text').empty();
				$('.question-text').append(question);
				//$('.flip-card-back').append(cardMeaning);								
			}			
			else
			{
				result();
			}
			
		
		};//end showCard

		showQuestion();
		
		//정답확인
		function checkCorrect()
		{
			var userAswer = $('.subjective-answerArea').val();
			
			console.log("내가 쓴 답 : " + userAswer)
			if(userAswer == random.meaning)
			{
				
				//정답Array에 추가
				addRightCardArray(random.word, random.meaning);
				console.log(RightCardArray);
				
				//array에서 삭제
				WordCardArray.splice(cardIndex,1);
				//console.log(WordCardArray);
				
				
				//정답창 비우고 다음문제 출제
				$('.subjective-answerArea').val('');
				showQuestion();
			}
			else
			{		
				//오답Array에 추가
				addWrongCardArray(random.word, random.meaning);
				console.log(WrongCardArray);
				
				//array에서 삭제
				WordCardArray.splice(cardIndex,1);
				
				//오답페이지 출력
				feedbackWrongAns();
				//정답창 비우고 다음문제 출제. 추후 오답페이지로 바꿀거임
				//$('.subjective-answerArea').val('');
				//showQuestion();
			}
		}
		
		//정답버튼
		var checkCorrectBtn = $('.subjective-submitBtn');
		checkCorrectBtn.on("click", function(e)
			{			
				e.preventDefault();
				checkCorrect();
				
			});
		
		//정답확인- enter
		$('.subjective-answerArea').keypress(function(e){
			if(e.keyCode == 13 && !e.shiftKey){
				e.preventDefault();
				checkCorrect();
			}
		});
		
		//계속하기버튼
		$(document).on("click","#continueBtn",function(e){
			e.preventDefault();
			console.log("다시하기...");
			console.log("남은카드 : "+cardListLength);
			var allCardLength = cardListLength + rightCardLength + wrongCardLength;
			if(cardListLength == 0)
			{
				resetData();			
			}
			else
			{
				resetSubjectiveDiv();
				showQuestion();
			}
		});
		
		
		
		function addRightCardArray(word, meaning){
			RightCardArray.push({
				word : word,
				meaning : meaning			
			});
		}
		
		function addWrongCardArray(word, meaning){
			WrongCardArray.push({
				word : word,
				meaning : meaning			
			});
		}
		
		//문제페이지 다시 보이기
		function resetSubjectiveDiv(){
			$('.result-feedback').remove();
			$('.quizDiv').show();
			$('.subjective-answerArea').val('');
		};
		
		
		//결과보기(중간)
		function result() {
			if(wrongCardLength !== 0){
				var allCardLength = cardListLength+rightCardLength+wrongCardLength;
				//cardListLength = cardListLength+rightCardLength+wrongCardLength;
				var rightPer = Math.round((rightCardLength/allCardLength)*100);
				var wrongPer = Math.round((wrongCardLength/allCardLength)*100);
				var progressPer = Math.round((rightCardLength/allCardLength)*100);
				
				var resultinnDiv = $('.subjectiveQuestion');
				var txt = '<div class="result-feedback"><div class="feedback-box" id="rightResult">';
				txt += '<div class="feedback-description">정답</div>';
				txt += '<div class="feedback-count">'+RightCardArray.length+'</div>';
				txt += '<div class="feedback-percentage">'+rightPer+'%'+'</div>';
				txt += '</div><div class="feedback-box" id="wrongResult">';
				txt += '<div class="feedback-description">오답</div>';
				txt += '<div class="feedback-count">'+WrongCardArray.length+'</div>';
				txt += '<div class="feedback-percentage">'+wrongPer+'%'+'</div></div>';
				txt += '<div class="feedback-box"><div class="feedback-description">전체 진행률</div>';
				txt += '<div class="feedback-count">'+RightCardArray.length+'/'+allCardLength+'</div>';
				txt += '<div class="feedback-percentage">'+progressPer+'%'+'</div></div>';				
				txt += '<div class="continueBtnBox"><button id="continueBtn" type="button">계속하시려면 클릭하세요';
				txt += '</button></div>';
				
				$('.quizDiv').hide();
				$('.subjectiveQuestion').append(txt);				
			}
			else
			{
				showFinalResult();
			}
		};//.result()
		
		
		//중간데이터 세팅
		function resetData(){
			console.log(wrongCardLength);
			if(wrongCardLength ==0)
			{
				WordCardArray = RightCardArray;
				RightCardArray = [];
			}
			else
			{
				WordCardArray = WrongCardArray;
				WrongCardArray = [];
			}			
			resetSubjectiveDiv();
			showQuestion();
		}
		
		
		//오답화면 출력
		function feedbackWrongAns()
		{
			var userAswer = $('.subjective-answerArea').val();
			var resultinnDiv = $('.subjectiveQuestion');
			var txt = '<div class="result-feedback">';
			txt += '<div class="wrongAnsFeedback"><div class="feedback-header"><i class="far fa-tired"></i>';
			txt += '학습이 필요해요!</div>';
			txt += '<div class="feedback-boxs"><div class="wrongAns-feedback-box">';
			txt += '<div class="feedback-label">문제</div>';
			txt += '<div class="feedback-content">'+ random.word +'</div></div>';
			txt += '<div class="wrongAns-feedback-box">';
			txt += '<div class="feedback-label">입력하신 답</div>';
			txt += '<div class="feedback-content">'+ userAswer +'</div></div>';
			txt += '<div class="wrongAns-feedback-box">';
			txt += '<div class="feedback-label">정답</div>';
			txt += '<div class="feedback-content" id="content-correct">'+ random.meaning +'</div></div>';
			txt += '</div>';
			txt += '<div class="continueBtnBox"><button id="continueBtn" type="button">계속하시려면 클릭하세요';
			txt += '</button></div>';
			
			$('.quizDiv').hide();
			$('.subjectiveQuestion').append(txt);	
		}; 
		
		
		//최종 결과 화면
		function showFinalResult(){
			//debugger;
			var txt = '<div class="finalResult">';
			console.log(RightCardArray);
			console.log(rightCardLength);

			for(i=0; i<rightCardLength;i++)
			{
				txt += '<div class="resultBox" id="rightResult">';
				txt += '<div class="feedback-word">'+RightCardArray[i].word+'</div>';
				txt += '<div class="feedback-meaning">'+RightCardArray[i].meaning+'</div></div>';
			}
			txt += '<div class="resetBtn"><button id="resetBtn" type="button">처음부터 다시하기</button>';
			txt += '</div></div>';
			
			$('.quizDiv').hide();
			$('.subjectiveQuestion').append(txt);
		}
		
		//처음부터 다시하기 버튼
		$(document).on("click","#resetBtn",function(e){
			e.preventDefault();
			window.location.reload();
		});
		
		
		//모르겠어요 버튼
		$(document).on("click","#dontKnowBtn",function(e){
			e.preventDefault();
			feedbackWrongAns();
		});
		
		
		
	});//end doc.ready 

	
		
	</script>

</body>
</html>