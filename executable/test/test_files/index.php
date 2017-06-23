/* generated javascript */
var skin = 'monobook';
var stylepath = '/skins-1.5';

/* MediaWiki:Common.js */
/* 

[[en:-]]
[[de:-]]
[[fr:-]]
[[pl:-]]
[[ja:-]]
[[nl:-]]
[[it:-]]
[[pt:-]]
[[sv:-]]
[[es:-]]
[[ru:-]]
[[zh:-]]
[[fi:-]]
[[no:-]]
[[eo:-]]
[[sk:-]]
[[cs:-]]
[[da:-]]
[[he:-]]
[[ca:-]]
[[ro:-]]
[[id:-]]
[[hu:-]]
[[uk:-]]
[[tr:-]]
[[sr:-]]
[[sl:-]]
[[lt:-]]
[[bg:-]]
[[ko:-]]
[[et:-]]
[[hr:-]]
[[te:-]]
[[gl:-]]
[[nn:-]]
[[th:-]]
[[el:-]]
[[fa:-]]
[[ms:-]]
[[eu:-]]
[[io:-]]
[[ceb:-]]
[[ka:-]]
[[simple:-]]
[[vi:-]]
[[bn:-]]
[[is:-]]
[[nap:-]]
[[bs:-]]
[[lb:-]]
[[bpy:-]]
[[sq:-]]
[[br:-]]
[[la:-]]
[[mk:-]]
[[su:-]]
[[wa:-]]
[[sh:-]]
[[scn:-]]
[[ku:-]]
[[lv:-]]
[[ast:-]]
[[mr:-]]
[[ht:-]]
[[oc:-]]
[[cy:-]]
[[af:-]]
[[be:-]]
[[ta:-]]
[[tl:-]]
[[ksh:-]]
[[uz:-]]
[[co:-]]
[[an:-]]
[[cv:-]]
[[hi:-]]
[[ga:-]]
[[kn:-]]
[[gd:-]]
[[jv:-]]
[[az:-]]
[[lmo:-]]
[[fy:-]]
[[tg:-]]
[[ur:-]]
[[tt:-]]
[[nds:-]]
[[vec:-]]
[[ia:-]]
[[sw:-]]
[[yi:-]]
[[am:-]]
[[als:-]]
[[li:-]]
[[hy:-]]
[[pms:-]]
[[zh-min-nan:-]]
[[nrm:-]]
[[ilo:-]]
[[fo:-]]
[[ml:-]]
[[war:-]]
[[frp:-]]
[[pam:-]]
[[zh-yue:-]]
[[new:-]]
[[sco:-]]
[[nds-nl:-]]
[[os:-]]
[[fur:-]]
[[lij:-]]
[[qu:-]]
[[kw:-]]
[[nov:-]]
[[vo:-]]
[[pdc:-]]
[[se:-]]
[[sa:-]]
[[mt:-]]
[[ug:-]]
[[map-bms:-]]
[[vls:-]]
[[lad:-]]
[[nah:-]]
[[fiu-vro:-]]
[[csb:-]]
[[diq:-]]
[[ps:-]]
[[bat-smg:-]]
[[ang:-]]
[[zh-classical:-]]
[[tk:-]]
[[yo:-]]
[[ty:-]]
[[to:-]]
[[jbo:-]]
[[mi:-]]
[[mn:-]]
[[mo:-]]
[[ks:-]]
[[bar:-]]
[[hsb:-]]
[[ln:-]]
[[arc:-]]
[[ne:-]]
[[tpi:-]]
[[ky:-]]
[[ie:-]]
[[rm:-]]
[[gu:-]]
[[roa-rup:-]]
[[dv:-]]
[[wuu:-]]
[[tokipona:-]]
[[kk:-]]
[[na:-]]
[[udm:-]]
[[wo:-]]
[[mg:-]]
[[bo:-]]
[[rmy:-]]
[[sc:-]]
[[tet:-]]
[[eml:-]]
[[chr:-]]
[[pag:-]]
[[gv:-]]
[[ba:-]]
[[av:-]]
[[kg:-]]
[[si:-]]
[[sd:-]]
[[bm:-]]
[[bh:-]]
[[cbk-zam:-]]
[[pi:-]]
[[cr:-]]
[[got:-]]
[[so:-]]
[[km:-]]
[[zu:-]]
[[sm:-]]
[[cu:-]]
[[iu:-]]
[[glk:-]]
[[my:-]]
[[cdo:-]]
[[nv:-]]
[[pa:-]]
[[ab:-]]
[[kl:-]]
[[pap:-]]
[[zea:-]]
[[haw:-]]
[[lo:-]]
[[gn:-]]
[[st:-]]
[[ay:-]]
[[rw:-]]
[[as:-]]
[[xh:-]]
[[fj:-]]
[[bi:-]]
[[roa-tara:-]]
[[kv:-]]
[[sn:-]]
[[ak:-]]
[[xal:-]]
[[ce:-]]
[[ha:-]]
[[za:-]]
[[tn:-]]
[[tum:-]]
[[tw:-]]
[[ig:-]]
[[ki:-]]
[[ee:-]]
[[ff:-]]
[[ik:-]]
[[bug:-]]
[[mzn:-]]
[[aa:-]]
[[dz:-]]
[[om:-]]
[[rn:-]]
[[ii:-]]
[[sg:-]]
[[lg:-]]
[[kr:-]]
[[ny:-]]
[[kj:-]]
[[pih:-]]
[[bxr:-]]
[[ss:-]]
[[chy:-]]
[[ho:-]]
[[or:-]]
[[ts:-]]
[[ch:-]]
[[ng:-]]
[[mh:-]]
[[cho:-]]
[[mus:-]]
[[ti:-]]
[[ve:-]]
[[hz:-]]
[[lbe:-]]


<pre dir="ltr">
*/

