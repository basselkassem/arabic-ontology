// JavaScript Document
var PageOptions ={lang:"", contentType:"", contentCateg:"", contentSection:"", webRootPath:"", articleId:""};

function setPageOptions(lang, contentType, contentSection, contentCateg, webRootPath)
{
	PageOptions.lang = getNonNullValue(lang);
	PageOptions.contentType = getNonNullValue(contentType);
	PageOptions.contentCateg = getNonNullValue(contentCateg);
	PageOptions.contentSection = getNonNullValue(contentSection);
	PageOptions.webRootPath = getNonNullValue(webRootPath);
}

function getNonNullValue(objVal)
{
	if(objVal===undefined)
	   return "";
	 else
	 	return objVal;
}
