/**********************************************************
CONFIGURATION FOR NIELSEN GROUPING 
Author: Harold Jacinto

Related Files
1. misc_web.js
2. nielsenConfig.js
*************************************************************/

//<!-- START Nielsen Online SiteCensus V6.0 -->

//<!-- COPYRIGHT 2009 Nielsen Online -->

var statIdentifier;
//var nielsen_CG;

/** SET DEFAULTS WHEN THERE IS NO CATEGORY **/
if(statIdentifier===undefined ||  statIdentifier.nielsen=="")
	nielsen_CG = "0";
else
	nielsen_CG = statIdentifier.nielsen;

//alert(nielsen_CG);

 var pvar = { cid: "mena-mbc", content: nielsen_CG, server: "secure-uk" };
 var trac = nol_t(pvar);
 trac.record().post();

 document.write('<noscript><div><img src="//secure-uk.imrworldwide.com/cgi-bin/m?ci=mena-mbc&amp;cg=0&amp;cc=1&amp;ts=noscript" width="1" height="1" alt="" /></div></noscript>');