if (wgAction == 'edit' || wgAction == 'submit' || wgPageName == 'خاص:رفع') {
	importScript('ميدياويكي:Common.js/edit.js');
} else if (wgPageName == 'خاص:بحث') {
	importScript('ميدياويكي:Common.js/search.js');
}

// ============================================================
// BEGIN Dynamic Navigation Bars

// set up the words in your language
var NavigationBarHide = 'أخفِ';
var NavigationBarShow = 'أظهر';

// set up max count of Navigation Bars on page,
// if there are more, all will be hidden
// NavigationBarShowDefault = 0; // all bars will be hidden
// NavigationBarShowDefault = 1; // on pages with more than 1 bar all bars will be hidden
var NavigationBarShowDefault = 0;

// shows and hides content and picture (if available) of navigation bars
// Parameters:
//     indexNavigationBar: the index of navigation bar to be toggled
function toggleNavigationBar(indexNavigationBar) {
	var NavToggle = document.getElementById("NavToggle" + indexNavigationBar);
	var NavFrame = document.getElementById("NavFrame" + indexNavigationBar);

	if (!NavFrame || !NavToggle) {
		return false;
	}

	// if shown now
	if (NavToggle.firstChild.data == NavigationBarHide) {
		for (var NavChild = NavFrame.firstChild ; NavChild != null ; NavChild = NavChild.nextSibling) {
			if (NavChild.className == 'NavPic') {
				NavChild.style.display = 'none';
			}
			if (NavChild.className == 'NavContent') {
				NavChild.style.display = 'none';
			}
		}
	NavToggle.firstChild.data = NavigationBarShow;
	// if hidden now
	} else if (NavToggle.firstChild.data == NavigationBarShow) {
		for (var NavChild = NavFrame.firstChild ;	NavChild != null ; NavChild = NavChild.nextSibling) {
			if (NavChild.className == 'NavPic') {
				NavChild.style.display = 'block';
			}
			if (NavChild.className == 'NavContent') {
				NavChild.style.display = 'block';
			}
		}
		NavToggle.firstChild.data = NavigationBarHide;
	}
}

