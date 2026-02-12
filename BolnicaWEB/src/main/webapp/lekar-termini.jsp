<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Termini lekara</title>
</head>
<body>

	<h2>Termini</h2>

	<c:if test="${empty termini}">
		<p>Nema termina.</p>
	</c:if>

	<c:if test="${not empty termini}">
		<table border="1" cellpadding="6">
			<tr>
				<th>Pacijent</th>
				<th>Početak</th>
				<th>Kraj</th>
				<th>Status</th>
				<th>Pregled</th>
			</tr>
			<c:forEach var="t" items="${termini}">
				<tr>
					<td>${t.pacijent.ime}${t.pacijent.prezime}</td>
					<td><fmt:formatDate value="${t.pocetak}"
							pattern="dd.MM.yyyy HH:mm" /></td>
					<td><fmt:formatDate value="${t.kraj}"
							pattern="dd.MM.yyyy HH:mm" /></td>
					<td>${t.status}</td>
					<td>
						<c:choose>
							<c:when test="${not empty t.pregled}">
								<a href="<c:url value='/lekar/pregled/${t.pregled.id}'/>">Otvori
									pregled</a>
							</c:when>

							<c:otherwise>
								<c:choose>
									<c:when test="${t.kraj.time <= now.time}">
										<c:url var="kreirajUrl" value="/lekar/pregled/nov">
											<c:param name="terminId" value="${t.id}" />
										</c:url>
										<a href="${kreirajUrl}">Dodaj pregled</a>
									</c:when>
									<c:otherwise>
										<span>Nema pregleda</span>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<p>
		<a href="<c:url value='/lekar'/>">Nazad</a>
	</p>
</body>
</html>
