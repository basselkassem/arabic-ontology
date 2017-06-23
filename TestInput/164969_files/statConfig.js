/**********************************************************
CONFIGURATION FOR STATISTICS 
Related Files
1. stats.js
2. nielsen.js
3.xgemius.js

NOTE: 
Naming Convention: statatisticsName_PageType
	where:
		statisticsName = the name called in setPageCategory(statName) e.g. setPageCategory('nielsen')
		pageType = values are 'Home', 'Detail' and 'Default'
*************************************************************/


/***** BEGIN NIELSEN CONFIGURATION **************************/
var nielsen_Detail = new Object();
var nielsen_Home = new Object();
var nielsen_Default = new Object();
	/*********** BEGIN ARABIC **********************************
	FOR WEBSECTIONS. WEBSECTION CODES IS USED AS KEY
	***********************************************************/
	nielsen_Home["default"] 					= 	"ar-home";
	nielsen_Home["world"]						= 	"ar-world-main"; 
	nielsen_Home["sports"] 						= 	"ar-sport-main";
	nielsen_Home["league"] 						= 	"ar-sport-league-main"; 
	nielsen_Home["football"] 					= 	"ar-sport-football-main"; 		
	nielsen_Home["medicine_health"] 			= 	"ar-health-main";
	nielsen_Home["daleel_elafia"] 				= 	"ar-health-dalel-main"; 
	nielsen_Home["culture_and_art"] 			= 	"ar-culture-main";
	nielsen_Home["technology"] 					= 	"ar-technology-main"; 
	nielsen_Home["last_page"] 					= 	"ar-last-main";
	nielsen_Home["livestreaming"] 				= 	"ar-livestreaming";
	nielsen_Home["opinions"] 					= 	"ar-opinions";
	nielsen_Home["world"]						= 	"ar-world-main"; 
	nielsen_Home["programs"]					=	"ar-programs-main";
	nielsen_Home["ar-last"]						=	"ar-last";
	nielsen_Home["ar-other"] 					= 	"ar-otherPages";//GENERIC
	nielsen_Home["writters"]					=	"ar-opinions-writers";
	nielsen_Home["alaswaq"]						=	"ar-alaswaq";
	nielsen_Home["alaswaq_interviews"]			=	"ar-alaswaq-specInterv-main";	
	nielsen_Home["alaswaq_economy"]				=	"ar-alaswaq-economy-main";	
	nielsen_Home["alaswaq_companies"]			=	"ar-alaswaq-companies-main";	
	nielsen_Home["alaswaq_researches"]			=	"ar-alaswaq-research-main";	
	nielsen_Home["alaswaq_banks"]				=	"ar-alaswaq-services-main";	
	nielsen_Home["alaswaq_finance"]				=	"ar-alaswaq-financialMarkets-main";	
	nielsen_Home["alaswaq_islamic"]				=	"ar-alaswaq-islamic-main";	
	nielsen_Home["alaswaq_property"]			=	"ar-alaswaq-realEst-main";	
	nielsen_Home["alaswaq_technology"]			=	"ar-alaswaq-techEconomy-main";	
	//nielsen_Home["alaswaq_stock_ex"]			=	"ar-alaswaq-financialMarkets-main";	
	nielsen_Home["alaswaq_opinions"]			=	"ar-alaswaq-aswaqOthers-main";	
	nielsen_Home["videos"]						= 	"ar-videos-main";
	nielsen_Home["anaara"]						= 	"ar-videos-anaara";
	
	nielsen_Home["meast"]						= 	"ar-mena-main"; 	
	nielsen_Home["tunisia"]						=	"ar-mena-tn-main";
	nielsen_Home["egypt"]						=	"ar-mena-eg-main";
	nielsen_Home["libya"]						=	"ar-mena-lb-main";
	nielsen_Home["yemen"]						=	"ar-mena-ye-main";
	nielsen_Home["oman"]						=	"ar-mena-om-main";
	nielsen_Home["algeria"]						=	"ar-mena-dz-main";
	nielsen_Home["lebanon"]						=	"ar-mena-le-main";	
	nielsen_Home["kuwait"]						=	"ar-mena-kw-main";	
	nielsen_Home["qatar"]						=	"ar-mena-qa-main";
	nielsen_Home["ksa"]							=	"ar-mena-sa-main";	
	nielsen_Home["uae"]							=	"ar-mena-ae-main";
	nielsen_Home["bahrain"]						=	"ar-mena-bh-main";	
	nielsen_Home["jordan"]						=	"ar-mena-jo-main";
	nielsen_Home["syria"]						=	"ar-mena-sy-main";		
	nielsen_Home["iraq"]						=	"ar-mena-iq-main";			
	nielsen_Home["sudan"]						=	"ar-mena-sd-main";	
	nielsen_Home["adab"]						=	"ar-literature-main";	
	nielsen_Home["alhadath"]					=	"ar-hadath";	
	nielsen_Home["saudi"]						= 	"ar-saudiToday";
	nielsen_Home["alaswaq_oil_gas"]				= 	"ar-alaswaq-oilGas";
	nielsen_Home["alaswaq_travel"]				= 	"ar-alaswaq-tavelTourism";
	nielsen_Home["institute"]					= 	"ar-studies-main";
	
	//ARTICLE CATEGORY CODE AS KEY
	//These KEYS are category code in the category List
	nielsen_Detail["adab"]						=	"ar-literature-articles";
	nielsen_Detail["meast"]						= 	"ar-mena-articles";
	nielsen_Detail["world"]						= 	"ar-world-articles";	
	nielsen_Detail["sports"]					= 	"ar-sport-other";
	nielsen_Detail["league"]					= 	"ar-sport-league-articles";
	//nielsen_Detail["SportsFootball"]			= 	"ar-sport-football-articles";
	//nielsen_Detail["SportsOther"]				= 	"ar-sport-other";
	nielsen_Detail["angola2010"]				= 	"ar-sport-other";	
	nielsen_Detail["alaswaq_interviews"]		=	"ar-alaswaq-specInterv-articles";	
	nielsen_Detail["alaswaq_economy"]			=	"ar-alaswaq-economy-articles";	
	nielsen_Detail["alaswaq_companies"]			=	"ar-alaswaq-companies-articles";	
	nielsen_Detail["alaswaq_researches"]		=	"ar-alaswaq-research-articles";	
	nielsen_Detail["alaswaq_banks"]				=	"ar-alaswaq-services-articles";	
	nielsen_Detail["alaswaq_finance"]			=	"ar-alaswaq-financialMarkets-articles";	
	nielsen_Detail["alaswaq_islamic"]			=	"ar-alaswaq-islamic-articles";	
	nielsen_Detail["alaswaq_property"]			=	"ar-alaswaq-realEst-articles";	
	nielsen_Detail["alaswaq_technology"]		=	"ar-alaswaq-techEconomy-articles";	
	//nielsen_Detail["alaswaq_stock_ex"]			=	"ar-alaswaq-financialMarkets-articles";	
	nielsen_Detail["alaswaq_opinions"]			=	"ar-alaswaq-aswaqOthers-articles";	
	nielsen_Detail["culture_and_art"]			= 	"ar-culture-articles";			
	nielsen_Detail["technology"]				= 	"ar-technology-articles";				
	nielsen_Detail["tunisia"]					=	"ar-mena-tn-articles";
	nielsen_Detail["egypt"]						=	"ar-mena-eg-articles";
	nielsen_Detail["libya"]						=	"ar-mena-lb-articles";
	
	nielsen_Detail["yemen"]						=	"ar-mena-ye-articles";
	nielsen_Detail["oman"]						=	"ar-mena-om-articles";
	nielsen_Detail["algeria"]					=	"ar-mena-dz-articles";
	nielsen_Detail["lebanon"]					=	"ar-mena-le-articles";	
	nielsen_Detail["kuwait"]					=	"ar-mena-kw-articles";	
	nielsen_Detail["qatar"]						=	"ar-mena-qa-articles";
	nielsen_Detail["ksa"]						=	"ar-mena-sa-articles";	
	nielsen_Detail["uae"]						=	"ar-mena-ae-articles";
	nielsen_Detail["bahrain"]					=	"ar-mena-bh-articles";	
	nielsen_Detail["jordan"]					=	"ar-mena-jo-articles";
	nielsen_Detail["syria"]						=	"ar-mena-sy-articles";		
	nielsen_Detail["iraq"]						=	"ar-mena-iq-articles";			
	nielsen_Detail["sudan"]						=	"ar-mena-sd-articles";		
	nielsen_Detail["alhadath"]					=	"ar-hadath";		
	nielsen_Detail["saudi"]						= 	"ar-saudiToday";
	nielsen_Detail["alaswaq_oil_gas"]			= 	"ar-alaswaq-oilGas";
	nielsen_Detail["alaswaq_travel"]			= 	"ar-alaswaq-tavelTourism";
	nielsen_Detail["institute"]					= 	"ar-studies-articles";
	
	nielsen_Detail["News"]						= 	"ar-othernews";
	//Not included in the category List but will be used if article is assigned to health websection
	nielsen_Detail["medicine_health"] 			= 	"ar-health-articles"; 
	nielsen_Detail["daleel_elafia"] 			= 	"ar-health-dalel-articles";
	nielsen_Detail["progs_health"]				= 	"ar-health-articles";
	nielsen_Detail["culture_and_art"]			= 	"ar-culture-articles";
	nielsen_Detail["technology"] 				= 	"ar-technology-articles";
	nielsen_Detail["ar-programs"] 				= 	"ar-programs-rest"; //GENERIC (used by code)
	nielsen_Detail["ar-other"] 					= 	"ar-otherPages"; //GENERIC	
	
	
	//FOR TYPE VIEWS FORMAT: language + "-opinions-" + WebSection code. 
	//This List are not included in the category list but it will take the websection code if view is assigned to a websection
	nielsen_Detail["ar-opinions"]					= 	"ar-opinions"; //GENERIC
	nielsen_Detail["ar-opinions-sports"] 			= 	"ar-opinions-sports";
	nielsen_Detail["ar-opinions-programs"]			= 	"ar-opinions-programs";		
	nielsen_Detail["ar-opinions-world"] 			= 	"ar-opinions-worldMena"; //NA	
	nielsen_Detail["ar-opinions-meast"] 			= 	"ar-opinions-worldMena"; //NA	
	nielsen_Detail["ar-opinions-culture_and_art"] 	= 	"ar-opinions-culture"; //NA
	nielsen_Detail["ar-opinions-medicine_health"] 	= 	"ar-opinions-health";
	nielsen_Detail["ar-opinions-technology"] 		= 	"ar-opinions-technology"; //NA
	nielsen_Detail["ar-opinions-LastPage"] 			= 	"ar-opinions-last";
	nielsen_Detail["ar-opinions-alarabiyaWriters"] 	= 	"ar-opinions-alarabiyaWriters";//NA	
	/******* END ARABIC ***********************************************/
	

	/*********** BEGIN DEFAULT VALUES **************************
	DEFAULT VALUES IF CATEGORY NOT FOUND ABOVE
	***********************************************************/
	nielsen_Default["ar"] = "ar-others";
	nielsen_Default["en"] = "en-other";
	nielsen_Default["fa"] = "pr-other";
	nielsen_Default["ur"] = "ur-other";

