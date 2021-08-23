/**
 * 
 */
$(document).ready(function() {
	var formObj = $("form[role='form']"); // form태그를 선택 - submit을 하기 위해
	$("input[type='submit']").on("click", function(e) { // submit타입을 가진 input태그를 클릭했을때
		e.preventDefault(); // 원래의 이벤트가 발생하는 것을 막는다
		console.log("submit clicked")
		
		var str="";
		$(".uploadResult ul li").each(function(i, obj) {
			var jobj = $(obj);
			console.dir(jobj);
			str+="<input type='text' name='attachList["+i+"].filename' value='"+jobj.data("filename")+"'>"; // data("속성")은 li 태그안의 data-속성명과 같아야 한다
			str+="<input type='text' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str+="<input type='text' name='attachList["+i+"].uploadpath' value='"+jobj.data("path")+"'>"; 
			str+="<input type='text' name='attachList["+i+"].filetype' value='"+jobj.data("filetype")+"'>";
		})
		// 결과확인을 위한 선언
		// $(".uploadResult ul li").html(str);
		formObj.append(str).submit();
	})
	$("input[type='file']").change(function(e) {
		// <form>과 같은 역할 
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		// console.log(files);
		for(var i=0;i<files.length;i++){
			// checkExtension함수 호출(파일종류, 파일크기)
			if(!checkExtension(files[i].name,files[i].size)){
				return false;
			}
			formData.append("uploadFile", files[i])
		}
		$.ajax({
			url:"/uploadAjaxAction",
			processData: false,
			contentType: false,
			data : formData,
			type : "post",
			dataType: "json",
			success : function(result) {console.log(result); ShowUploadResult(result);}
		});
	});
}); // $(document).ready(function(){})의 끝
	
function checkExtension(fileName, fileSize) {
	// 정규식, 해당확장자의 첨부를 막는다
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxsize = 5242880; // 5mb
	if(fileSize>=maxsize){
		alert("파일 사이즈 초과")
		return false;
	}
	if(regex.test(fileName)){
		alert("해당 종류의 파일은 업로드할 수 없습니다.")
		return false;
	}
	return true;
} // checkExtension end

function ShowUploadResult(uploadResultArr) {
	if(!uploadResultArr || uploadResultArr.length==0){return;}
	var uploadUL = $(".uploadResult ul");
	var str = "";
	
	$(uploadResultArr).each(function(i,obj) {
		// img 형태
		if(obj.filetype){
			var fileCallPath = encodeURIComponent(obj.uploadpath + "/s_" + obj.uuid + "_"+ obj.filename); // 파일의 경로를 받아오는 변수
			str+="<li data-path='"+obj.uploadpath+"'";
			str+=" data-uuid='"+obj.uuid+"' data-filename='"+obj.filename+"'data-filetype='"+obj.filetype+"'"+"><div>";
			str+="<span>"+obj.filename+"</span>";
			str+="<button type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i>"+obj.filename+"</button>";
			str+="<img src='/display?fileName="+fileCallPath+"'>";
			str+="</div></li>";
		} else{ // 아닌 경우
			var fileCallPath = encodeURIComponent(obj.uploadpath + "/" + obj.uuid + "_"+ obj.filename);
			var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
			str+="<li><div>";
			str+="<span>"+obj.filename+"</span>";
			str+="<button type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i>"+obj.filename+"</button>";
			str+="</li></div>";

		}
	})
	uploadUL.append(str);
} // ShowUploadResult end

