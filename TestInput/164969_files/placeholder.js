function initPlaceholder(pageOpts) {
	if (pageOpts === undefined) {
		host = "/";
		section = "";
		lang = "";
		type = "";
	} else {
		host = pageOpts.webRootPath;
		section = pageOpts.contentSection;
		lang = pageOpts.lang;
		type = pageOpts.contentType;
	}
	$.ajax({
		type: "GET",
		url: host+"placeholder.xml",
		dataType: "xml",
		success: function (xml) {
			i = 0;
			$(xml).find("placeholder").each(function() {
				ph = $("#"+$(this).attr("id"));
				$(this).find("item").each(function () {
					sections = $(this).attr("section").split(",");
					langs = $(this).attr("lang").split(",");
					types = $(this).attr("type").split(",");
					if (($.inArray(section, sections) > -1 || sections[0]=="*") && ($.inArray(lang, langs) > -1 || langs[0]=="*") && ($.inArray(type, types) > -1 || types[0]=="*")) {
						div = '<div class="placeholder" id="ph_'+ ++i +'">';
						div += $(this).text();
						div += '</div>';
						ph.append(div);
					}
				});
			});
		}
	});
}
