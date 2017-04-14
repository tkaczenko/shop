<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1"/>
    <meta name="description" content="Интернет-магазин dj оборудования"/>
    <meta name="author" content="tkaczenko"/>
    <title>Интернет-магазин DJ</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>
    <div class="content">
        <h2>Ваш заказ добавлен ${orderId}</h2>

        <jsp:include page="_footer.jsp"/>
    </div>
</div>
</body>
</html>