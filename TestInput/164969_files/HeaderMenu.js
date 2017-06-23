/// <reference path="jquery-1.4.2.js" />
/// <reference path="jquery.hoverIntent.minified.js" />

var _item;
var _menuItem;
var _toParent = false;
var _isOutContainer = true;
var _intervalHideMenuItem = null;
var _intervalHideMenuContainer = null;
var _openContainer = null;
var _timeout = 300;
//this fire when page ready
jQuery(function () {
    var itemConfig = {
        over: showMenu,
        timeout: _timeout,
        out: mouseLeaveItem
    };
    jQuery("#HeaderMenu>ul>li>a").hoverIntent(itemConfig);

    var containerConfig = {
        over: function () { },
        timeout: _timeout,
        out: hideMenuwhenMouseLeaveContainer
    };

    jQuery(".menu-container").hoverIntent(containerConfig)
    jQuery(".menu-container").hover(mouseOverContainer, mouseLeaveContainer);
    jQuery(window).resize(setPostion);
})

//this function to show menu
function showMenu() {
    //get the _item
    _item =  jQuery(this);
    _menuItem = _item.parent().find(".menu-container");
    //check if _item has "menuContainerID" attribute the value of this attr a menu div
    if (_menuItem.length != 0) {
        if (_menuItem.attr("isActive") == true) {
            _toParent = true;
        } else {
			hideMenu();
			_toParent = false;
			_item.parent().addClass("selected")
			setPostion(_item, _menuItem);
			_openContainer = _menuItem[0].id;
			_menuItem.attr("isActive", true);
			_item.attr("menuContainerID", _openContainer);
			_menuItem.slideDown(300);
		}
    } else {
        _toParent = false;
        _item = null;
        _menuItem = null;
        hideMenu();
    }
}
//this function to hide menu
function hideMenu() {
    _isOutContainer = true;
    if (_openContainer) {
        _openContainer = null;
        jQuery(".menu-container").stop(true).hide().attr("isActive", false);
    }
    jQuery("#HeaderMenu>ul>li").removeClass("selected")
}
//this function to set Postion of menu menu
function setPostion() {
    if (!_item || !_menuItem)
        return;
    var pos = _item.position()
    pos.left -= _menuItem.width() - _item.width();
    pos.top += _item.height() + 3
    _menuItem.css({ left: pos.left, top: pos.top,height:"auto" })

}

function mouseLeaveItem() {
    _toParent = false;
    var menuContainerID = jQuery(this).attr("menuContainerID");
    if (!menuContainerID)
        return;
    if (_isOutContainer && (menuContainerID === _openContainer)) {
        hideMenu();
    }
}

function mouseLeaveContainer(ev) {
    _isOutContainer = true;
}

function mouseOverContainer(ev) {
    _isOutContainer = false;
}

function hideMenuwhenMouseLeaveContainer() {
    if (!_toParent) {
        hideMenu();
    }
}