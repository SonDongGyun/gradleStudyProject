<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<style type="text/css">
    table, thead, tbody, tfoot { border:1px solid #000000;border-collapse:collapse; }
    tfoot { text-align:right; }
    th, td { border:1px solid #000000;padding:10px; }
    tbody > tr > td { cursor:pointer;cursor:hand; }
    tbody > tr > td:first-child { text-align:center; }
    button { cursor:pointer;cursor:hand; }
    textarea { width:350px;height:150px;resize:none; }
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {

        document.addEventListener("keydown", function(event) {
            if(event.keyCode === 13) { // Enter 키의 키 코드는 13입니다.
                singUpMemberRequest();
            }
        });

        document.querySelector("#btnDuplicateId").addEventListener("click", function() {
            duplicateIdValidation();
        });

        document.querySelector("#memberPhone").addEventListener("keyup", function(event) {
            inputPhoneNumber(event.target);
        });

        document.querySelector("#memberLogIn").addEventListener("click", function() {
            window.location.href = "/login.do";
        });

        document.querySelector("#memberSingUp").addEventListener("click", function() {
            singUpMemberRequest();
        });
    });

    // 회원 신청 아이디 중복 확인
    function duplicateIdValidation() {

        if(document.querySelector("#memberId").value.replace(/\s/gi, "") === "") {
            alert("ID가 입력되지 않았습니다.\nID를 입력해 주세요.");
            document.querySelector("#memberId").focus();
            return false;
        }

        if(validateId(document.querySelector("#memberId").value) === false) {
            alert("ID는 영문으로 시작하는 영문 소문자, 숫자, 특수문자(_, -, .)로 이루어진\n6자 이상 20자 이상의 문자여야 합니다.");
            document.querySelector("#memberId").value = "";
            document.querySelector("#memberId").focus();
            return false;
        }

        jQuery.ajax({
            url : "/member/duplicateId.do"
            , type : "POST"
            , async : false
            , dataType : "json"
            , data : { memberId : document.querySelector("#memberId").value.replace(/\s/gi, "") }
            , success : function(response) {
                alert(response.message);
                if(response.result === "failure") {
                    document.querySelector("#memberId").value = "";
                    document.querySelector("#memberId").focus();
                    return false;
                }
            }
        });
    }

    // 회원가입
    function singUpMemberRequest() {

        if(document.querySelector("#memberId").value.replace(/\s/gi, "") === "") {
            alert("ID가 입력되지 않았습니다.\nID를 입력해 주세요.");
            document.querySelector("#memberId").focus();
            return false;
        }

        if(validateId(document.querySelector("#memberId").value) === false) {
            alert("ID는 영문으로 시작하는 영문 소문자, 숫자, 특수문자(_, -, .)로 이루어진\n6자 이상 20자 이상의 문자여야 합니다.");
            document.querySelector("#memberId").value = "";
            document.querySelector("#memberId").focus();
            return false;
        }

        if(document.querySelector("#memberPw").value.replace(/\s/gi, "") === "") {
            alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해 주세요.");
            document.querySelector("#memberPw").focus();
            return false;
        }

        if(document.querySelector("#confirmPw").value.replace(/\s/gi, "") === "") {
            alert("비밀번호 확인이 입력되지 않았습니다.\n비밀번호 확인을 입력해 주세요.");
            document.querySelector("#confirmPw").focus();
            return false;
        }

        if(document.querySelector("#memberPw").value !== document.getElementById("confirmPw").value) {
            alert("비밀번호가 일치하지 않았습니다.\n입력한 비밀번호를 다시 확인해 주세요.");
            document.querySelector("#memberPw").value = "";
            document.querySelector("#confirmPw").value = "";
            document.querySelector("#memberPw").focus();
            return false;
        }
        if(validatePassword(document.querySelector("#memberPw").value) === false) {
            alert("비밀번호는 8자 이상, 20자 이하의\n대문자, 소문자, 숫자, 특수문자를 혼합한\n비밀번호만 사용이 가능합니다.");
            document.getElementById("memberPw").value = "";
            document.getElementById("confirmPw").value = "";
            document.getElementById("memberPw").focus();
            return false;
        }

        if(document.querySelector("#memberPhone").value.replace(/\s/gi, "") === "") {
            alert("휴대폰 번호가 입력되지 않았습니다.\n휴대폰 번호를 입력해 주세요.");
            document.querySelector("#memberEmail").focus();
            return false;
        }

        if(validatePhoneNumber(document.querySelector("#memberPhone").value) === false) {
            alert("휴대폰 번호 형식이 올바르지 않습니다.\n정확한 휴대폰 번호를 입력해 주세요.");
            document.querySelector("#memberPhone").value = "";
            document.querySelector("#memberPhone").focus();
            return false;
        }

        if(document.querySelector("#memberEmail").value.replace(/\s/gi, "") === "") {
            alert("Email 주소가 입력되지 않았습니다.\nEmail 주소를 입력해 주세요.");
            document.querySelector("#memberEmail").focus();
            return false;
        }

        if(validateEmail(document.querySelector("#memberEmail").value) === false) {
            alert("Email 형식이 올바르지 않습니다.\n정확한 Email 주소를 입력해 주세요.");
            document.querySelector("#memberEmail").value = "";
            document.querySelector("#memberEmail").focus();
            return false;
        }

        document.querySelector("#formMemberSingUp").method = "POST";
        document.querySelector("#formMemberSingUp").action = "/member/memberRegistry.do";
        document.querySelector("#formMemberSingUp").submit();
    }

    function inputPhoneNumber(phone) {
        if(event.keyCode !== 8) {
            const regExp = new RegExp(/^[0-9]{2,3}-^[0-9]{3,4}-^[0-9]{4}/g);
            if(phone.value.replace( regExp, "").length !== 0) {
                if(checkPhoneNumber( phone.value ) === true) {
                    let number = phone.value.replace(/[^0-9]/g, "");
                    let tel = "";
                    let seoul = 0;
                    if(number.substring(0, 2).indexOf("02") === 0) {
                        seoul = 1;
                        phone.setAttribute("maxlength", "12");
                    } else {
                        phone.setAttribute("maxlength", "13");
                    }
                    if(number.length < (4 - seoul)) {
                        return number;
                    } else if(number.length < (7 - seoul)) {
                        tel += number.substr(0, (3 - seoul) );
                        tel += "-";
                        tel += number.substr(3 - seoul);
                    } else if(number.length < (11 - seoul)) {
                        tel += number.substr(0, (3 - seoul));
                        tel += "-";
                        tel += number.substr((3 - seoul), 3);
                        tel += "-";
                        tel += number.substr(6 - seoul);
                    } else {
                        tel += number.substr(0, (3 - seoul));
                        tel += "-";
                        tel += number.substr(( 3 - seoul), 4);
                        tel += "-";
                        tel += number.substr(7 - seoul);
                    }
                    phone.value = tel;
                } else {
                    const regExp = new RegExp(/[^0-9|^-]*$/);
                    phone.value = phone.value.replace(regExp, "");
                }
            }
        }
    }

    function checkPhoneNumber(number) {
        const regExp = new RegExp(/^[0-9|-]*$/);
        if( regExp.test( number ) === true ) { return true; }
        else { return false; }
    }

    // ID 형식 검사
    function validateId(id) {

        // 영문 소문자로 시작하고, 영문 소문자, 숫자, 특수문자(_, -, .)로 이루어진 6자 이상 20자 이하의 ID 형식을 검사하는 정규 표현식
        const idPattern = /^[a-z][a-z0-9_.-]{6,19}$/;
        return idPattern.test(id);
    }

    // 비밀번호 형식 검사
    function validatePassword(password) {

        // 비밀번호 길이 체크
        if(password.length < 8 || password.length > 20) {
            return false;
        }

        // 대문자, 소문자, 숫자, 특수문자 포함 여부 체크
        const uppercaseRegex = /[A-Z]/;
        const lowercaseRegex = /[a-z]/;
        const digitRegex = /[0-9]/;
        const specialCharRegex = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g

        // 비밀번호 형식 검사
        if(uppercaseRegex.test(password) == false || lowercaseRegex.test(password) == false || digitRegex.test(password) == false || specialCharRegex.test(password) == false) {
            return false;
        }

        // 모든 요구사항 충족
        return true;
    }

    // 휴대폰 번호 형식 검사
    function validatePhoneNumber(phoneNumber) {

        // 유효한 휴대폰 번호 패턴 정의 (01?-0000-0000, 가운데 번호는 3자리 또는 4자리)
        const pattern = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;

        // 정규식과 매치되는지 확인하여 유효성 검사 결과 반환
        return pattern.test(phoneNumber);
    }

    // 이메일 주소 형식 검사
    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
</script>
<body>
<h1>회원 로그인</h1>
<form id="formMemberSingUp">
    <input type="hidden" id="validationId" value="">
    <table>
        <tbody>
        <tr>
            <th>사용자 ID</th>
            <td>
                <input type="text" id="memberId" name="memberId" value="" required/>
                <button type="button" id="btnDuplicateId">ID 중복확인</button>
            </td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td>
                <input type="password" id="memberPw" name="memberPw" value="" required/>
            </td>
        </tr>
        <tr>
            <th>비밀번호 확인</th>
            <td>
                <input type="password" id="confirmPw" value=""/>
            </td>
        </tr>
        <tr>
            <th>이름</th>
            <td>
                <input type="text" id="memberName" name="memberName" value="" required/>
            </td>
        </tr>
        <tr>
            <th>휴대폰 번호</th>
            <td>
                <input type="text" id="memberPhone" name="memberPhone" value="" style="text-align:center;" maxlength="13" placeholder="000-0000-00000" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}"/>
            </td>
        </tr>
        <tr>
            <th>Email 주소</th>
            <td>
                <input type="text" id="memberEmail" name="memberEmail" value="" required/>
            </td>
        </tr>
        <tr>
            <th>기타사항</th>
            <td>
                <textarea name="memberOtherMatters" rows="4" cols="50"></textarea>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">
                <button type="button" id="memberLogIn">로그인</button>
                &nbsp;|&nbsp;
                <button type="button" id="memberSingUp">회원가입</button>
            </td>
        </tr>
        </tfoot>
    </table>
</form>
</body>
</html>