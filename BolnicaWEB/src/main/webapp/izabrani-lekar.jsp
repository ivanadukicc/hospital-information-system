<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Izabrani lekar</title>
</head>
<body>
	<h2>Izabrani lekar</h2>

	<c:if test="${not empty ok}">
		<p style="color: green">
			<strong>${ok}</strong>
		</p>
	</c:if>
	<c:if test="${not empty err}">
		<p style="color: red">
			<strong>${err}</strong>
		</p>
	</c:if>

	<c:choose>
		<c:when test="${not empty izabrani}">
			<p>
				Lekar: <strong>${izabrani.lekar.ime}
					${izabrani.lekar.prezime}</strong> (<em>${izabrani.lekar.specijalnost}</em>)
			</p>
			<form method="post"
				action="<c:url value='/pacijent/izabrani-lekar/ukloni'/>">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button type="submit">Ukloni izabranog lekara</button>
			</form>
		</c:when>
		<c:otherwise>
			<p>Nemate izabranog lekara.</p>
			<p>
				<a href="<c:url value='/pacijent/lekari'/>">→ Pronađi lekara</a>
			</p>
		</c:otherwise>
	</c:choose>

	<p>
		<a href="<c:url value='/pacijent'/>">← Nazad</a>
	</p>

</body>
</html>