<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Statistika</title>
</head>
<body>

	<h2>Statistika</h2>

	<form method="get" action="">
		Period: Od: <input type="datetime-local" name="od" value="${od}">
		Do: <input type="datetime-local" name="doo" value="${doo}">
		<button type="submit">Primeni</button>
	</form>

	<h3>Rang lekara (po broju termina u periodu)</h3>
	<table border="1" cellpadding="6">
		<tr>
			<th>Lekar</th>
			<th>Broj termina</th>
		</tr>
		<c:forEach items="${rang}" var="r">
			<tr>
				<td>${r[1]}${r[2]}</td>
				<td>${r[3]}</td>
			</tr>
		</c:forEach>
	</table>

	<h3 style="margin-top: 20px;">Termini po specijalnosti (mesečno)</h3>
	<table border="1" cellpadding="6">
		<tr>
			<th>Specijalnost</th>
			<th>Mesec (YYYY-MM)</th>
			<th>Broj</th>
		</tr>
		<c:forEach items="${spec}" var="s">
			<tr>
				<td>${s[0]}</td>
				<td>${s[1]}</td>
				<td>${s[2]}</td>
			</tr>
		</c:forEach>
	</table>

	<p>
		<a href="<c:url value='/admin'/>">Nazad</a>
	</p>
</body>
</html>