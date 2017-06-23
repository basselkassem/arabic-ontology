/// <reference path="jquery-1.4.2.js" />
var __pageUrl;
var _title;
var facebookUrl = 'http://www.facebook.com/sharer.php?u={u}&amp;t={t}';
var twitterUrl = 'http://twitter.com/home?status={t}%20{u}';
var giggUrl = 'http://digg.com/submit?phase=2&amp;url={u}&amp;title={t}';
var RssUrl = 'http://www.alarabiya.net/rss/rss_top08.xml';

//when page read select the divs has this class ".left-sdie-applictaion-link" this select all link in it 
//and add  href value
jQuery(function () {
    _pageUrl = window.location.href;
    _title = document.title;
    jQuery(".left-sdie-applictaion-link a").each(function () {
        
        switch (jQuery(this).attr("class")) {
            case "bookmarkrss":
                jQuery(this).attr("href", RssUrl);
                break;
            case "bookmarkfacebook":
                jQuery(this).attr("href", facebookUrl.replace(/\{u\}/, _pageUrl).replace(/\{t\}/, _title))
                break;
            case "bookmarktwitter":
                jQuery(this).attr("href", twitterUrl.replace(/\{u\}/, _pageUrl).replace(/\{t\}/, _title))
                break;
            case "bookmarkdig":
                jQuery(this).attr("href", giggUrl.replace(/\{u\}/, _pageUrl).replace(/\{t\}/, _title))
                break;
        }
    })
});

