(function laxMobile() {
    'use strict';
    
    var slideOptions = {
        sl: ['slin', 'slout'],
        sr: ['srin', 'srout']
    }

    function navSlide() {
        var $in = $('#' + $(this).data('target')), 
            $out = $('section.active'),
            slideType = $(this).data('slide');
        
        function onAnimationEnd() {
            $out.addClass('hidden');
            $in.addClass('active');
            $in.removeClass(slideOptions[slideType][0]);
            $out.removeClass(slideOptions[slideType][1]);
            $out.off('webkitAnimationEnd', onAnimationEnd);
            $out.off('animationend', onAnimationEnd);
        }

        $out.on('webkitAnimationEnd', onAnimationEnd);
        $out.on('animationend', onAnimationEnd);

        $out.removeClass('active');
        $in.removeClass('hidden');
        $in.addClass(slideOptions[slideType][0]);
        $out.addClass(slideOptions[slideType][1]);
    }

    function linkSlide() {
        var $in = $('#view-base'),
            url = $(this).data('url'),
            $that = $(this), df;
        
        df = $.ajax({
            url: url,
            type: 'GET',
            dataType: 'text/html'
        });
        
        df.done(function (data, status, xhr) {
            $in.html(data);
            $that.on('click', navSlide);
            $that.click();
            $that.off('click', navSlide);
        });
        
        df.fail(function (xhr, status, error) {
            var msg = 'Error (' + xhr.status + '): ' + xhr.responseText;
            console.log('Error: ' + msg);
        });
    }

    function geoFindMe() {
        var options = {
            enableHighAccuracy : true,
            timeout : 5000,
            maximumAge : 0
        };

        function success(pos) {
            var crd = pos.coords;
            console.log('Your current position is:');
            console.log('Latitude : ' + crd.latitude);
            console.log('Longitude: ' + crd.longitude);
            console.log('More or less ' + crd.accuracy + ' meters.');
        }

        function error(err) {
            console.log('ERROR(' + err.code + '): ' + err.message);
        }

        navigator.geolocation.getCurrentPosition(success, error, options);
    }

    function setListeners() {
        var $nav = $('header button'), $listItems = $('#view-nav li');

        $nav.each(function () {
            $(this).on('click', navSlide);
        });
        $listItems.each(function () {
            $(this).on('click', linkSlide);
        });
    }

    function setup(window) {
        function ensure(obj, name, factory) {
            return obj[name] || (obj[name] = factory());
        }

        var laxstats = ensure(window, 'laxstats', Object);

        return ensure(laxstats, 'module', function() {
            return function(name, args, fn) {
                laxstats[name] = fn(args);
            }
        });
    }

    setup(window);

    $(document).ready(function () {
        geoFindMe();
        setListeners();
    });
})();