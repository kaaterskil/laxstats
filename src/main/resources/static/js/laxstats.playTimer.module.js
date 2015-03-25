// <![CDATA[
(function () {
	'use strict';
	
	function laxTimer($clock, $period){
		var PERIOD = 15 * 60, OVERTIME = 4 * 60;
		var inProgress = false, 
			currPeriod = 0, 
			elapsed = 0, 
			remaining = 0, 
			$clock = $('.lax-clock'), 
			$period = $('.lax-period'), 
			interval;

		function setTime() {
			localStorage.setItem('currPeriod', currPeriod);
			localStorage.setItem('elapsed', elapsed);
		}

		function getTime() {
			currPeriod = parseInt(localStorage.getItem('currPeriod'));
			elapsed = parseInt(localStorage.getItem('elapsed'));
		}

		function updateRemaining() {
			remaining = (currPeriod > 4) ? OVERTIME - elapsed : PERIOD
					- elapsed;
		}
		
		function updateClock() {
			var min = Math.floor(remaining / 60),
				sec = remaining - (min * 60);
			
			min = min.toString().length == 1 ? "0" + min : min;
			sec = sec.toString().length == 1 ? "0" + sec : sec;
			$clock.text(min + ":" + sec);
			$period.text(currPeriod);
		}
		
		function periodEnd() {
			return remaining <= 0;
		}
		
		function isInProgress() {
			return inProgress;
		}
		
		function getCurrentTime() {
			return {
				currPeriod: currPeriod, 
				secsElapsed: elapsed,
				secsRemaining: remaining
			}
		}

		function advancePeriod() {
			currPeriod += 1;
			elapsed = 0;
			updateRemaining();
			setTime();
			updateClock();
		}

		function stopTimer() {
			clearInterval(interval);
			setTime();
			inProgress = false;
		}

		function updateTimer() {
			elapsed = parseInt(elapsed) + 1;
			updateRemaining();
			if(periodEnd()) {
				stopTimer();
			}
			updateClock();
		}

		function startTimer() {
			getTime();
			updateRemaining();
			
			interval = setInterval(updateTimer, 1000);
			inProgress = true;
		}
		
		return {
			isInProgress: isInProgress,
			getCurrentTime: getCurrentTime,
			initialize: advancePeriod,
			start: startTimer,
			stop: stopTimer
		}
	}
	
	var $timerBtn = $('#play-timer'), 
		$clock = $('.clock'), 
		$period = $('.period');
	
	laxstats.module('Timer', [$clock, $period], laxTimer);
	
	$(document).ready(function() {
		laxstats.Timer.initialize();
		
		$timerBtn.click(function() {
			if(!laxstats.Timer.isInProgress()) {
				laxstats.Timer.start();
				$(this).text('Stop');
			} else {
				laxstats.Timer.stop();
				$(this).text('Resume');
			}
		});
	});
})();
// ]]>