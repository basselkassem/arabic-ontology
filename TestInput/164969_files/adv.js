function writeadv(strSRC)
{
	strSRC = strSRC.replace(/ad.doubleclick.net/, "ad.sa.doubleclick.net");
	//document.write('<sc' + 'ript language="javascript" type="text/javascript" src="' + strSRC + '"><\/sc' + 'ript>'); 

    _size = /sz=(.*)x(.*);/.exec(strSRC);
    iFrameWidth = _size[1];
    iFrameHeight = _size[2];
	
    if( (iFrameWidth == '1' && iFrameHeight == '1' ) || (iFrameWidth == '728' && iFrameHeight == '90') || (iFrameWidth == '240' && iFrameHeight == '142') || (iFrameWidth == '300' && iFrameHeight == '250') )
	
    
    //if( (iFrameWidth == '1' && iFrameHeight == '1' ) || (iFrameWidth == '728' && iFrameHeight == '90') || (iFrameWidth == '240' && iFrameHeight == '142') )
    {
        document.write('<sc' + 'ript language="javascript" type="text/javascript" src="' + strSRC + '"><\/sc' + 'ript>');
    }
	else
    {
        strSRC = strSRC.replace(/\/adj\//, "/adi/");
        document.write('<iframe scrolling="no" marginwidth="0" frameborder="0" marginheight="0" style="border:0;padding:0;margin:0;width:'+iFrameWidth+'px;height:'+iFrameHeight+'px;" src="' + strSRC + '"></iframe>');
	  	//document.write('<iframe style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; BORDER-TOP-STYLE: none; PADDING-TOP: 0px; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: none" src="'+ strSRC +'" width="'+ iFrameWidth +'" height="'+ iFrameHeight +'" frameborder="0" scrolling="no"></iframe>');
    }
}



