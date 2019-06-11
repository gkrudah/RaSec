<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String camState = request.getParameter("camState");

        out.println("camState: " + camState + "<br />");

%>

