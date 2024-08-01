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
        document.getElementById("boardList").addEventListener("click", function() {
            denialMemberRequest2();
        });
    });

    function denialMemberRequest2() {
        window.location.href = "/board/boardList.do"
    }
    function denialMemberRequest() {
        window.location.href = "/member/login.do?logout"
    }
</script>
<body>
<h1>메인 페이지</h1>
<button type="button" id="boardList">게시판</button>
<button type="button" id="memberLogout">로그아웃</button>
</body>
</html>