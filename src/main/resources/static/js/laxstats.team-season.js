//$(document).ready(function(){
//	var $season = $('#season');
//	$season.change(function(){
//		console.log('select element changed');
//	});
//});

(function(){
	
	console.log('IIFE ready');
	$('#season').change(function(){
		console.log('select element changed');
	});
})();