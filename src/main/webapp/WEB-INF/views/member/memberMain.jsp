<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home 화면</title>
</head>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {

        document.getElementById("memberLogout").addEventListener("click", function() {
            denialMemberRequest();
        });
    });

    function denialMemberRequest() {
        window.location.href = "/member/logOut.do"
    }
</script>
<body>
<h1>메인 페이지</h1>
<button type="button" id="memberLogout">로그아웃</button>
</body>
</html>