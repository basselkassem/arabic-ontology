// JavaScript Document
//global variables

var player = {
    width:'640',
    height:(360+32).toString(),
    version:'9',
    movie:'assets/ar/flvplayer/AA_FLVPlayer_1.swf?plugins=gapro-1&gapro.accountid=UA-463820-9',
    container:'',
    containerId:'container',
    file:'',
    image:'',
    id:'',
    prerollAdTag :'',
    stretching: 'uniform'
}

function loadVideo(vContainer, path, prevImage, options, width, height){

    player.container = vContainer;
    player.file = path;
    player.image = prevImage;
    player.id = 'AAFLVPlayer';
	player.width = (width || false) || player.width;
	player.height = (height || false) || player.height;
		
    options = options || false;
	
    if(options){
        player.prerollAdTag = options.prerollAdTag || player.prerollAdTag;
    }

    jQuery('#'+player.container).css({
        width : player.width,
        height : player.height
    });
	
}

function buildPlayer(){
    
    
	var deviceAgent = navigator.userAgent.toLowerCase();

	var iOS = deviceAgent.match(/(iphone|ipod|ipad)/);

	var mp4file = player.file.substring(0,player.file.length-3)+'mp4';

	if (iOS) {
		//alert('LOAD HTML 5');
		var mp4file = player.file.substring(0,player.file.length-3)+'mp4';
		html5_video = '<video id="html5video" dir="ltr" controls poster="'+player.image+'"  width="'+player.width+'" height="'+player.height+'" src="' + mp4file +'" autoplay="autoplay" />';
		jQuery('#'+player.container).html(html5_video);
		jQuery('#'+player.container).show();

	}
	else
	{


		so = new SWFObject($.JSvars['HOST_FORGIEN_URL'] + player.movie ,player.id, player.width, player.height, player.version);
		
		//alert(site_name + player.movie);
		so.addParam('allowfullscreen','true');
		so.addParam('allowscriptaccess','always');
		so.addParam('wmode','transparent');
		so.addVariable('file', player.file);
		//alert(player.file);
		
		so.addVariable('prerollAdTag', player.prerollAdTag);
		so.addVariable('image', player.image);
		so.addVariable('autostart', 'true');
		so.addVariable("plugins", "gapro-1");
		so.addVariable("gapro.accountid","UA-463820-9");
		so.write(player.container);		


	}
	
	

    
   

    //jQuery('#'+player.container).parent().append(jQuery('<div style="width:0px;height:0px;overflow:hidden;"><iframe width="0" height="0" src="/vodTracker.php?file='+player.file+'" /></div>'));
}

function destroyPlayer(){
    jQuery('#'+player.id).remove();
    jQuery('#'+player.container).html('');
    
}

function stopVideoPlayer(behavior){
    behavior = behavior || '';
    switch(behavior){
        case 'stopEvent':
            //some work needed
            break;
        default:
            destroyPlayer();
            break;
    }
}

function playVideoPlayer(behavior){
    behavior = behavior || '';
    switch(behavior){
        case 'playEvent':
            //some work needed
            break;
        default:
            buildPlayer();
            break;
    }
}

function setPlayButton(id){
    imgObj = jQuery('#'+id);
    imgObj.attr('onclick', 'javascript:open_aawindow();');
    imgObj.css({'cursor' : 'pointer'});
    imgPlayBtn = jQuery("div");
    imgPlayBtn.html('<img src="'+ site_name +'/files/gfx/img/play_button.gif" style="position:absolute; top:45%; left:45%;" />');
    imgObj.append(imgPlayBtn);
}

function loadAfiaVid(container, url, image, title_id_container){
    open_window_effects();
    loadVideo(container, url, image);
    playVideoPlayer();
    jQuery('#AALightWindow_title').html(jQuery('#'+title_id_container).attr('alt'));
}

function startVideo() {

	
	$('#theimage').hide();
	$('#playerContainer').show();
	$('#ArticleCaption').hide();
	$('#ImgArticleToolbar').hide();
	buildPlayer();
}