// adds show/hide-button to navigation bars
function createNavigationBarToggleButton() {
	var indexNavigationBar = 0;
	// iterate over all < div >-elements
	for(var i = 0 ; NavFrame = document.getElementsByTagName("div")[i]; i++) {
		// if found a navigation bar
		if (NavFrame.className == "NavFrame") {
			indexNavigationBar++;
			var NavToggle = document.createElement("a");
			NavToggle.className = 'NavToggle';
			NavToggle.setAttribute('id', 'NavToggle' + indexNavigationBar);
			NavToggle.setAttribute('href', 'javascript:toggleNavigationBar(' + indexNavigationBar + ');');

			var NavToggleText = document.createTextNode(NavigationBarHide);
			NavToggle.appendChild(NavToggleText);
			// Find the NavHead and attach the toggle link (Must be this complicated because Moz's firstChild handling is borked)
			for(var j = 0 ; j < NavFrame.childNodes.length ; j++) {
				if (NavFrame.childNodes[j].className == "NavHead") {
					NavFrame.childNodes[j].appendChild(NavToggle);
				}
			}
			NavFrame.setAttribute('id', 'NavFrame' + indexNavigationBar);
		}
	}
	// if more Navigation Bars found than Default: hide all
	if (NavigationBarShowDefault < indexNavigationBar) {
		for(var i=1 ; i<=indexNavigationBar ; i++) {
			toggleNavigationBar(i);
		}
	}
}

addOnloadHook(createNavigationBarToggleButton);

// END Dynamic Navigation Bars
// ============================================================

// == Collapsible tables ======================================
var autoCollapse = 2;
var collapseCaption = "أخفِ";
var expandCaption = "أظهر";
 
function hasClass(element, className) {
	var Classes = element.className.split(" ");
	for (var i = 0 ; i < Classes.length ; i++) {
		if (Classes[i] == className) {
			return (true);
		}
	}
	return (false);
}

function collapseTable(tableIndex) {
	var Button = document.getElementById("collapseButton" + tableIndex);
	var Table = document.getElementById("collapsibleTable" + tableIndex);

	if (!Table || !Button) {
		return false;
	}

	var Rows = Table.getElementsByTagName("tr"); 

	if (Button.firstChild.data == collapseCaption) {
		for (var i = 1; i < Rows.length; i++) {
			Rows[i].style.display = "none";
		}
		Button.firstChild.data = expandCaption;
	} else {
		for (var i = 1; i < Rows.length; i++) {
			Rows[i].style.display = Rows[0].style.display;
		}
		Button.firstChild.data = collapseCaption;
	}
}
 
function createCollapseButtons() {
	var tableIndex = 0;
	var NavigationBoxes = new Object();
	var Tables = document.getElementsByTagName("table");

	for (var i = 0; i < Tables.length; i++) {
		if (hasClass(Tables[i], "collapsible")) {
			NavigationBoxes[ tableIndex ] = Tables[i];
			Tables[i].setAttribute("id", "collapsibleTable" + tableIndex);

			var Button     = document.createElement("span");
			var ButtonLink = document.createElement("a");
			var ButtonText = document.createTextNode(collapseCaption);

			Button.style.styleFloat = "left";
			Button.style.cssFloat = "left";
			Button.style.textAlign = "left";
			Button.style.width = "6em";

			ButtonLink.setAttribute("id", "collapseButton" + tableIndex);
			ButtonLink.setAttribute("href", "javascript:collapseTable(" + tableIndex + ");");
			ButtonLink.appendChild(ButtonText);

			Button.appendChild(document.createTextNode("["));
			Button.appendChild(ButtonLink);
			Button.appendChild(document.createTextNode("]"));

			var Header = Tables[i].getElementsByTagName("tr")[0].getElementsByTagName("th")[0];
			Header.insertBefore(Button, Header.childNodes[0]);

			tableIndex++;
		}
	}

	for (var i = 0 ; i < tableIndex ; i++) {
		if (hasClass(NavigationBoxes[i], "collapsed") || (tableIndex >= autoCollapse && hasClass(NavigationBoxes[i], "autocollapse"))) {
			collapseTable(i);
		}
	}
}
 
