<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novi lekar</title>
</head>
<body>

	<h2>Novi lekar</h2>

	<c:if test="${not empty ok}">
		<p style="color: green">${ok}</p>
	</c:if>
	<c:if test="${not empty err}">
		<p style="color: red">${err}</p>
	</c:if>

	<form method="post" action="<c:url value='/admin/lekari'/>">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

  Ime: <input name="ime" value="${lekarDTO.ime}" required><br>
  Prezime: <input name="prezime" value="${lekarDTO.prezime}" required><br>
  Specijalnost: <input name="specijalnost" value="${lekarDTO.specijalnost}" required><br>
  Username: <input name="username" value="${lekarDTO.username}" required><br>
  Email: <input type="email" name="email" value="${lekarDTO.email}" required><br>
  Lozinka: <input type="password" name="lozinka" required><br>

  <button type="submit">Sačuvaj</button>
</form>

	<p>
		<a href="<c:url value='/admin'/>">Nazad</a>
	</p>
</body>
</html>