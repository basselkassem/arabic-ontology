document.write('<!-- Template ID = 8485 Template Name = Banner Creative (Flash) - InPage Multiples withct=NL&st=&ac=165&zp=&bw=4&dma=1&city=9920 -->\n<!-- Copyright 2006 DoubleClick Inc., All rights reserved. -->\n');

function DCFlash(id){
swf = "http://s0.2mdn.net/1723100/728x90_V3.swf";
gif = "http://s0.2mdn.net/1723100/728x90_V3.swf";
minV = 6;
FWH = ' width="728" height="90" ';
url = escape("http://ad.sa.doubleclick.net/click%3Bh%3Dv8/3cc4/3/0/%2a/r%3B260116197%3B0-0%3B0%3B52562305%3B3454-728/90%3B49564784/49558387/1%3B%3B%7Esscs%3D%3fhttp://www.alarabiya.net/programs.html");
wmode = "opaque";
bg = "ffffff";
dcallowscriptaccess = "never";

openWindow = "false";
winW = 600;
winH = 400;
winL = 0;
winT = 0;

moviePath=swf.substring(0,swf.lastIndexOf("/"));
var sm=new Array();
sm[1] = "";
sm[2] = "";
sm[3] = "";
sm[4] = "";
sm[5] = "";

var ct=new Array();
ct[0]="";if(ct[0].substr(0,4)!="http"){ct[0]="";}         
ct[1] = "";
ct[2] = "";
ct[3] = "";
ct[4] = "";
ct[5] = "";
ct[6] = "";
ct[7] = "";
ct[8] = "";
ct[9] = "";
ct[10] = "";

fv='"ct=NL&st=&ac=165&zp=&bw=4&dma=1&city=9920&clickTAG='+url+'&clickTAG='+url+'&moviePath='+moviePath+'/'+'&moviepath='+moviePath+'/';
for(i=1;i<sm.length;i++){if(sm[i]!=""){fv+="&submovie"+i+"="+escape(sm[i]);}}
for(i=1;i<ct.length;i++){if(ct[i]!=""){if(ct[i].indexOf("http")==0){x=(ct[i].indexOf("doubleclick.net/clk;")>9)?escape(ct[0]+ct[i]):escape("http://ad.sa.doubleclick.net/click%3Bh%3Dv8/3cc4/3/0/%2a/r%3B260116197%3B0-0%3B0%3B52562305%3B3454-728/90%3B49564784/49558387/1%3B%3B%7Esscs%3D%3f"+ct[i]);}else{x=escape(ct[i]);}fv+="&clickTag"+i+"="+x+"&clickTAG"+i+"="+x+"&clicktag"+i+"="+x;}}
fv+='"';
bgo=(bg=="")?"":'<param name="bgcolor" value="#'+bg+'">';bge=(bg=="")?"":' bgcolor="#'+bg+'"';
this.FSWin=function(){if((openWindow=="false")&&(id=="DCF0"))alert('openWindow is wrong.');if((openWindow=="center")&&window.screen){winL=Math.floor((screen.availWidth-winW)/2);winT=Math.floor((screen.availHeight-winH)/2);}window.open(unescape(url),id,"width="+winW+",height="+winH+",top="+winT+",left="+winL+",status=no,toolbar=no,menubar=no,location=no");}
this.mF=function(){swf=swf+'?ct=NL&st=&ac=165&zp=&bw=4&dma=1&city=9920&clickTAG='+url;
	var adcode='<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="'+id+'"'+FWH+'>'+
		'<param name="movie" value="'+swf+'"><param name="flashvars" value='+fv+'><param name="quality" value="high"><param name="AllowScriptAccess" VALUE="'+dcallowscriptaccess+'"><param name="wmode" value="'+wmode+'">'+bgo+
		'<embed src="'+swf+'" flashvars='+fv+bge+FWH+' type="application/x-shockwave-flash" quality="high" swliveconnect="true" wmode="'+wmode+'" AllowScriptAccess="'+dcallowscriptaccess+'" name="'+id+'"></embed></object>';
  if(('j'!="j")&&(typeof dclkFlashWrite!="undefined")){dclkFlashWrite(adcode);}else{document.write(adcode);}}
eval('this.mI=function(){document.write(\'<a target="_blank" href="'+unescape(url)+'"><img src="'+gif+'"'+FWH+'border="0" galleryimg="no"></a>\');}');
this.getCode=function(pV){ua=navigator.userAgent;a=(minV<=pV&&(openWindow=="false"||(ua.indexOf("Mac")<0&&ua.indexOf("Opera")<0)))?this.mF():this.mI();return a;}}
pVF="No Flash";pVM=0;DCid=(isNaN("260116197"))?"DCF0":"DCF260116197";
if(navigator.plugins && navigator.mimeTypes.length){
  var x=navigator.plugins["Shockwave Flash"];if(x && x.description){pVFull=x.description;a=pVFull.indexOf("Flash ")+6;pVM=pVFull.substring(a,pVFull.indexOf(".",a));}}
else if (window.ActiveXObject && window.execScript){
  window.execScript('on error resume next\na=2\ndo\na=a+1\nset swControl = CreateObject("ShockwaveFlash.ShockwaveFlash."&a)\nloop while Err = 0\n',"VBScript");if(a>3){pVM=a-1;}}
eval("function "+DCid+"_DoFSCommand(c,a){if(c=='openWindow')o"+DCid+".FSWin();}o"+DCid+"=new DCFlash('"+DCid+"');o"+DCid+".getCode(pVM);");
//-->

document.write('\n<noscript><a target=\"_blank\" href=\"http://ad.sa.doubleclick.net/click%3Bh%3Dv8/3cc4/3/0/%2a/r%3B260116197%3B0-0%3B0%3B52562305%3B3454-728/90%3B49564784/49558387/1%3B%3B%7Esscs%3D%3fhttp://www.alarabiya.net/programs.html\"><img src=\"http://s0.2mdn.net/1723100/728x90_V3.swf\" width=\"728\" height=\"90\" border=\"0\" galleryimg=\"no\"></a></noscript>\n');