addOnloadHook(createCollapseButtons);
 
 // == END Collapsible tables =============================

/** وصلات إنترويكي للمقالات المختارة ***************************************
 *
 *  وصف: يبرز هذا القسم الوصلات التي اختيرت كمقالات مميزة في ويكيبيديا باللغات الأخرى بوضع نجمة
    مكان مربع القائمة.
    المصدر: نسخ من ويكيبيديا الإنجليزية.
*/
function LinkFA() {
	for(var i=0; a = document.getElementsByTagName("span")[i]; i++) {
		if(a.className == "FA") {
			for(var j=0; b = document.getElementsByTagName("li")[j]; j++) {
				if(b.className == "interwiki-" + a.id) {
					b.className += " FA";
					b.title = "اختيرت كمقالة مختارة";
				}
			}
		}
	}
}

addOnloadHook(LinkFA);

function LinkGA() {
	for(var i=0; a = document.getElementsByTagName("span")[i]; i++) {
		if(a.className == "GA") {
			for(var j=0; b = document.getElementsByTagName("li")[j]; j++) {
				if(b.className == "interwiki-" + a.id) {
					b.className += " GA";
					b.title = "اختيرت كمقالة جيدة";
				}
			}
		}
	}
}

addOnloadHook(LinkGA);

/* 

//============================================================
//
// إضافة بلوك للتحميل لمن يقومون برفع ملفات بدون ترخيص، لن يمنعهم من المشاركة العادية، فقط
   تمنع تحميل الملفات.
// لمنع أي مستخدم من تحميل أي ملف فقط انسخ السطرين في الأسفل بدون علامات الخطوط التي تسبقها 
//
// ============================================================

//var date = new Date(); date.setTime(date.getTime()+86400000);
//document.cookie = "evilbit=True; expires=" + date.toGMTString() + "; path=/";

//إلى صفحة User:Username/monobook.js

//هذا سيمنع المستخدم من رفع الملفات لمدة 24 ساعة فور دخوله بحسابه.
//لا يتوجب عليهم أن يعدلوا أي شيء، مجرد الدخول بالحساب يكفي، طبعا يظلون قادرين على التعديل في
  جميع أنحاء الموسوعة
//

function checkevilbit() {
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
                var c = ca[i];
                if (c.replace(/^\s+|\s+$/g,"") == "evilbit=True") {
                        document.getElementById("wpSave").disabled=true;
                        document.getElementById("wpUpload").disabled=true;
                } 
        }
}

addOnloadHook(checkevilbit);

</pre> */

//دالة  مساعدة لإضافة زر جديد لأحد قوائم الواجهة.
//An improved(I hope) version of [[Wikipedia:WikiProject User scripts/Scripts/Add LI link|addlilink]].
//[[User:JesseW/sig|JesseW, the juggling janitor]] 05:33, 8 November *2005 (UTC)

