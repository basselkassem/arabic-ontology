var host = $.JSvars.HOST_FORGIEN_URL;
var timestamp = new Date().getTime();
var brnewsurl = host+"brnews/script/index.php?c="+timestamp;
var _intervalBreakingNews = null;
var _breakingNewsDataIndex = 1;
jQuery.ajax({
	url: brnewsurl,
	success: function (data) {
		if (data.length > 0) {
			$('.header-frequencies-container').slideUp().fadeOut();
			$('#urgentNews').html(data);
			$('.header-urgent-news-container').slideDown().fadeIn();
		}
	}
});

