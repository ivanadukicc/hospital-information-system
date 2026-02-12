<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Moji recepti</title>
</head>
<body>
	<h1>Moji recepti</h1>

	<c:choose>
		<c:when test="${empty recepti}">
			<p>Nemate izdatih recepata.</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="r" items="${recepti}">
				<div
					style="border: 1px solid #ccc; padding: 10px; margin-bottom: 12px;">
					<h3>
						Recept #${r.id} — izdat:
						<fmt:formatDate value="${r.izdatAt}" pattern="dd.MM.yyyy HH:mm" />
					</h3>
					<p>Lekar: ${r.pregled.termin.lekar.ime}
						${r.pregled.termin.lekar.prezime}</p>

					<table border="1">
						<tr>
							<th>Lek</th>
							<th>Doza</th>
							<th>Količina</th>
							<th>Učestalost</th>
							<th>Trajanje</th>
						</tr>
						<c:forEach var="rl" items="${r.receptLeks}">
							<tr>
								<td>${rl.lek.naziv}</td>
								<td>${rl.doza}</td>
								<td>${rl.kolicina}</td>
								<td>${rl.ucestalost}</td>
								<td>${rl.trajanje}</td>
							</tr>
						</c:forEach>
					</table>
					<c:if test="${not empty r.napomena}">
						<p>
							<strong>Napomena:</strong> ${r.napomena}
						</p>
					</c:if>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<p>
		<a href="<c:url value='/pacijent'/>">Nazad na početnu</a>
	</p>
</body>
</html>