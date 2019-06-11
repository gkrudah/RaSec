<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

ModelAndView Photo 테스트페이지입니다.

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
<c:forEach var="photo" items="${results}">
    <tr>
        <a href=${photo}>
            <td>${photo}</td><br>
        </a>
    </tr>
</c:forEach>

</body>
</html>
