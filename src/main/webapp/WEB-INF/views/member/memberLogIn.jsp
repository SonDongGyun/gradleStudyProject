<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 로그인</title>
</head>
<style type="text/css">
    table, thead, tbody, tfoot { border:1px solid #000000;border-collapse:collapse; }
    tfoot { text-align:right; }
    th, td { border:1px solid #000000;padding:10px; }
    tbody > tr > td { cursor:pointer;cursor:hand; }
    tbody > tr > td:first-child { text-align:center; }
    button { cursor:pointer;cursor:hand; }
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {

        document.addEventListener("keydown", function(event) {
            if (event.keyCode === 13) { // Enter 키의 키 코드는 13입니다.
                accessMemberRequest();
            }
        });

        document.querySelector("#memberSingUp").addEventListener("click", function() {
            window.location.href = "/member/singUp.do";
        });

        document.querySelector("#memberLogin").addEventListener("click", function() {
            accessMemberRequest();
        });
    });

    function accessMemberRequest() {

        if(document.querySelector("#memberId").value.replace(/\s/gi, "") === "") {
            alert("ID가 입력되지 않았습니다.\nID를 입력해 주세요.");
            document.querySelector("#memberId").value = "";
            document.querySelector("#memberId").focus();
            return false;
        }

        if(document.querySelector("#memberPw").value.replace(/\s/gi, "") === "") {
            alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해 주세요.");
            document.querySelector("#memberPw").value = "";
            document.querySelector("#memberPw").focus();
            return false;
        }

        document.querySelector("#formMemberAccess").method = "POST";
        document.querySelector("#formMemberAccess").action = "/member/authenticationProcess.do";
        document.querySelector("#formMemberAccess").submit();

    }
</script>
<body>
<h1>회원 로그인</h1>
<form id="formMemberAccess">
    <table>
        <tbody>
        <tr>
            <th>사용자 ID</th>
            <td><input type="text" id="memberId" name="memberId" value="" required/></td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td><input type="password" id="memberPw" name="memberPw" value="" required/></td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <button type="button" id="memberSingUp">회원가입</button>
                &nbsp;|&nbsp;
                <button type="button" id="memberLogin">로그인</button>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
</body>
</html>