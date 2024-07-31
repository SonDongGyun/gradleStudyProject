<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>${exampleTitle}</title>
</head>
<style type="text/css">
    table, thead, tbody, tfoot { border:1px solid #000000;border-collapse:collapse; }
    tfoot { text-align:right; }
    th, td { border:1px solid #000000;padding:10px; }
    .fakeDisabled { cursor:default;background-color:#F8F8F8;color:#545454;border-color:#D6D6D6; }
</style>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("btnModify").addEventListener("click", function() {
            submitBoardModify();
        });
    });

    function submitBoardModify() {

        if(document.getElementsByName("boardTitle")[0].value.replace(/\s/gi, "") == "") {
            alert("제목을 입력하지 않았습니다.\n제목을 입력해 주세요.");
            document.getElementsByName("boardTitle")[0].focus();
            return false;
        }

        if(document.getElementsByName("boardDate")[0].value.replace(/\s/gi, "") == "") {
            alert("날짜를 입력하지 않았습니다.\n날짜를 입력해 주세요.");
            document.getElementsByName("boardDate")[0].focus();
            return false;
        }

        document.getElementById("formBoardRevise").method = "POST";
        document.getElementById("formBoardRevise").action = "./boardReviseUpdate.do";
        document.getElementById("formBoardRevise").submit();
    }
</script>
<body>
<h3>신규 글 등록</h3>
<form id="formBoardRevise">
    <input type="hidden" name="seq" value="${seq}"/>
    <table>
        <tbody>
        <tr>
            <th>ID</th>
            <td>
                <input type="text" class="fakeDisabled" name="writeId" value="<c:out value="${writeId}"></c:out>" readonly/>
            </td>
        </tr>
        <tr>
            <th>제목</th>
            <td>
                <input type="text" name="boardTitle" value="<c:out value="${boardTitle}"></c:out>"/>
            </td>
        </tr>
        <tr>
            <th>날짜</th>
            <td>
                <input type="date" name="boardDate" value="<fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td>
                <textarea name="boardContent"><c:out value="${boardContent}"></c:out></textarea>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <button id="btnModify" type="button">글수정</button>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
</body>
</html>