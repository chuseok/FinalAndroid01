<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
 
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>암기용 - 단어장</title>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="stylesheet" href="/resources/css/get.css">
  
  <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">

</head>
<body>

<%@ include file="../includes/header.jsp" %>

	<div class="HomeLayout">
		<div class="HomeLayout-container">
			<section class="HomeLayout-main">
				<div class="mainWrapper">
				<h2 class="wordTitle-two"></h2>
					<div class="mainContents">
						
						<!-- 단어카드 -->
						<section class="Section-WordCard">
							<div class="flip-card">
								<div class="flip-card-inner">
									<div class="flip-card-front"></div>
									<div class="flip-card-back"></div>
								</div>
							</div>
							<div class="progressNBtns">
								<button type="button" class="prevCard">
									<i class="fas fa-arrow-left"></i>
								</button>
								<div class="cardProgress"></div>
								<button type="button" class="nextCard">
									<i class="fas fa-arrow-right"></i>
								</button>
							</div>
						</section>
						
						<!-- 이동하기 -->
						<section class="Section-SetPageModsGroup">
							<div class="UIRow">
								<div class="SetPageMods-groups">
									<div class="SetPageMods-groupLabel">
										<h2 class="UIHeading-two">학습</h2>
									</div>
									<div class="SetPageMods-buttonWrapper">
										<div class="SetPageMods-button">
											<span>학습하기</span> 
											<div class="SetPageMods-button-Linkbox"><a class="SetPageMods-buttonWrapper-a"	href="/study"></a></div>
										</div>
										<div class="SetPageMods-button">
											<span>주관식</span> 
											<div class="SetPageMods-button-Linkbox"><a class="SetPageMods-buttonWrapper-a" href="/subjective"></a></div>
										</div>
										<div class="SetPageMods-button">
											<span>테스트</span> 
											<div class="SetPageMods-button-Linkbox"><a class="SetPageMods-buttonWrapper-a" href="/test"></a></div>
										</div>
									</div>
								</div>
							</div>
							
						</section>
						<!-- /.UIRow -->

						<section class="Section-WordLists">
						<div class="listOfWords">
							<h2 class="UIHeading-two">이 세트의 단어<span class="numOfList"></span></h2>

							<table class="oneOfWords">
								<tbody>

									<c:if test="${WordDTO.size() ==0 }">
										<td colspan="2">아직 단어가 없습니다.</td>
									</c:if>

									<c:forEach items="${WordDTO}" var="list">
										<form id="operForm" action="/learn/study">
											<input type="hidden" name="id" value="<c:out value='${list.id }' />">
											<input type="hidden" name="title" value="<c:out value='${list.title }' />">
										</form>
										
										<c:forEach items="${list.item}" var="item">
											<tr>
												<td class="item-word"><c:out value='${item.word }' /></td>
												<td class="item-meaning"><c:out	value='${item.meaning }' /></td>
											</tr>
										</c:forEach>
									</c:forEach>

								</tbody>
							</table>
						</div>
						</section>
						<!-- /.listOfWords -->
						
					</div>
				</div>
			</section>
			
		</div>
	</div>



<script>

	$(document).ready(function(){
		console.log('${WordDTO}');
		
		var WordJsonArray;
		var WordCardArray;
		
		var cardIndex;
		var cardListLength;
		
		
		function initData() {
			
			WordJsonArray =  JSON.parse('${WordDTO}');
			WordCardArray = WordJsonArray[0]["item"];
			
			cardIndex = 0;
			cardListLength = WordCardArray.length;
			
		}
		initData();
		
		//oper-form value전달
		var userId = WordJsonArray[0]["id"];
		var userTitle = WordJsonArray[0]["title"];
		
		$('input[name="id"]').val(userId);
		//console.log($('input[name="id"]').val());
		$('input[name="title"]').val(userTitle);
		//console.log($('input[name="title"]').val());


		$('.numOfList').append('('+cardListLength+')');
		//단어장 제목 출력
		$('.wordTitle-two').append(WordJsonArray[0]["title"]);
		
		/* console.log(WordJsonArray[0]["title"]); */
		/* console.log('${WordDTO}');
		
		console.log(WordCardArray);
		console.log(WordCardArray[cardIndex]);
		console.log(WordCardArray[cardIndex].word);
		console.log(WordCardArray[cardIndex].meaning); */
		
		
		var cardProgressIndex;

		//card progress 출력

		function showProgress() 
		{
					var cardProgressIndex = cardIndex+1;
			
					$('.cardProgress').empty();

					/* console.log("단어장 길이: "+ cardListLength); */
					$('.cardProgress').append(
							cardProgressIndex + "/" + cardListLength);

					if (cardProgressIndex == 1) {
						
						$('.prevCard').attr('disabled', true);
						$('.nextCard').attr('disabled', false);
						
					} else if (cardProgressIndex == cardListLength) {
						
						$('.nextCard').attr('disabled', true);
						$('.prevCard').attr('disabled', false);
						
					} else {
						
						$('.prevCard').attr('disabled', false);
						$('.nextCard').attr('disabled', false);
						
					};

		}//showProgress end
		
		
		function showCard()		
		{
			//card출력
			var cardWord = WordCardArray[cardIndex].word;
			var cardMeaning = WordCardArray[cardIndex].meaning;

			/* console.log("단어: "+ cardWord); */

			$('.flip-card-front').append(cardWord);
			$('.flip-card-back').append(cardMeaning);
			
			showProgress();
		
		};//end showCard
		
		//card click - flip animation
		var card = document.querySelector('.flip-card-inner');
		card.addEventListener('click', function() 
		{
			card.classList.toggle('is-flipped');
		});	
		
		showCard();
		
		var nextBtn = $('.nextCard');
		var prevBtn = $('.prevCard');
		
		nextBtn.on("click", function(e){
			
			$('.flip-card-front').empty();
			$('.flip-card-back').empty();
			cardIndex += 1;
			showCard(cardIndex);			
			//console.log(cardIndex)
		});
		
		prevBtn.on("click", function(e){
			
			$('.flip-card-front').empty();
			$('.flip-card-back').empty();
			cardIndex -= 1;
			showCard(cardIndex);			
			//console.log(cardIndex)
		});
		
		
		var operForm = $("#operForm");		
		$('a[href="/study"]').click(function(e){			
			e.preventDefault();
			operForm.submit();
		});
		
		$('a[href="/subjective"]').click(function(e){			
			e.preventDefault();
			operForm.attr("action","/learn/subjective");
			operForm.submit();
		});
		
		$('a[href="/test"]').click(function(e){			
			e.preventDefault();
			operForm.attr("action","/learn/test");
			operForm.submit();
		});

});
</script>

</body>
</html>