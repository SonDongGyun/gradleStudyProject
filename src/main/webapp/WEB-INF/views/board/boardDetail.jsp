<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${boardTitle}</title>
</head>
<style type="text/css">
    table, thead, tbody { border: 1px solid #000000;border-collapse:collapse; }
    th, td { border:1px solid #000000;padding:10px; }
    tfoot { text-align:right; }
</style>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("btnList").addEventListener("click", function() {
            history.back();
        });

        document.getElementById("btnErase").addEventListener("click", function() {
            if(confirm("해당 글을 삭제하시겠습니까?") == true) {
                window.location.href = "./boardDelete.do?seq=" + getParameter("seq");
            }
        });

        document.getElementById("btnRevise").addEventListener("click", function() {
            window.location.href = "./boardRevise.do?seq=" + getParameter("seq");
        });
    });

    var getParameter = function(param) {
        let returnValue;
        let url = location.href;
        let parameters = (url.slice(url.indexOf("?") + 1, url.length)).split("&");
        for(let i = 0; i < parameters.length; i++) {
            let varName = parameters[i].split("=")[0];
            if(varName.toUpperCase() == param.toUpperCase()) {
                returnValue = parameters[i].split("=")[1];
                return decodeURIComponent(returnValue);
            }
        }
    }
</script>
<body>
<table>
    <tbody>
    <tr>
        <th>제목</th>
        <td><c:out value="${boardTitle}"></c:out></td>
    </tr>
    <tr>
        <th>작성자</th>
        <td><c:out value="${writeId}"></c:out></td>
    </tr>
    <tr>
        <th>작성일</th>
        <td><fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
    </tr>
    <tr>
        <th>내용</th>
        <td><c:out value="${boardContent}"></c:out></td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="2">
            <button type="button" id="btnList">리스트</button>
            <button type="button" id="btnErase">글삭제</button>
            <button type="button" id="btnRevise">글수정</button>
        </td>
    </tr>
    </tfoot>
</table>
</body>
</html>