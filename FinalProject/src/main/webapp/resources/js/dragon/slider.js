

var $container = $('#costume').width()-90;//탭 크기 제외한 이미지 슬라이더 panel width
var $container_item = $('#inventory').width();
var $display = 6;
var $display_item = 4;//보여지는 아이템 개수

if($container>=540){
		$display = 5;
	}else if($container>=420&&$container<540){
		$display = 4;
	}else if($container<420){
		$display = 3;
	}
	if($container_item<630){
		$display_item = 3;
	}
var $item = $container / $display;
var $item_item = ($container_item / $display_item)-35;
var $count = $('.button_item').length;
var $slidebox = $item_item * $count;
var $count_dg = $('#inven-dg .costume-card').length;
var $slidebox_dg = $item * $count_dg;
var $count_bg = $('#inven-background .costume-card').length;
var $slidebox_bg = $item * $count_bg;
init();

$(window).resize(function() {//윈도우 사이즈 변경 시
	$container = $('#costume').width()-90;
	$container_item = $('#inventory').width();
	
	if($container>=540){
		$display = 5;
	}else if($container>=420&&$container<540){
		$display = 4;
	}else if($container<420){
		$display = 3;
	}
	if($container_item<630){
		$display_item = 3;
	}else{
		$display_item = 4;
	}
	console.log("리사이즈 후 " +$container_item);
	console.log("리사이즈 후 " +$display_item);
	$item = $container / $display;
	$item_item = ($container_item / $display_item)-35;
	
	$slidebox = $item_item * $count;
	$slidebox_dg = $item * $count_dg;
	$slidebox_bg = $item * $count_bg;
	init();
});


function init(){//이미지 슬라이더 너비 초기화
	$count = $('.button_item').length;
	$('.btn-array').css('width',$slidebox);
	$('#inven-dg').css('width',$slidebox_dg);
	$('#inven-background').css('width',$slidebox_bg);
	//$('.button_item').outerWidth($item_item, true);
	$('.button_item').css('width',100);
	$('.costume-card').css('width',$item);
}

function itemSlider() {//이미지 슬라이더 함수(아이템 선택)
	var check = $(this).attr('data-btn');
	if(check==0){
		$('.btn_array').animate({left:'+='+$item_item+"px"},300,slideEnd);
	}else if(check==1){
		$('.btn_array').animate({left:'-='+$item_item+"px"},300,slideEnd);
	}
}
function dragonListSlider() {//이미지 슬라이더 함수(용 선택)
	var check = $(this).attr('data-btn');
	if(check==0){
		$('.btn_array-costume').animate({left:'+='+$item+"px"},300,slideEndDragon);
	}else if(check==1){
		$('.btn_array-costume').animate({left:'-='+$item+"px"},300,slideEndDragon);
	}
}
function backgroundSlider() {//이미지 슬라이더 함수(배경 선택)
	var check = $(this).attr('data-btn');
	if(check==0){
		$('#inven-background').animate({left:'+='+$item+"px"},300,slideEndBackground);
	}else if(check==1){
		$('#inven-background').animate({left:'-='+$item+"px"},300,slideEndBackground);
	}
}
function slideEnd(){//이미지 슬라이더 제한(아이템 선택)
	var nowLeft = $('.btn_array').position().left;
	
	var end = -($slidebox-$container_item+(35*3));
	console.log(nowLeft);
	console.log(end);
	
	if(nowLeft<=end){
		if($count<4){
			$('.btn_array').animate({left:0});
		}else{
			$('.btn_array').animate({left:end});	
		}
	}else if(nowLeft>0){
		$('.btn_array').animate({left:0});
	}
}
function slideEndDragon(){//이미지 슬라이더 제한(용 선택)
	var nowLeft = $('#inven-dg').position().left;
	
	var end = -($slidebox_dg-$container-55);
	console.log(nowLeft);
	console.log(end);
	
	if(nowLeft<=end){
		if($count_dg<4){
			$('#inven-dg').animate({left:60});
		}else{
			$('#inven-dg').animate({left:end});	
		}
	}else if(nowLeft>0){
		$('#inven-dg').animate({left:60});
	}
}
function slideEndBackground(){//이미지 슬라이더 제한(배경 선택)
	var nowLeft = $('#inven-background').position().left;
	
	var end = -($slidebox_bg-$container-55);
	console.log(nowLeft);
	console.log(end);
	
	if(nowLeft<=end){
		if($count_bg<4){
			$('#inven-background').animate({left:60});
		}else{
			$('#inven-background').animate({left:end});	
		}
	}else if(nowLeft>0){
		$('#inven-background').animate({left:60});
	}
}

$(document).ready(function(){
	init();
});