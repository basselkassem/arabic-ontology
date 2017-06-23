/* Misc. Website Javascript Functions
/* created by Anews Development Team */
function get_time_stamp()
{
	var time_stamp = new Date();
	time_stamp = time_stamp.getTime();
	return time_stamp;
}

function initAttachmentLightbox()
{
	$('a.lightbox-attachment').lightBox( {
		maxHeight: 1000,
		maxWidth: 1000
	});		
}


// Form validation for index file
function validateFormEn(adminpath,frm_letter)
{	
	var strErr = '';	

	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if ( ! (filter.test(document.frm_newsletter.fields_email.value))) 
		  strErr += 'Email not correct\n'; 
	if(strErr != '')
	{
		delCookie('fields_email');
		alert(strErr);
		return false;
	}

	else{ 
		//window.open(adminpath+"newsletter_web_subscriber_en.php?frm_email="+document.frm_newsletter.frm_email.value, "SUBSCRIBERS", "status=yes,width=300,height=275,resizable=no");		
		//var temp_ts = get_time_stamp();
		//var cookie_name = 'fields_email_'+temp_ts;
		var temp_redirect = document.frm_newsletter.redirect.value;
		var temp = new Array();
		temp = temp_redirect.split('?');
		temp_redirect = temp[0];
		var fields_email = document.getElementById('fields_email').value;
		//setCookie(cookie_name, fields_email, 1);
		document.frm_newsletter.redirect.value = temp_redirect+'?email='+fields_email;
		return true;
		}
}

	
// Form validation for index file
function validateForm(adminpath,frm_letter)
{
	var strErr = '';
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if ( ! (filter.test(document.frm_newsletter.fields_email.value))) 
		  strErr += 'عفوا.. عنوان البريد الإلكتروني الذي أدخلته غير صحيح'; 
	if(strErr != '')
	{
		delCookie('fields_email');
		alert(strErr);
		return false;
	}
	else{
		//window.open(adminpath+"newsletter_web_subscriber.php?frm_email="+document.frm_newsletter.frm_email.value, "SUBSCRIBERS", "status=yes,width=300,height=275,resizable=no");		
		//var temp_ts = get_time_stamp();
		//var cookie_name = 'fields_email_'+temp_ts;
		var temp_redirect = document.frm_newsletter.redirect.value;
		var temp = new Array();
		temp = temp_redirect.split('?');
		temp_redirect = temp[0];
		var fields_email = document.getElementById('fields_email').value;
		//setCookie(cookie_name, fields_email, 1);
		document.frm_newsletter.redirect.value = temp_redirect+'?email='+fields_email;
		return true;
	}
}



function validateSubscribeForm(url, error_str)
{
	
	var strErr = '';
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var email_val =  document.getElementById('fields_email2').value;
	if ( ! (filter.test(email_val))) 
	{
		  strErr += error_str; //'عفوا.. عنوان البريد الإلكتروني الذي أدخلته غير صحيح'; 
		  alert(strErr);  
	}
	else
	{
		document.location.href = url + '?email=' + email_val;
		delCookie('fields_email');
		return false;
	}

}



/* ew popup window in center*/
var win= null;
function NewWindow(mypage,myname,w,h,scroll)
{
	var winl = (screen.width-w)/2;
	var wint = (screen.height-h)/2;
	var settings  ='height='+h+',';
	settings +='width='+w+',';
	settings +='top='+wint+',';
	settings +='left='+winl+',';
	settings +='scrollbars=no,';
	settings +='resizable=yes';
	win=window.open(mypage,myname,settings);
	if(parseInt(navigator.appVersion) >= 4)
	{
		win.window.focus();
	}
}

function pop_stream()
{
	var w = 1000;
	var h = 700;
	var winl = (screen.width-w)/2;
	var wint = (screen.height-h)/2;
	var settings  ='height='+h+',';
	settings +='width='+w+',';
	settings +='top='+wint+',';
	settings +='left='+winl+',';
	settings +='scrollbars=no,';
	settings +='location=no,';
	settings +='menubar=no,';
	settings +='status=no,';
	settings +='toolbar=no,';
	settings +='directories=no,';
	settings +='resizable=no';
	var win;
	win = window.open("/live/index.php","arastream",settings);
}




function doSearch(obj)
{
	//document.location.href= $.JSvars.HOST_FORGIEN_URL+ document.getElementById(obj).value;
	
	jQuery("form#search_ar_form").submit();
}

// opening a pop up window to show the reuslts
function open_popup_ws_void (link)
{
   var fnc_obj_win;   
   fnc_obj_win=window.open(link,"POLLS"," resizable=yes, scrollbars=yes, menubar=no, toolbar=no");		
}


function getUrlParam(name)
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}



function isPreview()
{
		var param_websection = getUrlParam('web_section_code');
		if(param_websection=='' || param_websection==null)
			return false;
		else
			return true
}


function pup(PupURL,w,h)
{
	Seed = Math.floor(Math.random()*1000000);
	PupLeft=(screen.width/2)-(w/2);
	PupTop=(screen.height/2)-(h/2);
	window.open( '/files/features/' + PupURL + '/main.html' ,'PopUp' + Seed ,'width='+ w +',height='+ h +',left=' + PupLeft + ',top=' + PupTop );
	void(0);
}



function doSort(val) {
$('#search_ar_form').submit();

};


function sendArticleLink(cont_id)
{
	PupLeft=(screen.width/2)-(400/2);
	PupTop=(screen.height/2)-(500/2);
	window.open( '/send_article_link.php?cont_id='+cont_id ,'send article link' ,'status=no,toolbar=no,menubar=no,location=no,width=400,height=500,left=' + PupLeft + ',top=' + PupTop);
}

/*** FUNCTION TO LOAD CSS DYNAMICALLY ***/
function loadjscssfile(filename, filetype){
 if (filetype=="js"){ //if filename is a external JavaScript file
  var fileref=document.createElement('script')
  fileref.setAttribute("type","text/javascript")
  fileref.setAttribute("src", filename)
 }
 else if (filetype=="css"){ //if filename is an external CSS file
  var fileref=document.createElement("link")
  fileref.setAttribute("rel", "stylesheet")
  fileref.setAttribute("type", "text/css")
  fileref.setAttribute("href", filename)
 }
 if (typeof fileref!="undefined")
  document.getElementsByTagName("head")[0].appendChild(fileref)
}
/*** END FUNCTION TO LOAD CSS DYNAMICALLY ***/


/*** BEGIN TAKEOVER SCRIPTS *********/
function openSponsorLink($Url)
{
	window.location.href="$Url";	
}



/** BEGIN HEADER BUTTON CLICKABLE LINK TO HADATH **/
$(function() {
		
		
		//$html_link = "<div class='headTitleButton'><a href='/hadath'>&nbsp;</a></div>";
		
		
		//$('.header-frequencies').prepend($html_link );
		//$('.header-frequencies').css('backgound-image','none');
		
		
		
});
/** END HEADER BUTTON CLICKABLE LINK TO HADATH **/

function loadGeoLoc()
{
	$.getJSON('/geoloc.php?rnd='+Math.random(), function(data) {
  		//alert(data.isWhiteListed);
		if(data.isWhiteListed == false)
		{
			window.location = 'http://www.alarabiya.net';
		}
		else
		{
			//alert('whitelisted');	
		}
	});
}
