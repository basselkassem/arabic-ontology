/// <reference path="jquery-1.4.2.js" />
/*this code run when page be ready */
jQuery(function () {
    //Reorder the news boxes base one last order & build the drag drop
    //to select Daily Readers Choise
    selectReadersChoiseDaily(jQuery(".readers-date-day")[0]);
    // set watermark 
    jQuery("#txtsearch").watermark("ابحث في العربية", "txt-box-watermark")


    //build the vertical scrollables list base on div container id 
    jQuery("#scrollable_Policy").scrollable({ vertical: true });
    jQuery("#scrollable_Sport").scrollable({ vertical: true });
    jQuery("#scrollable_Economics").scrollable({ vertical: true });

    //build the tabs base on div container id 
    jQuery("#tabs_readers_choise").tabs()
    jQuery("#tabs_opinion").tabs()
    jQuery("#tabs_arab_markets").tabs()
    jQuery("#beforeFooterTab").tabs()

    //marquee stop on mouseover
    jQuery("marquee").mouseout(function () { this.start() }).mouseover(function () { this.stop() });

});

//////////////Tabs//////////////////////////////////////
function readersChoiseData() {
    this.mostReadeData = "";
    this.mostCommentData = "";
    this.mostSendingData = "";
}



var _readersChoiseDaily = null;
var _readersChoiseWeekly = null;
var _readersChoiseMonthly = null;



function selectReadersChoiseDaily(anchor) {
    changeSelectedReadersChoise(anchor);
    if (_readersChoiseDaily == null) {
        _readersChoiseDaily = new readersChoiseData();
        _readersChoiseDaily.mostCommentData = jQuery("#dailyMostCommentData").html();
        _readersChoiseDaily.mostReadeData = jQuery("#dailyMostReadeData").html();
        _readersChoiseDaily.mostSendingData = jQuery("#dailyMostSendingData").html();
    }
    fillTabsData(_readersChoiseDaily);
}

function selectReadersChoiseWeekly(anchor) {
    changeSelectedReadersChoise(anchor);
    if (_readersChoiseWeekly == null) {
        _readersChoiseWeekly = new readersChoiseData();
        _readersChoiseWeekly.mostCommentData = jQuery("#weeklyMostCommentData").html();
        _readersChoiseWeekly.mostReadeData = jQuery("#weeklyMostReadeData").html();
        _readersChoiseWeekly.mostSendingData = jQuery("#weeklyMostSendingData").html();
    }
    fillTabsData(_readersChoiseWeekly);
}

function selectReadersChoiseMonthly(anchor) {
    changeSelectedReadersChoise(anchor);
    if (_readersChoiseMonthly == null) {
        _readersChoiseMonthly = new readersChoiseData();
        _readersChoiseMonthly.mostCommentData = jQuery("#monthlyMostCommentData").html();
        _readersChoiseMonthly.mostReadeData = jQuery("#monthlyMostReadeData").html();
        _readersChoiseMonthly.mostSendingData = jQuery("#monthlyMostSendingData").html();
    }
    fillTabsData(_readersChoiseMonthly);
}

function fillTabsData(data) {
    var tabsReaders = jQuery("#tabs_readers_choise")
    tabsReaders.find("#mostReade").html(data.mostReadeData);
    tabsReaders.find("#mostComment").html(data.mostCommentData);
    tabsReaders.find("#mostSending").html(data.mostSendingData);
}

function changeSelectedReadersChoise(anchor) {
    jQuery("#readersDateContainer li").removeClass("selected");
    jQuery(anchor).parent().addClass("selected");
}
//////////////Tabs//////////////////////////////////////


function ShowHideComments() {
    jQuery("#hidecommentsContainer").slideDown(400);
	
}

