// <![CDATA[
(function laxPlayEntry() {
	'use strict';

	function playEntry($clearForm, $faceOffForm, $goalForm, $groundBallForm,
			$penaltyForm, $shotForm) {
		function post(url, data, token) {
			var df;

			df = $.ajax({
				data : JSON.stringify(data),
				dataType : 'text',
				headers : {
					'Accept' : 'text/html',
					'Content-Type' : 'application/json',
					'X-CSRF-TOKEN' : token
				},
				method : 'POST',
				url : url
			});

			return df;
		}

		function postSuccess(panelNum, data, status, jqXHR) {
			var $panel;

			console.log(data);
			console.log('Status: ' + status);
			console.log(jqXHR);

			$('#real-time-entry').carousel(0);
			$('#play-index').html(data);
		}

		function fail(jqXHR, status, error) {
			var msg = 'Error (' + jqXHR.status + '): ' + jqXHR.responseText;
			console.log('Error: ' + msg);
		}

		function getPanel(panelNum) {
			var $panel = null;
			switch (panelNum) {
			case 1:
				$panel = $('#goal');
				break;
			case 2:
				$panel = $('#shot');
				break;
			case 3:
				$panel = $('#groundBall');
				break;
			case 4:
				$panel = $('#penalty');
				break;
			case 5:
				$panel = $('#clear');
				break;
			case 6:
				$panel = $('#faceOff');
				break;
			}
			return $panel;
		}

		function setFormElementHandlers(panelNum) {
			switch (panelNum) {
			case 1:
				$goalForm = $('#real-time-goal-form');
				$goalForm.submit(createGoal);
				break;
			case 2:
				$shotForm = $('#real-time-shot-form');
				$shotForm.submit(createShot);
				break;
			case 3:
				$groundBallForm = $('#real-time-ground-ball-form');
				$groundBallForm.submit(createGroundBall);
				break;
			case 4:
				$penaltyForm = $('#real-time-penalty-form');
				$penaltyForm.submit(createPenalty);
				break;
			case 5:
				$clearForm = $('#real-time-clear-form');
				$clearForm.submit(createClear);
				break;
			case 6:
				$faceOffForm = $('#real-time-faceoff-form');
				$faceOffForm.submit(createFaceOff);
				break;
			}
		}

		/*---------- Public methods ----------*/

		function createClear(event) {
			var url = $clearForm.attr('action'), data = {
				playType : $clearForm.find('#play-type').val(),
				playKey : $clearForm.find('#play-key').val(),
				gameId : $clearForm.find('#game-id').val(),
				teamSeasonId : $clearForm.find('#team-season-id').val(),
				period : $clearForm.find('#period').val(),
				result : $clearForm.find('#result').val(),
				comment : $clearForm.find('#comments').val()
			}, token = $clearForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data, status, jqXHR) {
				postSuccess(5, data, status, jqXHR);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function createFaceOff(event) {
			var url = $faceOffForm.attr('action'), data = {
				gameId : $faceOffForm.find('#game-id').val(),
				teamSeasonId : $faceOffForm.find('#team-season-id').val(),
				period : $faceOffForm.find('#period').val(),
				elapsedTime : $faceOffForm.find('#clock').val(),
				winnerId : $faceOffForm.find('#winner').val(),
				loserId : $faceOffForm.find('#loser').val(),
				comment : $faceOffForm.find('#comments').val()
			}, token = $faceOffForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data) {
				postSuccess(6, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function createGoal(event) {
			var url = $goalForm.attr('action'), data = {
				gameId : $goalForm.find('#game-id').val(),
				teamSeasonId : $goalForm.find('#team-season-id').val(),
				period : $goalForm.find('#period').val(),
				elapsedTime : $goalForm.find('#clock').val(),
				scorerId : $goalForm.find('#scorer').val(),
				attemptType : $goalForm.find('#attempt-type').val(),
				assistId : $goalForm.find('#assist').val(),
				comment : $goalForm.find('#comments').val()
			}, token = $goalForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data) {
				postSuccess(1, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function createGroundBall(event) {
			var url = $groundBallForm.attr('action'), data = {
				gameId : $groundBallForm.find('#game-id').val(),
				teamSeasonId : $groundBallForm.find('#team-season-id').val(),
				period : $groundBallForm.find('#period').val(),
				playerId : $groundBallForm.find('#player').val(),
				comment : $groundBallForm.find('#comment').val()
			}, token = $groundBallForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data) {
				postSuccess(3, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function createPenalty(event) {
			var url = $penaltyForm.attr('action'), data = {
				gameId : $penaltyForm.find('#game-id').val(),
				teamSeasonId : $penaltyForm.find('#team-season-id').val(),
				period : $penaltyForm.find('#period').val(),
				elapsedTime : $penaltyForm.find('#clock').val(),
				violationId : $penaltyForm.find('#violation').val(),
				committedById : $penaltyForm.find('#violator').val(),
				committedAgainstId : $penaltyForm.find('#against').val(),
				duration : $penaltyForm.find('#duration').val(),
				comment : $penaltyForm.find('#comments').val()
			}, token = $penaltyForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data) {
				postSuccess(4, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function createShot(event) {
			var url = $penaltyForm.attr('action'), data = {
				gameId : $shotForm.find('#game-id').val(),
				teamSeasonId : $shotForm.find('#team-season-id').val(),
				period : $shotForm.find('#period').val(),
				playerId : $shotForm.find('#shooter').val(),
				attemptType : $shotForm.find('#attempt-type').val(),
				result : $shotForm.find('#result').val(),
				comment : $penaltyForm.find('#comment').val()
			}, token = $shotForm.find("input[name='_csrf']").val(), df;
			event.preventDefault();
			df = post(url, data, token);

			df.done(function(data) {
				postSuccess(2, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function cancelPlay() {
			$header.css('display', 'block');
			$carousel.carousel(0);
		}

		function newPlay() {
			var game = $('#game').data('game'), playType = $(this).data('play'), team = $(
					this).data('team'), panel = parseInt($(this).data('panel')), teamSeason = team == 1 ? $(
					'#team-1').data('team')
					: $('#team-2').data('team'), state = laxstats.Timer
					.getCurrentTime(), df;

			var url = '/admin/events/' + game + '/teamSeasons/' + teamSeason
					+ '/' + playType + '/new', query = '?p=' + state.currPeriod
					+ '&t=' + state.secsElapsed;

			df = $.ajax({
				url : url + query,
				type : 'GET',
				dataType : 'text'
			});

			df.done(function(data) {
				var $panel = getPanel(panel);
				if ($panel) {
					$panel.html(data);

					setFormElementHandlers(panel);
					$('#real-time-header').css('display', 'none');
					$('.lax-btn-cancel').on('click', cancelPlay);
					$('#real-time-entry').carousel(panel);
				}
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		return {
			createClear : createClear,
			createFaceOff : createFaceOff,
			createGoal : createGoal,
			createGroundBall : createGroundBall,
			createPenalty : createPenalty,
			createShot : createShot,
			cancelPlay : cancelPlay,
			newPlay : newPlay
		}
	}

	var $clearForm = $('#real-time-clear-form'), $faceOffForm = $('#real-time-faceoff-form'), $goalForm = $('#real-time-goal-form'), $groundBallForm = $('#real-time-ground-ball-form'), $penaltyForm = $('#real-time-penalty-form'), $shotForm = $('#real-time-shot-form'), $header = $('#real-time-header'), $carousel = $('#real-time-entry');

	laxstats.module('Play', [ $clearForm, $faceOffForm, $goalForm,
			$groundBallForm, $penaltyForm, $shotForm ], playEntry);
})();
// }}>
