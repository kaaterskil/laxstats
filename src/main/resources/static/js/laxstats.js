(function(window){
	'use strict';
	
	function setup(window){
		function ensure(obj, name, factory){
			return obj[name] || (obj[name] = factory());
		}
		
		var laxstats = ensure(window, 'laxstats', Object);
		
		return ensure(laxstats, 'module', function(){
			return function(name, args, fn){
				laxstats[name] = fn(args);
			}
		});
	}
	
	setup(window);
})(window);