/** END NIELSEN CONFIGURATION **/




/***** BEGIN GEMIUS CONFIGURATION **************************/
var xgemius_Detail = new Object();
var xgemius_Home = new Object();
var xgemius_Default = new Object();

	/*********** BEGIN ARABIC **********************************
	FOR WEBSECTIONS. WEBSECTION CODES IS USED AS KEY
	***********************************************************/
	xgemius_Home["default"] 					= 	"bQBKUUdWT_A7KPPiTjA2WZevLXUI2MM2fJ4nYAW6JGr.w7";
	xgemius_Home["meast"]						= 	"16.gja8.Yw8FTVeA6XhOl5evzSsI2ALmstZdiwALdDv.v7"; 
	xgemius_Home["world"]						= 	"baBAq0bWcx.xzvbWXquu4JdRzUsITAL2HNfXLFzNzND.T7"; 
	xgemius_Home["sports"] 						= 	"ba.qj0bWPvGbPp513fAS4pdRLVUITMMmHC4ngt1ZfB...7";
	xgemius_Home["league"] 						= 	"bQpAq0bjcx.xX7zyJl55R_Tk7zYFituaNxpg.W3opgL.V7"; 	
	xgemius_Home["football"] 					= 	"baBArUbWc0eRib2wFkr5oZbxLVUIjMMmsuknAAC3fLv.u7"; 		
	xgemius_Home["medicine_health"] 			= 	"d1Xq8c7MP6fPLj3nuOECTJcPzSsImALmHBtdCV2gzNj.07";
	xgemius_Home["daleel_elafia"] 				= 	"d1ZKsc7MT2.VPD5Hsd76ZZcP71YImNuqsuBgXahNJjX.c7"; 
	xgemius_Home["culture_and_art"] 			= 	"d2.g689ZY1fVPLcO6QpezeVELVUFSsMmscMnAJZUJMr.87"; 
	xgemius_Home["technology"] 					= 	"zU.qjyb8PvGb8Z5elk8WzrbuLVTY.8MmRRNk9BtW0Hj.v7"; 
	xgemius_Home["last_page"] 					= 	"d1Xq787MP3E1G1PQ_bFeVZev7zYI2NuastZg.QCzThH.r7";
	xgemius_Home["livestreaming"] 				= 	"15ZAS69yc5.VXxzmIW6Nm8VHjw011xtaKnFdXcjkdUP.V7";
	//xgemius_Home["opinions"] 					= 	"ar-opinions";
	xgemius_Home["world"]						= 	"baBAq0bWcx.xzvbWXquu4JdRzUsITAL2HNfXLFzNzND.T7"; 
	xgemius_Home["ar-last"]						=	"d1Xq787MP3E1G1PQ_bFeVZev7zYI2NuastZg.QCzThH.r7";
	xgemius_Home["writters"]					=	"d2.q789ZP3EFKp3eeaIWjpev78wI2NuORcqtznsSTXX.s7";
	xgemius_Home["alaswaq_interviews"]			=	"dvXg689MY1fPOB0fOG_ZZZbx71YIjNuqspLqxIBN0Qj.E7";	
	xgemius_Home["alaswaq_economy"]				=	"15Xqka9yPyfVXz3w2VUOT8WnzbU1lwMCijS6WEFBYHr.G7";	
	xgemius_Home["alaswaq_companies"]			=	"zang7ScJY49rKldgTpVOb5evLXUI2MM2HButnd0t.Mn.a7";	
	xgemius_Home["alaswaq_researches"]			=	"dvZKUc9MT_A1UT4HwRRypZbx7_wIjNueUjBgGNfzzu3.47";	
	xgemius_Home["alaswaq_finance"]				=	"zapAqycJcxCbPVeLltG6u7eO7_vYP9ue5TmtLsuWTQ3.t7";	
	xgemius_Home["alaswaq_islamic"]				=	"d1ZAq87Mcx.VPL1SMeP5QJdRj8MITBteHC4Q5LUldlT.K7";	
	xgemius_Home["alaswaq_property"]			=	"dvXgi89MY9hvmH2r4Bih.OWij8MF1hteN0cQ5JaoHgv.g7";	
	xgemius_Home["alaswaq_technology"]			=	"15Xqka9yPyfVXz3w2VUOT8WnzbU1lwMCijS6WEFBYHr.G7";	
	//xgemius_Home["alaswaq_opinions"]			=	"ar-asw-other";		
	//xgemius_Home["alaswaq_banks"]				=	"ar-asw-other";		
	xgemius_Home["videos"]						= 	".cpLoqbg9HImELVhAin1WMew.jzYfUbs3xxnlHizCWD.c7";
	xgemius_Home["anaara"]						= 	"B7XlpC9aiVZkJVvBNMHh6dTJ.fM11UbwWsxH6ZjGoWT.G7";
	xgemius_Home["tunisia"]						=	"B7Xr4i9a5Lpqol_x1PrRO9TJXhw11QcxWpYR4EEB_a3.S7";
	xgemius_Home["egypt"]						=	"pxXlRD9Hidaa1vy19aXZssbiXhvYxAcx30kR4FBJ_a3.97";
	xgemius_Home["libya"]						=	"pxXrQD9H5AM0Kl7TfMpREseC7FjYBCxXf4aEn4COTMP.W7";


	//ARTICLE CATEGORY CODE AS KEY
	//These KEYS are category code in the category List
	xgemius_Detail["meast"]						= 	"bQnq8UbjP6iRfz5HlqsKNOVELd8FSsNCNySt4padfLH.y7";
	xgemius_Detail["world"]						= 	"16BKr68.TymlEN5ckPVqrJevLVUI2MMmfJ4nAK2wfLv.X7";	
	xgemius_Detail["sports"]					= 	"zaBKTyd8T6lhHfRCLr226LeOzZTYPwLyHLfXj31zTJj.R7";
	xgemius_Detail["news"]						= 	"d2.g689ZY1hvjnzrYOmpNOVEjy0FShtqUUAQKcZ59mf.E7";
	//Not included in the category List but needed if article is assigned to health websection
	xgemius_Detail["medicine_health"] 			= 	"zUngjSeJYw8LZFcgLkPOkJcPLXUImMM2fL6tna0gJGr..7";
	xgemius_Detail["daleel_elafia"] 			= 	"dw.qkc7ZPyhvXj4HYHmC.OTk71YFituqsUzKfG5ZueT.U7";
	xgemius_Detail["progs_health"]				= 	"zUngjSeJYw8LZFcgLkPOkJcPLXUImMM2fL6tna0gJGr..7";
	xgemius_Detail["culture_and_art"]			= 	"d1ZAS87Mc5.PLr0ScMj56Jevj8MI2BtesfYQ5ACjHgv.T7";
	//xgemius_Detail["technology"] 				= 	"ar-technology-articles";
	xgemius_Detail["ar-programs"] 				= 	"dw.g687ZY1g.QrdXgEhS8pdRzbUITAMCpXYU0yMvoHL.R7"; //GENERIC (used by code)
	xgemius_Detail["ar-othernews"]				= 	"d2.g689ZY1hvjnzrYOmpNOVEjy0FShtqUUAQKcZ59mf.E7"; //GENERIC	(used by code. for type ARTICLE only)

	xgemius_Detail["alaswaq_interviews"]		=	"zapKrycJTykLWfQEru62T5cPzZUImALysu.XEQAs9Kr.W7";	
	xgemius_Detail["alaswaq_economy"]			=	"dvXqj89MPvDPnj5nuCmKa5bxzZUIjALyssldzKho9Kb.e7";	
	xgemius_Detail["alaswaq_companies"]			=	"dvZArc9Mc0dvmFcAKHm2MOUCj6MFlhtOUS0QhB40Hmv.q7";	
	xgemius_Detail["alaswaq_researches"]		=	"zangjScJYw9hfleArh.GMJevj6MI2BtOsqCabKgVSX3.I7";	
	xgemius_Detail["alaswaq_finance"]			=	"15Xq769yP3Fv5T4S0GyGRsWnj6M1lxtOihPXXcFDnZL.H7";	
	xgemius_Detail["alaswaq_islamic"]			=	"zapKrycJTylripQ3lgWm_Jev78wI2NuOstZguACLJk..v7";	
	xgemius_Detail["alaswaq_property"]			=	"d1Xqj87MPvFvwlRQqOZe.ZdR78wITNuOHA5gOrTlJpP.17";	
	xgemius_Detail["alaswaq_technology"]		=	"d2.gjc9ZYw8Fird3wQHSKpcPLb8ImMMyD3HKKzAREET.O7";	
	xgemius_Detail["alaswaq_opinions"]			=	"zU.giyb8Y9fxfH2UzrstyLesLd7YS8NC5WXK.aOOuGv.w7";	
	xgemius_Detail["tunisia"]					=	"pxZFBj9HmcWadzZWPa72BceC7O7YBCxLf6YOUyilRwr.N7";
	xgemius_Detail["egypt"]						=	".cpLoKbg9Dsg_RUu4ZqFNscQ7O7YPSxLFT8OUxuHRwr.f7";
	xgemius_Detail["libya"]						=	".cnlRKbgidXAXZYEeTOWuccQTKLYPeyntPG060uTZLT.E7";
	//xgemius_Detail["alaswaq_stock_ex"]			=	"ar-asw-other";	
	//xgemius_Detail["alaswaq_banks"]				=	"ar-asw-other";	
	



	//FOR TYPE VIEWS FORMAT: language + "-opinions-" + WebSection code. 
	//This List are not included in the category list but it will take the websection code if view is assigned to a websection
	xgemius_Detail["ar-opinions"]				= 	"1vXg667yY1hvO7dX0FnSesWnj8M1lxteVLxd_saq9Rv.x7"; //GENERIC
	xgemius_Detail["ar-opinions-Sports"] 		= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010"] 	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_groupa"]	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_groupb"]	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_groupc"]	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_groupd"]	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_schedual"]= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_scorers"]= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-angola2010_cards"]	= 	"15Xqka9yPyhvRPPLGATCvMVH7_w119ueKscn8UkmTWX.O7";
	xgemius_Detail["ar-opinions-programs"]= 	"zUBKsSb8T2A7T.QLPgs6rLesLd7YS8NCD8hkB9Bo0E..X7";		
	xgemius_Detail["ar-opinions-world"] 			= 	"d2BATc9Zc8c.0hzmyCiNI5evzSsI2ALmD4gUwDBYIJb.Y7"; //NA	(worldMena)
	xgemius_Detail["ar-opinions-meast"] 				= 	"d2BATc9Zc8c.0hzmyCiNI5evzSsI2ALmD4gUwDBYIJb.Y7"; //NA	(worldMena)
	xgemius_Detail["ar-opinions-culture_and_art"] 			= 	"1vXg7a7yY481NFbp6PnKZMWnzZU1lwLyikqasMD6IBX.87"; //NA
	xgemius_Detail["ar-opinions-medicine_health"] 			= 	"d2.qkc9ZPyifdj3w8KIOT5evLd8I2MNCpWLK.ctJOIH.f7";
	//xgemius_Detail["ar-opinions-technology"] 		= 	"ar-opinions-technology"; //NA
	xgemius_Detail["ar-opinions-alarabiyaWriters"] 	= 	"d2.q789ZP3EFKp3eeaIWjpev78wI2NuORcqtznsSTXX.s7";//NA	
	/******* END ARABIC ***********************************************/
	
	
	/*********** BEGIN DEFAULT VALUES **************************
	DEFAULT VALUES IF CATEGORY NOT FOUND ABOVE
	***********************************************************/
	xgemius_Default["ar"] = "dw.g687ZY1ifnFeLuLRCI5bx7_wIjNueRPMn8PtLpP7.e7";
	xgemius_Default["en"] = "d2BKsc9ZT2CfdpQ_cO8i7pbxzUsIjAL2D0UUnlfpS.7.a7";
	xgemius_Default["fa"] = "zUpKsSeJT2Cbt94DFoDuieVELVUFSsMmymNkcpku0CT.r7";
	xgemius_Default["ur"] = "d1Xg7c7MY48.HBzGSMEdQuVELd8FSsNCykLqRZjG0E..87";
/** END GEMIUS CONFIGURATION **/
