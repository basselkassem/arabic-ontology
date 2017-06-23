
var _vlink;
var showcaseObj = '';
var showcaseObjPlayTimeoutId = '';
var listnerModeAdded = false;

function stateCallback(obj,ele){
    _player = document.getElementById(obj['id']);
    state = _player.getConfig().state;
    switch(state){
        case 'IDLE':break;
        case 'BUFFERING':break;
        case 'PLAYING':break;
        case 'PAUSED':break;
        case 'COMPLETED':
            //clearTimeout(showcaseObjPlayTimeoutId);
            showcaseObj.play();
            break;
    }
    return;
}

function showcaseInit(showcaseSelector) {
	showcaseObj = jQuery(showcaseSelector).showcase();
}

function playerReady(obj) {
    _player = document.getElementById(obj['id']);
    _player.sendEvent("PLAY","true");
    showcaseObj._stop();
    //showcaseObjPlayTimeoutId = setTimeout('showcaseObj.play();', 8000);
    _player.addModelListener('STATE','stateCallback');
}


(function($) {
    $.fn.extend({
        showcase : function(options) {
            var self = this;

            var defaults = {
                itemsSelector : '#Navigator',
                itemSelectors:{
                    title:'[id*="maintitle"]',
                    subtitle:'[id*="subtitle"]',
                    image:'[id*="imagObj"]',
                    imageSrc:'[id*="image"]',
                    caption:'[id*="caption"]',
                    description:'[id*="description"]',
                    source:'[id*="source"]',
                    newsLink:'[id*="newsLink"]',
                    videoLink:'[id*="videoLink"]'
                },
                displayItemsSelectors:{
                    descriptionText:'#descriptionText',
                    descriptionLink:'#descriptionLink',
                    source:'#source',
                    subtitle:'#subtitle',
                    title:'#title',
                    image:'#image',
                    caption:'#caption',
                    captionLink :'#captionLink',
                    player:'#player'
                },
				flashvars:{
					volume:'0'
				}
            };

            options = $.extend(defaults, options);
            options.itemsContainer = this.find(options.itemsSelector);
            options.navigatorItems = options.itemsContainer.find('>div');
            options.mainContainer = $(this);
            options.currentPointer = 0;
            options.selectedPointer = null;
            options.timeOutId = 0;
            options.delay = 5000;
            options.itemsLength = options.navigatorItems.size();


            var self = this;
            self.options = options;
            this.extend({
                getNextItem:function(){
                    return $(self.options.navigatorItems.get(self.options.currentPointer));
                },
                getCurrentItem:function(){
                    return $(self.options.navigatorItems.get(self.options.selectedPointer));
                },
                getData:function(item){
                    data = {};
                    item = $(item);
                    itemSelectors = options.itemSelectors;
                    data.title = item.find(itemSelectors.title).text();
                    data.subtitle = item.find(itemSelectors.subtitle).text();
                    data.image = item.find(itemSelectors.image).clone();
                    data.imageSrc = item.find(itemSelectors.imageSrc).text();
                    data.caption = item.find(itemSelectors.caption).text();
                    data.description = item.find(itemSelectors.description).text();
                    data.source = item.find(itemSelectors.source).text();
                    data.newsLink = item.find(itemSelectors.newsLink).text();
                    data.videoLink = item.find(itemSelectors.videoLink).text();
                    data.isHaseVido = (data.videoLink) ? true : false;
                    return data;

                },
                getNextData:function(){
                    item2 = self.getNextItem();
                    return self.getData(item2);
                },
                fecthNext:function(){
                    data = self.getNextData();
                    self.fetchData(data);
                    self.shiftPointer();
                },
                fetchData:function(data){
                    displayItemsSelectors = self.options.displayItemsSelectors;
                    $(displayItemsSelectors.descriptionText).text(data.description);
                    $(displayItemsSelectors.descriptionLink).attr("href", data.newsLink);
                    $(displayItemsSelectors.source).text(data.source);
                    $(displayItemsSelectors.subtitle).text(data.subtitle);
                    $(displayItemsSelectors.title).text(data.title);
                    $(displayItemsSelectors.image).empty();
                    $(displayItemsSelectors.caption).text(data.caption);
					
					$slider_url = $(displayItemsSelectors.descriptionLink).attr("href", data.newsLink).attr('href'); 
					_gaq.push(['_trackEvent', 'Slider_Arabic', 'View', $slider_url, 1, true]);
					
					// hide the caption shade if no data is presentable
					/*
					 * activating this hides the curved side shade
					 * 
					*/
					if ($(displayItemsSelectors.caption).text() == '') {
						$(displayItemsSelectors.caption).hide();
					} else if ($(displayItemsSelectors.caption).is(':hidden')) {
						$(displayItemsSelectors.caption).show();
					}
					// 
                    this.options.mainContainer.find("a").attr("href", data.newsLink);
                    //for iOS on ipad we need to show html5 video instead
                    //detect device
        			var deviceAgent = navigator.userAgent.toLowerCase();
        			var iOS = deviceAgent.match(/(iphone|ipod|ipad)/);


                    if (data.isHaseVido && !iOS ) {
                        //$(displayItemsSelectors.captionLink).attr("href", "#");
                        //$(displayItemsSelectors.captionLink).removeAttr("href");
                        $(displayItemsSelectors.player).show();
                        $(displayItemsSelectors.image).hide();
                        self.createPlayer(data.videoLink, '');
                    }else{
                        $(displayItemsSelectors.image).show();
                        $(displayItemsSelectors.image).html(data.image);
                        $(displayItemsSelectors.player).hide();
                        $(data.image).fadeOut(0).fadeIn(500);
                    }
					
                },
                createPlayer:function(FilePath, imagePath) {
                    self.options.flashObj = $('#player').flash(
                    {
                        swf: '/assets/ar/flash/player-licensed-viral.swf',
                        width: 401,
                        height: 310,
                        expressInstaller: '/assets/ar/flash/expressInstall.swf',
                        allowscriptaccess: 'always',
                        allowfullscreen: 'false',
                        wmode: 'opaque',
                        id:'teaserplayer',
                        name:'teaserplayer',
                        flashvars: {
                            controlbar: 'none',
                            volume:self.options.flashvars.volume,
                            plugins:'none',
                            //'plugins' :  'gapro-1',
                            //'gapro.accountid' : 'UA-463820-9',
                            autostart: true,
                            file: FilePath,
                            image: imagePath
                        }
                    },
                    {
                        expressInstall: true
                    });
                },
                recalipratePointers:function(){
                    self.options.navigatorItems.each(function(index,element){
                        if($(this).hasClass('big-news-item-selected-item')){
                            self.options.currentPointer = index;
                            self.shiftPointer();
                        }
                    })
                },
                shiftPointer:function(){
                    lowerBoundry = -1;
                    upperBoundry = self.options.itemsLength - 1;
                    pointer = self.options.currentPointer;

                    if(pointer > lowerBoundry && pointer < upperBoundry){
                        pointer++;
                    }else{
                        pointer = lowerBoundry + 1;
                    }
                    self.options.currentPointer = pointer;

                    selectedPointer = pointer - 1;
                    if(selectedPointer >= upperBoundry || selectedPointer <= lowerBoundry){
                        selectedPointer = upperBoundry;
                    }
                    self.options.selectedPointer = selectedPointer;
                },
                removeTheme:function(element){
                    if($(element).hasClass('big-news-item-selected-item')){
                        $(element).removeClass('big-news-item-selected-item');
                    }

                    if(!$(element).hasClass('big-news-item')){
                        $(element).addClass('big-news-item');
                        $(element).unbind('click');
                    }
                },
                addTheme:function(element){
                    if($(element).hasClass('big-news-item')){
                        $(element).removeClass('big-news-item');
                    }

                    if(!$(element).hasClass('big-news-item-selected-item')){
                        $(element).addClass('big-news-item-selected-item');
                        $(element).click(function(){
							$slider_url =  $(this).find(options.itemSelectors.newsLink).text();
							$slider_url = $.JSvars.HOST_FORGIEN_URL + $slider_url.substring(1);
							_gaq.push(['_trackEvent', 'Slider_Arabic', 'Click', $slider_url, 1, true]);
												  
                            window.location = $(this).find(options.itemSelectors.newsLink).text();
                            window.location.refresh();
                        });
                    }
                },
                prepearImages:function() {
                    self.options.navigatorItems.each(function(_ind,element){
                       /*
                    	srcSmallImage = $(this).find("[id*='smallImage']").text();
                        smallImage = $("<img  class='big-news-item_img' />");
                        smallImage.attr("src", srcSmallImage).bind("error load", self.loading);
                        $(this).find("[id*='tdSmallImage']").html(smallImage);
						*/
                        srcImage = $(this).find("[id*='image']").text();
                        _image = $("<img id='item" + _ind + "_imagObj' class='news-rotator-main-image_img' />");
                        _image.attr("src", srcImage).bind("error load", self.loading);
                        $(this).find("[id*='info']").append(_image);
                    });
                },
                prepearBinds:function() {
                    
                },
                loading:function(){
                    //$("#loadingDiv").css("display", "none");
                    //$("#NewsNavigator").css("display", "block");
                },
                setupSelectedTheme:function(){
                    currentItem = self.getCurrentItem();
                    self.options.navigatorItems.each(function(){
                        self.removeTheme(this);
                    });
                    self.addTheme(currentItem);
                },
                play:function(){
                    $('#player').flash().remove();
                    self.fecthNext();
                    self.setupSelectedTheme();
                    self._stop();
                    self.options.timeOutId = setTimeout(self.play, self.options.delay);
                },
                _stop:function(){
                    clearTimeout(self.options.timeOutId);
                },
                resume:function(){
                    self.options.timeOutId = setTimeout(self.play, self.options.delay);
                },
                _over:function(element){
                    self._stop();
                    self.removeTheme(self.getCurrentItem());
                    self.addTheme(self.options.hoverThis);
                    self.fetchData(self.getData(self.options.hoverThis));
                    self.recalipratePointers();
                },
                _out:function(element){
                    self.resume();
                },
                setupHover:function(){
                    self.options.navigatorItems.each(function(){
                        $(this).hover(function(){
                            if(!$(this).hasClass('big-news-item-selected-item')){
                                clearTimeout(self.options.outTimeoutId);
                                self.options.hoverThis = this;
                                self.options.overTimeoutId = setTimeout(self._over,150);
                            }else{
                                self._stop();
                            }
                        },function(){
                            if($(this).hasClass('big-news-item-selected-item')){
                                clearTimeout(self.options.overTimeoutId);
                                self.options.hoverThis = null;
                                self.options.outTimeoutId = setTimeout(self._out,150);
                            }else{
                                self._resume();
                            }
                        })
                    })
                }
            });

            this.each(function(){
                self.prepearImages();
                self.setupHover();
            });

            self.createPlayer('', '');
            self.play();

            return this;
        }
    });
})(jQuery);