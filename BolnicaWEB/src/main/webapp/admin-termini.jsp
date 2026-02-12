<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Termini</title>
</head>
<body>

	<h2>Svi termini (filter)</h2>

	<form method="get" action="">
		Lekar ID: <input name="lekarId" value="${lekarId}">&nbsp; Od:
		<input type="datetime-local" name="od" value="${od}">&nbsp;
		Do: <input type="datetime-local" name="doo" value="${doo}">&nbsp;
		<button type="submit">Filtriraj</button>
	</form>

	<table border="1" cellpadding="6" style="margin-top: 10px;">
		<tr>
			<th>ID</th>
			<th>Lekar</th>
			<th>Pacijent</th>
			<th>Početak</th>
		</tr>
		<c:forEach items="${page.content}" var="t">
			<tr>
				<td>${t.id}</td>
				<td>${t.lekar.ime}${t.lekar.prezime}</td>
				<td><c:choose>
						<c:when test="${t.pacijent != null}">
              ${t.pacijent.ime} ${t.pacijent.prezime}
            </c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose></td>
				<td>${t.pocetak}</td>
			</tr>
		</c:forEach>
	</table>

	<p>Strana ${page.number + 1} od ${page.totalPages}</p>
	<c:if test="${!page.first}">
		<a
			href="?lekarId=${lekarId}&od=${od}&doo=${doo}&page=${page.number - 1}">«
			Prethodna</a>
	</c:if>
	<c:if test="${!page.last}">
		<a
			href="?lekarId=${lekarId}&od=${od}&doo=${doo}&page=${page.number + 1}">Sledeća
			»</a>
	</c:if>

	<p>
		<a href="<c:url value='/admin'/>">Nazad</a>
	</p>
</body>
</html>