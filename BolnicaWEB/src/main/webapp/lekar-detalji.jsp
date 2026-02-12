<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalji lekara</title>
</head>
<body>

	<h2>${lekar.ime}${lekar.prezime}</h2>
	<p>Specijalnost: ${lekar.specijalnost}</p>
	<a href="${pageContext.request.contextPath}/pacijent/termin/nov?lekarId=${lekar.id}">Zakaži
	termin</a>
	<br>
	<c:if test="${not empty lekar && lekar.specijalnost != null && lekar.specijalnost eq 'Opšta medicina'}">
  <form method="post" action="<c:url value='/pacijent/izabrani-lekar'/>" style="display:inline;">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="lekarId" value="${lekar.id}"/>
    <button type="submit">Postavi za izabranog lekara</button>
  </form>
</c:if>
	<br>
	<a href="${pageContext.request.contextPath}/pacijent/lekari">Nazad na listu</a>

</body>
</html>