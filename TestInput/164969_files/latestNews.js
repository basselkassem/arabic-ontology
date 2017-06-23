/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery(document).ready(function() {
 loadLatestNews();
});

function decorate_latestnews_ar(item)
{
      var news_block = jQuery('<li></li>');
      jQuery('<a></a>').html(item.Title).attr('href',item.StaticLink).appendTo(news_block);

      return news_block;
}

function loadLatestNews()
{
	jQuery.ajax({
        dataType: 'json',
        //url: "/ajax/contents/latestNews.php?content_id="+latestNewsObj.contentId + "&site_id=" + latestNewsObj.siteId + "&lang_id=" + latestNewsObj.siteLang,
		url: "/latest_news_" + latestNewsObj.siteId + "_" + PageOptions.lang + ".html",
        beforeSend: function(){$("div#ajax-small-loader").show()},
        success: function(data){

		   	jQuery.each(data, function(index,item){
                            decorate_latestnews_ar(item).appendTo("div#latestNewsDiv ul");
			});

                        //show the element
                        
                        //hide the loader
                        jQuery("div#ajax-small-loader").hide();
                        
                        jQuery("div#latestNewsDiv").slideDown(400);
//
//
//			commentLoader.comments.hide();
//
//			if(commentOptions.commentPages > page )
//			{
//				pagerAttrib = "javascript:loadComments("+ (page+1) +");";
//				jQuery('#commentPagerControl').show();
//			}
//			else
//			{
//				pagerAttrib = "javascript:void(0);";
//				jQuery('#commentPagerControl').hide();
//
//			}
//
//
//			if(commentOptions.commentPages == page)
//				jQuery('#commentPagerControl').hide();
//			else
//				jQuery('#commentPagerControl').show();
//
//			var nextpage_container = jQuery('<div>').html('').attr('id','commentsPage_' + (page+1)).attr('style', 'display:none');
//			  nextpage_container.appendTo("#commentsContainer");
//
//
//			jQuery('#commentsPage_'+ page).slideDown(400);
//
//			commentLoader.comments.hide();
//			jQuery('#commentPagerControl').attr('href', pagerAttrib);
        }
    });
}