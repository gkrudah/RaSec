<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
Current information of device
<br>
deviceID : ${device.deviceId}
<br>
camState : ${device.camState}
<br>
buzzerState : ${device.buzzerState}

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RaSec</title>
</head>
<body>
<FORM action="/RaSec/device" method="post" enctype='application/json'>
deviceID : <INPUT type= "text" name ="deviceId"><br>
camState :
    ON :<input type="radio" name="camState" value="true">
    OFF :<input type="radio" name="camState" value="false"><br>
buzzerState :
    ON :<input type="radio" name="buzzerState" value="true">
    OFF :<input type="radio" name="buzzerState" value="false">
<INPUT type = "submit" value = "요청">
</FORM>
</body>
</html>