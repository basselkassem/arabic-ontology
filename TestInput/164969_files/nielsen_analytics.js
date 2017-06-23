//for debugging purposes (IE)
if(!window.console)  window.console = {log:function(data){  }  }; 
// initialization code (Native Mode) 

var _nolggGlobalParams = {
	clientid: "ae-802626",
	vcid: "b01",
	cisuffix: "",
	sfcode: "uk",
	prod: "sc"
};

var canUseSWF = false;
var uid = 0;
var gg1 = new gg();
gg1.ggInitialize(_nolggGlobalParams, uid, canUseSWF);
var trick = null;

//required for listeners / player
var firstTime = true;
var currentTime;


function timeListener(obj) {
	if (obj.position != null) {
		currentTime = Math.round(obj.position);
	}

}

function stateListener(obj) {

	var plst = null;
 
	plst = document.getElementById(player.id).getPlaylist();

	duration = Math.round(plst[0].duration);

	if (plst) {
		if (duration != 0) {
			if (obj.newstate == 'PLAYING' && firstTime) {
				clearTimeout(trick);
				var info = '<uurl>' + player.file + '</uurl>' + '<length>' + duration + '</length>';
				info += '<title>' + $('h3#AALightWindow_title').html() + '</title>';
				
				//Loaded and Play Event
				console.log('sending Load and play event: '+ info)
				gg1.ggPM(15, player.file, 'content',info);
				firstTime = false;
				}
			
				
		}
		else 
		{

	trick = setTimeout(function() {stateListener(obj)}, 100);
		}
		
	}
	//console.log('old: '+ obj.oldstate);
	//console.log('new: '+ obj.newstate);
	
	if (obj.newstate == "PLAYING" && obj.oldstate == "COMPLETED") {
		//send Play event
		console.log('sending Play after completion event) ');
			gg1.ggPM(5, Math.round(currentTime));
			 
		};

	if (obj.newstate == 'PAUSED' && obj.oldstate == 'PLAYING') {
		//PAUSED Event
		gg1.ggPM(6, currentTime);
		console.log('sending paused event at: ' + currentTime);
	}
	
	if (obj.newstate == 'PLAYING' && obj.oldstate == 'PAUSED') {
		//Play Event
		gg1.ggPM(5, currentTime);
		console.log('sending play event at: ' + currentTime);
	}
	
	
	if (obj.newstate == 'COMPLETED' && obj.oldstate == 'PLAYING') {
		console.log('sending stop event at: '+duration);
		gg1.ggPM(7, duration);
	}
}

function seekTracker(obj) {
	tempSeekto = Math.round(obj.position);
	console.log('cur: '+ currentTime);
	console.log('tempseekto: '+ tempSeekto);
	gg1.ggPM(8, Math.round(currentTime), tempSeekto);
	
};

$(document).ready(function() {
	$('a.AALightWindow_close_button').click(function() {
		console.log('Sending the stop event at: '+ currentTime);
		gg1.ggPM(7, currentTime);
	});
});

window.beforeunload = function() {
	
	gg1.ggPM(7, currentTime);
};




function addListeners() {
	if (player) {
		p = document.getElementById(player.id);
		p.addModelListener("STATE", "stateListener");
		p.addModelListener('TIME', 'timeListener');
		p.addViewListener("SEEK","seekTracker");

	} else {
		setTimeout("addListeners()", 100);
	}
}


function playerReady(thePlayer) {
	addListeners();
}



