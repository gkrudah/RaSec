<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

ModelAndView videos 테스트페이지입니다.

<br>
${results.size()}
<br>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RaSec</title>
</head>
<body>
<c:forEach var="video" items="${results}">
    <tr>
        <a href=${video}>
            <td>${video}</td><br>
        </a>
    </tr>
</c:forEach>

</body>
</html>
