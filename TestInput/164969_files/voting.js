/// <reference path="jquery-1.4.2.js" />
function submitVoting() {
    var selectedValue = jQuery("#votingTable :radio");
    if (selectedValue == undefined) {
        debugger;
    }
    jQuery("#votingDiv").hide()
    jQuery("#loaderDiv").show()
    //send selectedValue to server
    //end send to server 
    //retrve result like sample 

    //show Result
	
}

function showVoting()
{
	jQuery("#votingDiv").show();
    jQuery("#loaderDiv").hide();
	jQuery("#resultsDiv").hide();
	captchaReset('captcha_image');
	
}

function captchaReset(elem){
     document.getElementById(elem).src = '/pollcaptcha.php?' + Math.random();		
     return false
}


function doVote(mybtn)
{	
		if(isPreview()) //IF PREVIEW MODE
			url = "/data/web/poll_results.php";
		else
			url = "/poll_results.php";			
			
		
		//alert(url);
		
		document.frm_epolls.invoker.value = mybtn;
		
		//alert(document.frm_epolls.sec.value);
		
		//alert(document.frm_epolls.invoker.value);
		if(mybtn == "submitPollBtn")
		{
			var int_err = 0;		
			for (var i=0; i < document.frm_epolls.frm_opt.length; i++)
			{
			   if (document.frm_epolls.frm_opt[i].checked)
			   {
				   var answer = document.frm_epolls.frm_opt[i].value;
			   }
			}		
			if( answer == null )
			{
				int_err = 1;
				alert("Please choose an option to participate in the E-Poll");
				return false;
			}	
		}//end of if submitPollBtn
		
		jQuery("#loaderDiv").show()
		jQuery("#votingDiv").hide()
		jQuery("#resultsDiv").hide();
		
		var data = jQuery('#frm_epolls').serialize();
				
		jQuery.post(url, data, function(result) {
																								
			var selectedValue = jQuery("#votingTable :radio");
			if (selectedValue == undefined) {
				debugger;
			}
			//send selectedValue to server
			//end send to server 
			//retrve result like sample 
		
			//show Result
			showResult(result);
			//jQuery("#resultsDiv").html(result);
					
			jQuery("#resultsDiv").html(result); 
			
			jQuery("#loaderDiv").hide()
																								
																								
  			
		});


		
		

	return true;
} 


//sample of result
/*
C1 it mean the 1st choise
C2 it mean the 2nd choise

"20" it mean the 1st choise has 20%
"80" it mean the 2nd choise has 80%

*/
var result1 = [{ choise: "C1", persanteg: "20%" }, { choise: "C2", persanteg: "80%"}];

function showResult(result) {
//    var resultsDiv = jQuery("#resultsDiv");
//    for (var i = 0; i < result1.length; i++) {
//        resultsDiv.find("#" + result1[i].choise).css("width", result1[i].persanteg);
//    }
    jQuery("#resultsDiv").show()
}

function showResultOnly() {
    jQuery("#votingDiv").hide()
    jQuery("#resultsDiv").show()
}

