// <![CDATA[
(function laxPlayEntry() {
	'use strict';

	function playEntry() {
		var PANELS = [{ id : '#real-time-entry', playType : 'carousel' }, 
					{ id : '#goal', playType : 'goals' }, 
					{ id : '#shot', playType : 'shots' }, 
					{ id : '#groundBall', playType : 'groundBalls' }, 
					{ id : '#penalty', playType : 'penalties' }, 
					{ id : '#clear', playType : 'clears' }, 
					{ id : '#faceOff', playType : 'faceOffs' }];
		var $clearForm, $faceOffForm, $goalForm, $groundBallForm, $penaltyForm, $shotForm, that = this;

		function send(url, method, data, token) {
			var df;

			df = $.ajax({
				data : JSON.stringify(data),
				dataType : 'text',
				headers : {
					'Accept' : 'text/html',
					'Content-Type' : 'application/json',
					'X-CSRF-TOKEN' : token
				},
				method : method,
				url : url
			});
			return df;
		}

		function sendSuccess(panelNum, data, status, jqXHR) {
			$('#real-time-entry').carousel(0);
			$('#play-index').html(data);
			setPlayIndexHandlers();
		}

		function fail(jqXHR, status, error) {
			var msg = 'Error (' + jqXHR.status + '): ' + jqXHR.responseText;
			console.log('Error: ' + msg);
		}

		function getPanel(panelNum) {
			return $(PANELS[panelNum].id);
		}

		function getPanelByType(playType) {
			var $panel = null;

			PANELS.forEach(function(elem) {
				if (elem.playType == playType) {
					$panel = $(elem.id);
				}
			});
			return $panel;
		}

		function getPanelNum(playType) {
			var panelNum = -1;

			PANELS.forEach(function(elem, index) {
				if (elem.playType == playType) {
					panelNum = index;
				}
			});
			return panelNum;
		}

		function getMethod($form) {
			var method = $form.find("input[name='_method']").val();
			if (!method) {
				method = $form.attr('method');
			}
			return method;
		}
		
		function setPlayIndexHandlers() {
			$('.lax-btn-delete').on('submit', deletePlay);
			$('.lax-link-edit').on('click', editPlay);
		}

		function setFormElementHandlers(panelNum) {
			switch (panelNum) {
				case 1 :
					$goalForm = $('#real-time-goal-form');
					$goalForm.submit(saveOrUpdateGoal);
					break;
				case 2 :
					$shotForm = $('#real-time-shot-form');
					$shotForm.submit(saveOrUpdateShot);
					break;
				case 3 :
					$groundBallForm = $('#real-time-ground-ball-form');
					$groundBallForm.submit(saveOrUpdateGroundBall);
					break;
				case 4 :
					$penaltyForm = $('#real-time-penalty-form');
					$penaltyForm.submit(saveOrUpdatePenalty);
					break;
				case 5 :
					$clearForm = $('#real-time-clear-form');
					$clearForm.submit(saveOrUpdateClear);
					break;
				case 6 :
					$faceOffForm = $('#real-time-faceoff-form');
					$faceOffForm.submit(saveOrUpdateFaceOff);
					break;
			}
			$('.lax-btn-cancel').on('click', cancelPlay);
		}

		function saveOrUpdateClear(event) {
			var url = $(this).attr('action'), 
				data = {
					playType : $(this).find('#play-type').val(),
					playKey : $(this).find('#play-key').val(),
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					result : $(this).find('#result').val(),
					comment : $(this).find('#comment').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data, status, jqXHR) {
				sendSuccess(5, data, status, jqXHR);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function saveOrUpdateFaceOff(event) {
			var url = $(this).attr('action'), 
				data = {
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					elapsedTime : $(this).find('#clock').val(),
					winnerId : $(this).find('#winner').val(),
					loserId : $(this).find('#loser').val(),
					comment : $(this).find('#comments').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data) {
				sendSuccess(6, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function saveOrUpdateGoal(event) {
			var url = $(this).attr('action'), 
				data = {
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					elapsedTime : $(this).find('#clock').val(),
					scorerId : $(this).find('#scorer').val(),
					attemptType : $(this).find('#attempt-type').val(),
					assistId : $(this).find('#assist').val(),
					comment : $(this).find('#comments').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data) {
				sendSuccess(1, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function saveOrUpdateGroundBall(event) {
			var url = $(this).attr('action'), 
				data = {
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					playerId : $(this).find('#player').val(),
					comment : $(this).find('#comment').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data) {
				sendSuccess(3, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function saveOrUpdatePenalty(event) {
			var url = $(this).attr('action'), 
				data = {
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					elapsedTime : $(this).find('#clock').val(),
					violationId : $(this).find('#violation').val(),
					committedById : $(this).find('#violator').val(),
					committedAgainstId : $(this).find('#against').val(),
					duration : $(this).find('#duration').val(),
					comment : $(this).find('#comments').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data) {
				sendSuccess(4, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function saveOrUpdateShot(event) {
			var url = $(this).attr('action'), 
				data = {
					gameId : $(this).find('#game-id').val(),
					teamSeasonId : $(this).find('#team-season-id').val(),
					period : $(this).find('#period').val(),
					playerId : $(this).find('#shooter').val(),
					attemptType : $(this).find('#attempt-type').val(),
					result : $(this).find('#result').val(),
					comment : $(this).find('#comment').val()
				}, 
				token = $(this).find("input[name='_csrf']").val(), 
				method = getMethod($(this)), df;

			event.preventDefault();
			df = send(url, method, data, token);

			df.done(function(data) {
				sendSuccess(2, data);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		/*---------- Public methods ----------*/

		function cancelPlay() {
			$('#real-time-header').css('display', 'block');
			$('#real-time-entry').carousel(0);
		}

		function deletePlay(event) {
			var gameId = $(this).find('#id').val(), 
				playType = $(this).find("input[name='play-type']").val(), 
				playId = $(this).find("input[name='play-id']").val(), 
				token = $(this).find("input[name='_csrf']").val(), 
				url = '/admin/events/' + gameId + '/' + playType + '/' + playId, 
				df;

			event.preventDefault();

			df = $.ajax({
				url : url,
				method : 'delete',
				headers : {
					'X-CSRF-TOKEN' : token
				},
				dataType : 'text'
			});

			df.done(function(data, status, jqXHR) {
				$('#play-index').html(data);
				setPlayIndexHandlers();
				$('#real-time-entry').carousel(0);
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function editPlay() {
			var gameId = $(this).data('game'), 
				playType = $(this).data('play-type'), 
				playId = $(this).data('play'), 
				url = '/admin/events/' + gameId + '/' + playType + '/'
					+ playId + '/edit', df;

			df = $.ajax({
				url : url,
				method : 'GET',
				dataType : 'text'
			});

			df.done(function(data, status, jqXHR) {
				var $panel = getPanelByType(playType), panelNum = getPanelNum(playType);
				if ($panel) {
					$panel.html(data);
					setFormElementHandlers(panelNum);
					$('#real-time-header').css('display', 'none');
					$('#real-time-entry').carousel(panelNum);
				}
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function newPlay() {
			var game = $('#game').data('game'), 
				playType = $(this).data('play'), 
				team = $(this).data('team'), 
				panel = parseInt($(this).data('panel')), 
				teamSeason = team == 1
					? $('#team-1').data('team')
					: $('#team-2').data('team'), 
				state = laxstats.Timer.getCurrentTime(), 
				df, 
				url = '/admin/events/' + game + '/teamSeasons/' + teamSeason 
					+ '/' + playType + '/new', query = '?p='
					+ state.currPeriod + '&t=' + state.secsElapsed;

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
					$('#real-time-entry').carousel(panel);
				}
			});

			df.fail(function(jqXHR, status, error) {
				fail(jqXHR, status, error);
			});
		}

		function initialize() {
			$clearForm = $('#real-time-clear-form');
			$faceOffForm = $('#real-time-faceoff-form');
			$goalForm = $('#real-time-goal-form');
			$groundBallForm = $('#real-time-ground-ball-form');
			$penaltyForm = $('#real-time-penalty-form');
			$shotForm = $('#real-time-shot-form');

			$('.lax-btn-play').on('click', newPlay);
			setPlayIndexHandlers();
		}

		return {
			initialize : initialize,
			cancelPlay : cancelPlay,
			deletePlay : deletePlay,
			editPlay : editPlay,
			newPlay : newPlay
		}
	}

	laxstats.module('Play', [], playEntry);
})();
// }}>
