function stripslashes(str) {
	str=str.replace(/\\'/g,'\'');
	str=str.replace(/\\"/g,'"');
	str=str.replace(/\\0/g,'\0');
	str=str.replace(/\\\\/g,'\\');
	
	return str;
}


/*** BEGIN LIST COMMENTS */
document.write('<link href="/assets/ar/css/comments-control.css" rel="stylesheet" type="text/css" />');

var commentOptions = {baseUrl:"", commentPages:0, commentHTML:""};
var commentLoader = {form: {show:null, hide:null}, comments: {show:null, hide:null, showContainer:null}};
var commentObj = {setTitleClass:"", titleClassName:"", limitText:null, maxCommentString:null};

var listCommentsReq;
var _lastRequestedPages = 0;


var totalCommentsPages = 0;
var totalComments = 0;
var renderedBlocks = 0;
//var commentsBlocks = new Array();
var commentsBlocksItems = new Array();
var commentsControlHtml = '<div id="comment-controls-container">\
							   <div id="comments-count"></div>\
							   <div id="comment-control">\
								   <select name="comments control" id="comment-control-select">\
									   <option value="asc">ترتيب التعليقات تصاعديا</option>\
									   <option value="desc">ترتيب التعليقات تنازليا</option>\
								   </select>\
						       </div>\
						   </div>';







function listAllComments(){
		
	var comments_info_path = getCommentsUrlPath() + PageOptions.articleId + '_comments_info.html' ;
	
	//alert(comments_info_path);
    jQuery('#commentPagerControl').hide();
    jQuery('#commentMorePagerControl').hide();
    commentLoader.comments.show();
    jQuery.ajax({
        dataType: 'json',
        url: comments_info_path,
        success: function(data){
            totalComments = parseInt(data.rows);
            totalCommentsPages = parseInt(data.pages);
            if(totalCommentsPages > 0){
				if (window.parent && window.parent.updateIFrameHeight) {
					window.parent.updateIFrameHeight();
				}
                for(i=1;i<=totalCommentsPages;i++){
					eval('var commentsBlocks_'+i+' = jQuery("<div></div>");');
                    buildPageBlock(i);
                }
				commentLoader.comments.showContainer();
				commentLoader.comments.show();
				if (window.parent && window.parent.updateIFrameHeight) {
					window.parent.updateIFrameHeight();
				}
				jQuery('#Div1').find('#commentsContainer').append(commentsControlHtml).find('#comments-count').text(totalComments);
				jQuery('#Div1').find('#comment-control-select').attr('disabled', 'disabled');
				


				
				
				
				
            }
			
			
			
        }


		
    });
}


function resizeFancyBox()
{
		parent_iframe = jQuery('#fancybox-wrap', window.parent.document);
		if(parent_iframe.length)
		{	
			//alert('inIframe');
			
			

			
			content_height = parseInt(jQuery(document).height()) + 250;
			//content_height = ;
			//alert(content_height);
			parent_iframe.height(content_height);
			
		
		}		
}

function getCommentsUrlPath()
{
	var commentsPathURL;
	var loc_path = location.pathname;
	loc_path = loc_path.replace(PageOptions.contentType.toLowerCase(), 'comments');
	loc_path = loc_path.replace(PageOptions.articleId + ".html", '');	
	commentsPathURL = location.protocol + "//" + location.host + loc_path;

	return commentsPathURL;	
}

	
function buildPageBlock(page){
	var comments_page_path = getCommentsUrlPath() + PageOptions.articleId + '_comments_' + page + '.html' ;

    jQuery.ajax({
        dataType: 'json',
        url: comments_page_path,
        success: function(data){
            renderedBlocks++;
            eval('commentsBlocks_'+page+' = jQuery("<div></div>");');
            jQuery.each(data, function(index,myitem){
                eval('decorate_comment_ar(myitem).appendTo(commentsBlocks_'+page+');');
            });
            if(renderedBlocks == totalCommentsPages){
				commentLoader.comments.hide();
				appendAllBlocks();
				enableCommentsControl();
				resizeFancyBox();
            }
        }
    })
}

function appendAllBlocks(){
	_allCommentsRowsAsc = jQuery('<div id="all-comments-rows-asc" ></div>');
	_allCommentsRowsDesc = jQuery('<div id="all-comments-rows-desc" ></div>');
    for(i=1;i<=totalCommentsPages;i++){
		eval('commentsBlock = commentsBlocks_'+i+';');
		commentsBlock.children().each(function(){
			_thisAsc = jQuery(this).clone();
			_thisDesc = jQuery(this).clone();
			_thisAsc.attr('id',(_thisAsc.attr('id')+'_asc')).appendTo(_allCommentsRowsAsc);
			_thisDesc.attr('id',(_thisDesc.attr('id')+'_desc')).prependTo(_allCommentsRowsDesc);
		});
		eval('commentsBlocks_'+i+' = null;');
    }
	_allCommentsRowsAsc.appendTo("#commentsContainer");
	_allCommentsRowsAsc.hide();
	
	_allCommentsRowsDesc.appendTo("#commentsContainer");
	_allCommentsRowsDesc.hide();
	
	commentsBlock = null;
}

/**
 * commentsOrder <string> value of "asc" or "desc"
 */
function enableCommentsControl(commentsOrder){
	order = new Array('asc','desc');
	if (commentsOrder == undefined || jQuery.inArray(commentsOrder.toLowerCase(), order) == -1) {
		commentsOrder = order[1];
	}
	if ($.cookie('commnetsOrder') != undefined)
		commentsOrder = $.cookie('commnetsOrder');
	jQuery('#Div1').find('#comment-control-select').val(commentsOrder).removeAttr('disabled').change(function(){
		switch(jQuery(this).val()){
			case order[0]:
				jQuery('#all-comments-rows-asc').show();
				jQuery('#all-comments-rows-desc').hide();
				$.cookie('commnetsOrder', order[0], {expires: 30, path: '/'});
				break;
			case order[1]:
				jQuery('#all-comments-rows-desc').show();
				jQuery('#all-comments-rows-asc').hide();
				$.cookie('commnetsOrder', order[1], {expires: 30, path: '/'});
				break;
		}
	}).change();
}


jQuery(document).ready(function(){
    listAllComments();
})


function loadPage(page,loadNext){
    try{
        listCommentsReq.abort();
    }catch(e){};
    
    listCommentsReq = jQuery.ajax({
        dataType: 'json',
        url: "/ajax/comments/get_comments_page.php?content_id="+PageOptions.articleId+"&page="+page,
        beforeSend: function(){commentLoader.comments.show()},
        success: function(data){
            if(data=="" || data==null)
            {
                commentLoader.comments.hide();
                return;
            }
            
            jQuery.each(data, function(index,myitem){
                 commentOptions.commentPages = parseInt(myitem.totalPages);                                    
                 decorate_comment_ar(myitem).appendTo("#commentsPage_" + page);  
            });
            

            commentLoader.comments.showContainer();
            
            if(commentOptions.commentPages > page )
            {
                pagerAttrib = "javascript:loadComments("+ (page+1) +");";
                jQuery('#commentPagerControl').show();
                jQuery('#commentMorePagerControl').show();
            }
            else
            {
                pagerAttrib = "javascript:void(0);";
                jQuery('#commentPagerControl').hide();
                jQuery('#commentMorePagerControl').hide();
            
            }
            
                    
            if(commentOptions.commentPages == page){ 
                jQuery('#commentPagerControl').hide();
            }else{
                jQuery('#commentPagerControl').show();
            }
            var nextpage_container = jQuery('<div>').html('').attr('id','commentsPage_' + (page+1)).attr('style', 'display:none');
              nextpage_container.appendTo("#commentsContainer");
            
            
            jQuery('#commentsPage_'+ page).slideDown(400);
            
            commentLoader.comments.hide();
            jQuery('#commentPagerControl').attr('href', "javascript:loadComments(0);");
            jQuery('#commentMorePagerControl').attr('href', pagerAttrib);
            _lastRequestedPages = page;
            if(loadNext && commentOptions.commentPages > page){
                loadPage((page+1),true);
            }
        }
    });
}

function loadComments(page)
{    
return;
    page = parseInt(page);
    switch(page){
        case 0:
            loadPage((_lastRequestedPages+1),true);
            break;
        default:
            loadPage(page,false);
            break;
    }
}


function decorate_comment_ar(item)
{
	
	if(parseInt(item.user) > 0)
		str_member_stat = 'عضو مسجل';
	else
		str_member_stat = 'زائر';
	
      var comment_block = jQuery('<div>').html('').attr('id','comment_' + item.itemNumber).attr('class', 'comment-box');
      jQuery('<span>').html( item.itemNumber + " - " +  stripslashes(item.title) ).attr('class','comment-title').appendTo(comment_block);
      jQuery('<span>').html(  stripslashes(item.name) + '<span class="commenter-status"> ('+ str_member_stat +') </span>').attr('class','commenter-name').appendTo(comment_block);
      jQuery('<span>').html(item.commentDate + ' م، ' + item.commentDate2).attr('class','comment-time').appendTo(comment_block);
      jQuery('<span>').html( stripslashes(item.comment) ).attr('class','comment-text').appendTo(comment_block);

      return comment_block;
}

/*** END LIST COMMENTS ***/



/*** BEGIN COMMENTS FORM ***/
var overrideScroller = true;
var timestamp =  Number(new Date());
var commentsFormVisible = false;
var add_comment = function() {
	$("#commentsFormContainer").html('<div style="text-align:center;margin-top:60px;"><img src="/assets/ar/images/spinner_16x16.gif" /></div>');
//	$(window).scroll(function(){
		if (commentsFormVisible == false) {
			formOffset = $("#commentsFormContainer").offset();
			formMargin = 200;
			if (($(window).scrollTop()+$(window).height() >= (formOffset.top - formMargin)) || overrideScroller) {
				commentsFormVisible = true;
				url_addComment = '/ajax_app/comments/manage/add-comment/';
				data='contentId=' + PageOptions.articleId;
				jQuery.post(url_addComment, data, function(result) {
					$("#commentsFormContainer").html(result);
					if (window.parent && window.parent.updateIFrameHeight) {
						window.parent.updateIFrameHeight();
					}
					resizeFancyBox();
				});
			}
		}
//	});
}


function validateCommentsForm()
{
    if(jQuery("#cname").val().length == 0)
    {
        alert('يرجى تحديد الاسم');
       jQuery("#cname").focus();
    }
    else if(jQuery("#ctitle").val().length == 0)
    {
        alert('يرجى تحديد عنوان التعليق');
        jQuery("#ctitle").focus();
    }
    else if(jQuery("#cbody").val().length == 0)
    {
        alert('يرجى تحديد نص التعليق');
        jQuery("#cbody").focus();
    }
    else
    {
        ajax_insert_comment();
    }
}

function doInsertComment(){
    _serializedData = jQuery('div.form-container :input').serialize();
    commentLoader.form.show();
    url_addComment = '/ajax_app/comments/manage/insert-comment/' + Number(new Date());
    jQuery.post(url_addComment, _serializedData, function(result)
    {
        commentLoader.form.hide();
        jQuery("#commentsFormContainer").html(result);
		if (window.parent && window.parent.updateIFrameHeight) {
			window.parent.updateIFrameHeight();
		}
            
    });
}

var ajax_insert_comment = function()
{
   	var cname = jQuery("#cname").val();
    var cid = jQuery("#cid").val();
    var ctitle = jQuery("#ctitle").val();
    var cbody = jQuery("#cbody").val();
    var nid = jQuery("#content_id").val();
    //var data = "cname=" + cname + "&cid=" + cid + "&ctitle=" + encodeURI(ctitle) + "&cbody=" + encodeURI(cbody) + "&nid=" + encodeURI(nid) + "&lang=1";
	var data = "cname=" + cname + "&cid=" + cid + "&ctitle=" + encodeURI(ctitle) + "&cbody=" + encodeURI(cbody) + "&nid=" + encodeURI(nid);

	commentLoader.form.show();

    url_addComment = '/ajax_app/comments/manage/insert-comment/' + timestamp;
	jQuery.post(url_addComment, data, function(result) {
		commentLoader.form.hide();
		jQuery("#commentsFormContainer").html(result); 
		if (window.parent && window.parent.updateIFrameHeight) {
			window.parent.updateIFrameHeight();
		}
	});	
}


var commentsHandler = function(formID)
{
    var data, action;
    var fid = formID;
    if(typeof(formID) == 'undefined')
    {
       data='contentId=' + PageOptions.articleId;	
		
        action = '/ajax_app/comments/manage/add-comment/'
    }
    else
    {
        data = jQuery('#'+formID).serialize();
		action = jQuery('#'+formID).attr("action");

		if(data=='' || data==null)
		{
			str_contentId = jQuery('#content_id').val();
			
			if(str_contentId===undefined || str_contentId=='')
				str_contentId = jQuery('#contentId').val();
				
			data = "contentId=" + str_contentId;			
		}
    }
	
	commentLoader.form.show();
  
   
	jQuery.post(action, data, function(result) {
		commentLoader.form.hide();
		jQuery("#commentsFormContainer").html(result);
		if (window.parent && window.parent.updateIFrameHeight) {
			window.parent.updateIFrameHeight();
		}
	});
}



var availabilityHandler = function(element)
{
    var name = jQuery('#'+element).attr("name");
	var value = jQuery('#'+element).val();
	
	var data = name + '=' +  value;
	

	jQuery('#NickName').attr('disabled','disabled');
	
	commentLoader.form.show();
	action = '/ajax_app/comments/user/check-' + name + '/',

	commentLoader.form.show();

	jQuery.post(action, data, function(result) 
	{					
			
			commentLoader.form.hide();
			eval(result)
			
            //jQuery(element).enable();
	});	
}



commentLoader.comments.show=function()
{
	jQuery('#commentsLoaderStat').show(); 
}

commentLoader.comments.hide= function()
{
	jQuery('#commentsLoaderStat').hide(); 			
}

commentLoader.comments.showContainer=function()
{
	jQuery('#Div1').show();	
}


commentLoader.form.show = function(isList)
{
			h = jQuery('#commentsFormContainer').height();
			jQuery('#commentsLoader').height(h+10);
			jQuery('#commentsLoader').show();	
}

commentLoader.form.hide = function()
{
	jQuery('#commentsLoader').hide();
}


commentObj.setTitleClass = function(str)
{
	commentObj.titleClassName = str;		
}


commentObj.limitText = function(ele){

	var limit = parseInt(commentObj.maxCommentString);
	
	len = jQuery(ele).val().length;
		
	if(len >= limit){
		jQuery(ele).val(jQuery(ele).val().substring(0,limit));
		remains = 0;

	}else{
		remains = limit - len;
	}
	jQuery('#characterRemains').html(remains);
	
}




/*** END COMMENTS FORM ***/





