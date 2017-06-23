var statIdentifier = new Object(); //GENERIC 
var global_CG;

function writeStatFunction(funcStr)
{
	document.write('<scr'+'ipt type="text/javascript">'+ funcStr +'<\/scr'+'ipt>');
}

function setStatsVariableValue(surveyName)
{
	statIdentifier[surveyName] = global_CG;
	
	//alert(statIdentifier[surveyName]);
}

function setPageCategory(surveyName)
{
	var pageType = PageOptions.contentType;
	var site_lang = PageOptions.lang;
	var pageCat = PageOptions.contentSection;
	var pageSection = PageOptions.contentSection;
	/*
	var pageCat = PageOptions.contentCateg;
	var pageSection = PageOptions.contentSection;
	*/
	
	var len;
	var key;
	
	if(pageType.toUpperCase()=='HOME')
	{
		isHomeVal = true;
		//alert('Home');
	}
	else
		isHomeVal = false;
	


	/* THE ALGORITHM */
	/****** STEP1: Check if category exists **************/
	tmp_CG = pageCat.split(",");

	len = tmp_CG.length;
	for(var i=0; i<len; i++) 
	{
		key = tmp_CG[i];
		global_CG  = getStatCategory(surveyName, isHomeVal, key);		
		
		if(global_CG!="") 
		{
			setStatsVariableValue(surveyName);
			return 0;
		}
	}
	
	/**** STEP2: Check Opinion then get the section code **********/
	if(pageType=='Views')
	{
		tmp_websection = pageSection.split("|");
		if(tmp_websection.length >0 ) tmp_websection = tmp_websection[0];
		key = site_lang+"-opinions-" + tmp_websection;
		global_CG = getStatCategory(surveyName, false, key);
		//alert('global_CG' + global_CG);
		if(global_CG=="") //IF NOT FOUND IN ANY CATEGORY
		{
			key = site_lang+"-opinions";
			global_CG = getStatCategory(surveyName, false, key);
		}
		
		if(global_CG!="") 
		{
			setStatsVariableValue(surveyName);
			return 0;
		}
	}
	

	/**** STEP3: Check if associated section code **********/
	tmp_CG2 = pageSection.split("|");
	len =tmp_CG2.length;

	for(var j=0; j<len; j++) 
	{
		key = tmp_CG2[j];
		global_CG  = getStatCategory(surveyName, isHomeVal, key );
		//alert('global_CG' + global_CG);
		if(global_CG!="") 
		{
			setStatsVariableValue(surveyName);
			return 0;
		}

	}

	/**** STEP4: Check if pageType is "Program" then set to the default program group or if view then get the parent websection code**********/
	if(pageType=="Programs")
	{
		key  = site_lang+"-programs";
		global_CG = getStatCategory(surveyName, false, key);
		//alert('global_CG' + global_CG);
		if(global_CG!="") 
		{
			setStatsVariableValue(surveyName);
			return 0;
		}
	}

	/*** STEP 5: if article does not belong to any category ***/
	if(pageType=='Articles' && global_CG=='')
	{
		key = site_lang + "-othernews";
		global_CG = getStatCategory(surveyName, false, key);
		//alert('global_CG' + global_CG);
		if(global_CG!="") 
		{
			setStatsVariableValue(surveyName);
			return 0;
		}
	}
	
	/*** STEP 6: if doesn't meet the above criteria ***/
	if(site_lang=='' || site_lang==undefined)
		site_lang == 'ar'; //SET ARABIC AS DEFAULT WHEN NO LANGUAGE SET
		
	if(global_CG===undefined || global_CG=="")
	{
		
		global_CG = eval(surveyName+"_Default['" + site_lang + "']");
		//alert('global_CG' + global_CG);
		setStatsVariableValue(surveyName);
		
	}
	
}

function getStatCategory(surveyName, isHome, catVal)
{
			var statCat;
			
			if(isHome)
				statCat = eval(surveyName+"_Home['" + catVal + "']");
			else
				statCat = eval(surveyName+"_Detail['" + catVal + "']");
			
			if(statCat === undefined) //Array Does not Exists
				return ""
			else
				return statCat;
}




writeStatFunction("setPageCategory('nielsen')"); //WRITE NIELSEN CATEGORY
//document.write('<scr'+'ipt type="text/javascript">'+ alert(global_CG) +'<\/scr'+'ipt>');
writeStatFunction("setPageCategory('xgemius');"); //WRITE XGEMIUS CATEGORY
//document.write('<scr'+'ipt type="text/javascript">'+ alert(global_CG) +'<\/scr'+'ipt>');