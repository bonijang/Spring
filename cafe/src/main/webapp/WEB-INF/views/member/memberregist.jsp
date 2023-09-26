<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style type="text/css">
    div.grid {
        display: grid;
        grid-template-columns: 120px 1fr;
        grid-template-rows: 28px 28px 28px 28px 1fr;
        row-gap: 10px;
    }
    div.grid > div.btn-group {
        display: grid;
        grid-column: 1 / 3;
    }
    div.grid div.right-align{
        text-align: right;
    }
    label {
        padding-left: 10px;
    }
    button, input {
        padding: 10px;
    }
    div.errors {
        background-color: #ff00004a;
        opacity: 0.8;
        padding: 10px;
        color: #333;
    }
    div.errors:last-child {
        margin-bottom: 15px;
    }
</style>
</head>
<body>
    <h1>회원가입</h1>
    <form:form modelAttribute="memberVO" method="post">
        <div>
            <form:errors path="email" element="div" ccsClass="errors" />
            <form:errors path="name" element="div" ccsClass="errors" />
            <form:errors path="password" element="div" ccsClass="errors" />
            <form:errors path="confirmPassword" element="div" ccsClass="errors" />
        </div>
        <div class="grid">
            <label for="email">이메일</label>
            <input id="email" type="email" name="email" 
                   value="${memberVO.email}"/>

            <label for="name">이름</label>
            <input id="name" type="text" name="name" value="${memberVO.name}"/>

            <label for="password">비밀번호</label>
            <input id="password" type="password" name="password" 
                   value="${memberVO.password}"/>

            <label for="confirmPassword">비밀번호 확인</label>
            <input id="confirmPassword" type="password" name="confirmPassword" 
                   value="${memberVO.confirmPassword}"/>

            <div class="btn-group">
                <div class="right-align">
                    <input type="submit" value="등록" />
                </div>
            </div>
        </div>
    </form:form>
</body>
</html>