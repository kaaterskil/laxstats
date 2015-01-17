(function laxEvents($) {
	'use strict';
	
	function setTeamData(seasonId) {
		var url = "/rest/events/teams/" + seasonId;
		$.get(url, function(data){
			var $teamOne = $('#team-one'), 
			$teamTwo = $('#team-two');
			
			$teamOne.empty();
			$teamTwo.empty();
			data.each(function(elem){
				$teamOne.append($('<option></option>').attr('value', elem.id).text(elem.name));
				$teamTwo.append($('<option></option>').attr('value', elem.id).text(elem.name));
			});
		})
		.fail(function(xhr, status, error) {
			console.log(status);
		});;
	}
	
	$('#season').on('change', function() {
		var season = $(this).val();
		setTeamData(season);
	});
})(jQuery);