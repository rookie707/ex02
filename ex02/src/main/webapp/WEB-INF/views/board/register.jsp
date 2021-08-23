<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="../resources/js/register.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        form{
            width: 700px;
            height: auto;
            border: 1px solid #ccc;
            margin: 0 auto;
        }
        td{
            padding: 5px;
        }
        h3{
            margin: 5px auto;
        }
    </style>
</head>
<body>
<form role="form" action="register" method="post">
    <table>
        <tr><td colspan="2" ><h3 style="text-align: center;">글쓰기</h3></td></tr>
        <tr><td style="width: 70px;">작성자</td><td style="width: 600px;"><input type="text" name="writer"></td></tr>
        <tr><td style="width: 70px;">제목</td><td><input type="text" name="title" style="width: 400px;"></td></tr>
        <tr><td style="width: 70px;">내용</td><td><textarea name="content" id="" cols="70" rows="25"    ></textarea></td></tr>
        <tr >
	        <td colspan="2" style="width: 100px; padding: 30px 0 10px 0; text-align: center;">
	        <input type="submit" value='작성'>
	        </td>
        </tr>
    </table>
    <div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	<div class="uploadResult">
		<ul>
		</ul>
	</div>
</form>
</body>
</html>