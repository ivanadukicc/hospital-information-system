<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.Lekar" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Spisak lekara</title>
</head>
<body>
<form method="get" action="${pageContext.request.contextPath}/pacijent/lekari">
  <input type="text" name="q" value="${q}" placeholder="Specijalnost...">
  <button type="submit">Traži</button>
</form>
    <h2>Spisak lekara</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Ime</th>
            <th>Prezime</th>
            <th>Specijalnost</th>
        </tr>
        <c:forEach var="l" items="${lekari}">
            <tr>
                <td>${l.id}</td>
                <td>${l.ime}</td>
                <td>${l.prezime}</td>
                <td>${l.specijalnost}</td>
                <td><a href="${pageContext.request.contextPath}/pacijent/lekari/${l.id}">Detalji</a></td>
            </tr>
        </c:forEach>
    </table>
    <p>
    <a href="<c:url value='/pacijent'/>">Nazad na pocetnu</a>
    </p>
</body>
</html>
