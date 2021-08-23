/**
 * 
 */
$(document).ready(function() {
	var bno = $('#bno').val();
	// alert(bno);
	$.getJSON("getAttachList", {bno:bno}, function (arr) {
		// console.log(arr);
		var str="";
		$(arr).each(function(i, attach) {
			// image type
			if(attach.filetype){
				var fileCallPath = encodeURIComponent(attach.uploadpath + "/s_" + attach.uuid + "_"+ attach.filename); 
				str +="<li data-uploadpath='"+attach.uploadpath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.filename+"' data-filetype='"+attach.filetype+"'><div>";
				str +="<img src='/display?fileName="+fileCallPath+"'>";
				str +="</div></li>";
			}else{
				str +="<li data-uploadpath='"+attach.uploadpath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.filename+"' data-filetype='"+attach.filetype+"'><div>";
				str +="<span> "+attach.filename+"</span><br>";
				str +="<img src='/display?fileName="+fileCallPath+"'>";
				str +="</div></li>";
			}
		}) // each함수의 끝
		$(".uploadResult ul").html(str);
	})
})