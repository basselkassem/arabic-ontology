/*****************************************************************************************
Alarabiya.net Urchin Statistics Code
*****************************************************************************************/
function IncludeJavaScript(jsFile)
{
	document.write('<scr'+'ipt type="text/javascript" src="' +jsFile+ '"><\/scr'+'ipt>');
}
//For Nielsen Script
//IncludeJavaScript('//secure-uk.imrworldwide.com/v60.js'); 
IncludeJavaScript('/assets/ar/js/Nielsen.js');
//For Xgemius
IncludeJavaScript('/assets/ar/js/xgemius.js');

IncludeJavaScript('/assets/ar/js/effectiveMeasure.js');

//For Google Analytics Script
/* transferred to header
IncludeJavaScript('http://www.google-analytics.com/ga.js');
IncludeJavaScript('/assets/ar/js/googleid.js');
*/
//For Urchin Script
//IncludeJavaScript('http://wasv3.navlink.com/__utmala.js');

//this line of code to avoid javascript error
function _sp_trackV2(sp)
{
	var statvar = 1;	
}