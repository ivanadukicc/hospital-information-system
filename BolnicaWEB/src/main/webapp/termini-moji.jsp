<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<fmt:setTimeZone value="Europe/Belgrade" />
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Moji termini</title>
</head>
<body>
	<p>
		<a
			href="<c:url value='/pacijent/termini/moji'>
             <c:param name='pacijentId' value='${pacijentId}'/>
             <c:param name='samoAktivni' value='${!samoAktivni}'/>
           </c:url>">
			<c:choose>
				<c:when test="${samoAktivni}">Prikaži sve</c:when>
				<c:otherwise>Prikaži samo aktivne</c:otherwise>
			</c:choose>
		</a>
	</p>

	<h2>Moji termini</h2>

	<c:if test="${not empty err}">
		<div style="color: red">${err}</div>
	</c:if>
	<c:if test="${not empty ok}">
		<div style="color: green">${ok}</div>
	</c:if>

	<c:choose>
		<c:when test="${empty termini}">
			<p>Trenutno nema zakazanih termina.</p>
		</c:when>
		<c:otherwise>
			<table border="1" cellpadding="6">
				<tr>
					<th>ID</th>
					<th>Lekar</th>
					<th>Početak</th>
					<th>Kraj</th>
					<th>Status</th>
					<th>Napomena</th>
					<th>Akcije</th>
				</tr>
				<c:forEach var="t" items="${termini}">
					<tr>
						<td>${t.id}</td>
						<td>${t.lekar.ime}${t.lekar.prezime}</td>
						<td><fmt:formatDate value="${t.pocetak}"
								pattern="dd.MM.yyyy HH:mm" /></td>
						<td><fmt:formatDate value="${t.kraj}"
								pattern="dd.MM.yyyy HH:mm" /></td>
						<td>${t.status}</td>
						<td>${t.napomena}</td>
						<td><c:choose>
								<c:when
									test="${t.status eq 'ZAKAZAN' && t.pocetak.time > now.time}">
									<form method="post"
										action="<c:url value='/pacijent/termin/${t.id}/otkazi'/>"
										style="display: inline">

										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<button type="submit"
											onclick="return confirm('Sigurno otkazati termin?')">Otkaži</button>
									</form>
								</c:when>
								<c:otherwise>
									<em>Otkazan ili prošao</em>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<p>
		<a href="<c:url value='/pacijent/lekari'/>">← Nazad na lekare</a>
		<br>
		<a href="<c:url value='/pacijent'/>">← Nazad na pocetnu</a>
	</p>
</body>
</html>