function addLink(where, url, name, id, title, key, after){
	//* where is the id of the toolbar where the button should be added;
	//   i.e. one of "p-cactions", "p-personal", "p-navigation", or "p-tb".
	//
	//* url is the URL which will be called when the button is clicked.
	//   javascript: urls can be used to do more complex things.
	//
	//* name is what will appear as the name of the button.
	//
	//* id is the id of the button; it's best to define one.	
	//   Use a prefix to make sure its unique. Optional.
	//
	//* title is the tooltip title that gives a longer description 
	//   of the button; if you define a accesskey, mention it here. Optional.
	//
	//* key is the char you want for the accesskey. Optional.
	//
	//* after is the id or DOM node of the button you want to follow this one. Optional.
	//
	var na = document.createElement('a');
	na.href = url;
	na.appendChild(document.createTextNode(name));
	var li = document.createElement('li');
	if(id) li.id = id;
	li.appendChild(na);
	var tabs = document.getElementById(where).getElementsByTagName('ul')[0];
	if (!after) {
		tabs.appendChild(li);
	} else if (after.cloneNode) { // looks like a DOM node
		tabs.insertBefore(li,after);
	} else { // assume this is an ID string
		tabs.insertBefore(li,document.getElementById(after));
	}
	if(id) {
		if(key && title) { ta[id] = [key, title]; }
		else if(key) { ta[id] = [key, '']; }
		else if(title) { ta[id] = ['', title];} 
	}
	// re-render the title and accesskeys from existing code in wikibits.js
	akeytt();
	return li;
}

 /** WikiMiniAtlas *******************************************************
   *
   *  Description: WikiMiniAtlas is a popup click and drag world map.
   *               This script causes all of our coordinate links to display the WikiMiniAtlas popup button.
   *               The script itself is located on meta because it is used by many projects.
   *               See [[Meta:WikiMiniAtlas]] for more information. 
   *  Created by: [[User:Dschwen]]
   */

document.write('<script type="text/javascript" src="' 
	 + 'http://meta.wikimedia.org/w/index.php?title=MediaWiki:Wikiminiatlas.js' 
	 + '&action=raw&ctype=text/javascript&smaxage=21600&maxage=86400"></script>');

if (/msie 8\.0/.test(navigator.userAgent.toLowerCase()))
	importStylesheet('Mediawiki:IE8Fixes.css');

/* MediaWiki:Monobook.js */
/* <source lang=javascript> */
/* Any JavaScript here will be loaded for users using the MonoBook skin */

addOnloadHook(function() {
	var upload = document.getElementById('t-upload');
	if (upload) upload.childNodes[0].setAttribute('href', '/wiki/%D9%88%D9%8A%D9%83%D9%8A%D8%A8%D9%8A%D8%AF%D9%8A%D8%A7:%D8%B1%D9%81%D8%B9_%D8%A7%D9%84%D8%B5%D9%88%D8%B1');
});

/** Main Page layout fixes
 *
 *  Description:   Various layout fixes for the main page, including an
 *  additional link to the complete list of languages available
 *  and the renaming of the 'Article' to to 'Main Page'.
 *  Maintainers:   [[User:AzaToth]], [[User:R. Koot]], [[User:Alex Smotrov]]
 */
 
function mainPageRenameNamespaceTab() {
	try {
		var Node = document.getElementById('ca-nstab-main').firstChild;
		if (Node.textContent) { // Per DOM Level 3
			Node.textContent = 'الصفحة الرئيسية';
		} else if (Node.innerText) { // IE doesn't handle .textContent
			Node.innerText = 'الصفحة الرئيسية';
		} else {   // Fallback
			Node.replaceChild(Node.firstChild, document.createTextNode('الصفحة الرئيسية')); 
		}
	} catch(e) {
		// bailing out!
	}
}

if (wgTitle == 'الصفحة الرئيسية' && (wgNamespaceNumber == 0 || wgNamespaceNumber == 1)) {
	switch(wgNamespaceNumber) {
		case 0:
			addOnloadHook(mainPageAppendCompleteListLink);
			addOnloadHook(mainPageRenameNamespaceTab);
			break;
		case 1:
			addOnloadHook(mainPageRenameNamespaceTab);
			break;
	}
}

function mainPageAppendCompleteListLink() {
	addPortletLink('p-lang', 'http://meta.wikimedia.org/wiki/قائمة_الويكيبيديات', 'القائمة الكاملة', 'interwiki-completelist', 'القائمة الكاملة للويكيبيديات');
}