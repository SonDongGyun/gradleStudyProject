<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>신규 글 등록</title>
</head>
<style type="text/css">
    table, thead, tbody, tfoot { border:1px solid #000000;border-collapse:collapse; }
    tfoot { text-align:right; }
    th, td { border:1px solid #000000;padding:10px; }
</style>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {

        document.getElementById("btnList").addEventListener("click", function() {
            history.back();
        });

        document.getElementById("btnRegistry").addEventListener("click", function() {
            submitBoardRegistry();
        });
    });

    function submitBoardRegistry() {

        if(document.getElementsByName("boardTitle")[0].value.replace(/\s/gi, "") == "") {
            alert("제목을 입력하지 않았습니다.\n제목을 입력해 주세요.");
            document.getElementsByName("exampleTitle")[0].focus();
            return false;
        }

        if(document.getElementsByName("boardDate")[0].value.replace(/\s/gi, "") == "") {
            alert("날짜를 입력하지 않았습니다.\n날짜를 입력해 주세요.");
            document.getElementsByName("boardDate")[0].focus();
            return false;
        }

        if(document.getElementsByName("boardContent")[0].value.replace(/\s/gi, "") == "") {
            alert("내용을 입력하지 않았습니다.\n내용을 입력해 주세요.");
            document.getElementsByName("boardContent")[0].focus();
            return false;
        }

        document.getElementById("formBoardWrite").method = "POST";
        document.getElementById("formBoardWrite").action = "./boardWriteInsert.do";
        document.getElementById("formBoardWrite").submit();
    }
</script>
<body>
<h3>신규 글 등록</h3>
<form id="formBoardWrite">
    <!-- 사용자 ID는 임시로 'user'를 고정으로 사용 -->
    <input type="hidden" name="writeId" value="user"/>
    <table>
        <tbody>
        <tr>
            <th>제목</th>
            <td>
                <input type="text" name="boardTitle" value=""/>
            </td>
        </tr>
        <tr>
            <th>날짜</th>
            <td>
                <input type="date" name="boardDate" value="" required/>
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td>
                <textarea name="boardContent"></textarea>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <button id="btnList" type="button">리스트</button>
                <button id="btnRegistry" type="button">글등록</button>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
</body>
</html>