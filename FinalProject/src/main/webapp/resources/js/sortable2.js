$(function() {
	$("ul").sortable({
		stop : function(event, ui) {
			var wordList = [];
			var arr = new Array();

		}
	});
	$("ul").disableSelection();
});

$('button').on("click", function(e) {
	e.preventDefault();
	/*var operation = $(this).data("oper");

	console.log(operation);

	if (operation === 'save') {
		formObj.attr("action", "/word/write");
	}
	formObj.submit();*/

	var wordList = [];
	var arr = new Array();
	
	var word = document.getElementsByName("word");
	var meaning = document.getElementsByName("meaning");
	
	for(i=0; i<word.length; i++) {

		var obj = new Object();
		obj.word = word[i].value;
		obj.meaning = meaning[i].value;
		console.log("word 값 : " + obj.word);
		console.log("meaning 값 : " + obj.meaning);
		arr.push(obj);
	}
	
	
	$("ul").children("li").each(function() {
		/* idList.push($(this).attr('id')); */
		
	});
	
	var item = JSON.stringify(arr);
	var wordTitle = $('input[name=word-title]').val();
	$.ajax({
		url : '/word/read',
		type : 'post',
		dataType : 'json',
		traditional : true,
		data : {"item" : item,
			"wordTitle" : wordTitle},
		success : function(response) {
			alert('리스트에 추가함.')
		}
	});
});