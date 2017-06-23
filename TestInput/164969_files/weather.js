(function($) {
	String.prototype.trim = function() {
		return this.replace(/\s{2,}/g, " ").replace(/^\s+|\s+$/g,"");
	}

	$.fn.initWeatherWidget = function () {
		var defaultW = "AEXX0004"; //Riyadh = SAXX0017
		var webroot = $.JSvars.HOST_FORGIEN_URL;
		var pAjaxRequestUrl = webroot+"weather/searchplaces.php";
		$.ajaxSetup({
			cashe: false
		});
		$('#searchbox').autocomplete(pAjaxRequestUrl, {
			autoFill: false,
			cacheLength: 10,
			delay: 200,
			matchContains: false,
			selectFirst: true,
			minChars: 3
		});
		$('#searchbox').result(function (event, data, formatted) {
			if (data) {
				$.cookie("weather_w", data[1], {expires: 30, path: '/'});
				hidePlaceOverlay();
				getWeather();
			}
		});
		$('#searchbox').focus(function () {
			$(this).val("");
		});
		$('#searchform').submit(function () {
			hidePlaceOverlay();
			showLoadingOverlay();
			var pRequest = pAjaxRequestUrl+"?q="+$('#searchbox').val();
			$.ajax({
				url: pRequest,
				success: function (data) {
					var places = data.split("\n");
					w = places[0].split("|")[1];
					if (w == "0") w = defaultW;
					$.cookie("weather_w", w, {expires: 30, path: '/'});
					hideLoadingOverlay();
					getWeather();
				}
			});
			return false;
		});
		function getWeather() {
			var w = $.cookie("weather_w");
			// if this is the first time "never located a city before", get the geo location
			if (w === null) {
				showLoadingOverlay();
				var geoLocationRequestUrl = webroot+"weather/geolocation.php";
				$.ajax({
					url: geoLocationRequestUrl,
					success: function (data) {
						var places = data.split("\n");
						w = places[0].split("|")[1];
						if (w == "0") w = defaultW;
						$.cookie("weather_w", w, {expires: 30, path: '/'});
						getWeather();
					}
				});
				return false;
			}
			// if unit is not set yet, set to default
			if (!$.cookie("weather_u")) {
				setUnit();
			}
			// if place is loaded, we can get weather
			if (w.length) {
				if (w == "0" || w == 'undefined') {
					w = defaultW;
					$.cookie("weather_w", w, {expires: 30, path: '/'});
				}
				if (!document.loadingOverlayVisiblity) {
					showLoadingOverlay();
				}
				var u = $.cookie("weather_u");
				cache = Math.round((new Date).getTime()/3600000);
				var wAjaxRequestUrl = webroot+"weather/getweather.php?w="+w+"&u="+u+"&cache="+cache;
				$.ajax({
					url: wAjaxRequestUrl,
					cache: true,
					dataType: 'json',
					success: function (data) {
						if (data) {
							$('#condition_image').css('background', 'url("'+webroot+'assets/ar/modules/weather/images/icons/'+data.condition_code+'.jpg") scroll no-repeat top left');
							for (key in data) {
								key = key || false;
								if (key) {
									$('#'+key).text(data[key]);
									if (eval('typeof keyFunc_'+key+' == "function"')) {
										eval('keyFunc_'+key+'("'+data[key]+'")');
									}
								}
							}
							hideLoadingOverlay();
						}
					},
					error: function() {
						$.cookie("weather_w", defaultW, {expires: 30, path: '/'});
						//getWeather();
					}
				});
			}
			return false;
		}
		function keyFunc_yweatherforecast_high1(value) {
			if (value == "N/A" || value == "") {
				$('#yweatherforecast_high1').parent().hide();
			} else {
				$('#yweatherforecast_high1').parent().show();
			}
		}
		function setUnit(u) {
			var units = new Array("c", "f");
			if (u === undefined || $.inArray(u, units) < 0) {
				u = $.cookie("weather_u") ? $.cookie("weather_u") : "c";
			}
			$.cookie("weather_u", u, {expires: 30, path: '/'});
			$('#units').children("[unit]").addClass("active").removeClass("inactive");
			$('#units').children("[unit="+u+"]").addClass("inactive").removeClass("active");
		}
		$('#units').children("[unit]").click(function () {
			setUnit($(this).attr("unit"));
			getWeather();
		});
		$('#changeplacelink').click(function () {
			showPlaceOverlay();
		});
		$('#change-city').click(function () {
			$(this).submit();
		});
		$('#cancel').click(function () {
			hidePlaceOverlay();
		});
		$('#detailslink').hover(function () {
			$('#weatherdetails').stop().fadeTo(200, '1', function () {
				$(this).show();
			});
		}, function () {
			$('#weatherdetails').stop().fadeTo(200, '0', function () {
				$(this).hide();
			});
		});
		function showLoadingOverlay() {
			setOverlay();
			$('#loadingOverlay').fadeIn();
			document.loadingOverlayVisiblity = true;
		}
		function hideLoadingOverlay() {
			$('#loadingOverlay').hide();
			document.loadingOverlayVisiblity = false;
			unsetOverlay();
		}
		function showPlaceOverlay() {
			setOverlay();
			$('#changecityOverlay').fadeIn();
		}
		function hidePlaceOverlay() {
			unsetOverlay();
		}
		function setOverlay() {
			$('.weatherdata').hide();
			$('#overlay').show();
			return;
		}
		function unsetOverlay() {
			$('#overlay').hide();
			$('#changecityOverlay').hide();
			$('#loadingOverlay').hide();
			$('#weatherdetails').hide();
			$('.weatherdata').fadeIn();
		}
		unsetOverlay();
		setUnit();
		getWeather();
	}
})(jQuery);