if(!window.console)  window.console = {log:function(data){  }  }; 
if(window.io) {
var socket = io.connect('nodejs.alarabiya.net', {port : 4444});

socket.on('connect', function(){ console.log('connected: client'); });

socket.on('news', function(data) {
	
	console.log(data);	
	
	if ($.trim(data) != '' && isNaN(data)) {
			
			$('#urgentNews').show();
			$('.header-frequencies-container').slideUp().fadeOut('fast');
			$('.header-urgent-news-container').slideUp().fadeOut('fast',function() {
				$('#urgentNews').html(data);
				$('.header-urgent-news-container').slideDown().fadeIn();				
			});
	} else {
		$('.header-urgent-news-container').slideUp().fadeOut();
	}
});


$(window).scroll(function(){
	    threshold = $(window).scrollTop() ;
	    if(threshold > 174)
	    	$('.header-urgent-news-container').addClass('sticky');
		else
			$('.header-urgent-news-container').removeClass('sticky');
	
});
}
	
