<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalji pregleda</title>
</head>
<body>
	<h2>Pregled #${pregled.id}</h2>

	<p>
		<strong>Lekar:</strong> ${pregled.termin.lekar.ime}
		${pregled.termin.lekar.prezime}<br/>
		<strong>Pacijent:</strong>
		${pregled.termin.pacijent.ime} ${pregled.termin.pacijent.prezime}<br/>
		<strong>Početak:</strong>
		<fmt:formatDate value="${pregled.termin.pocetak}"
			pattern="dd.MM.yyyy HH:mm" />
	</p>

	<h3>Recept</h3>

<c:choose>
  <c:when test="${not empty recept}">
    <p>Recept #${recept.id}, izdat: <fmt:formatDate value="${recept.izdatAt}" pattern="dd.MM.yyyy HH:mm"/></p>

    <c:if test="${not empty recept.receptLeks}">
      <ul>
        <c:forEach var="s" items="${recept.receptLeks}">
          <li>
            ${s.lek.naziv}
            — doza: ${s.doza},
            količina: ${s.kolicina},
            učestalost: ${s.ucestalost},
            trajanje: ${s.trajanje}
          </li>
        </c:forEach>
      </ul>
    </c:if>

    <form method="post" action="<c:url value='/lekar/pregled/${pregled.id}/recept'/>">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <label>Lek:</label>
      <select name="lekId" required>
        <c:forEach var="l" items="${lekovi}">
          <option value="${l.id}">${l.naziv}</option>
        </c:forEach>
      </select>
      <label>Doza:</label><input name="doza" required/>
      <label>Količina:</label><input type="number" name="kolicina" min="1" value="1" required/>
      <label>Učestalost:</label><input name="ucestalost" required/>
      <label>Trajanje:</label><input name="trajanje" required/>
      <button type="submit">Dodaj lek</button>
    </form>

  </c:when>

  <c:otherwise>
    <p>Još nije izdat recept.</p>
    <form method="post" action="<c:url value='/lekar/pregled/${pregled.id}/recept'/>">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <label>Lek:</label>
      <select name="lekId" required>
        <c:forEach var="l" items="${lekovi}">
          <option value="${l.id}">${l.naziv}</option>
        </c:forEach>
      </select>
      <label>Doza:</label><input name="doza" required/>
      <label>Količina:</label><input type="number" name="kolicina" min="1" value="1" required/>
      <label>Učestalost:</label><input name="ucestalost" required/>
      <label>Trajanje:</label><input name="trajanje" required/>
      <button type="submit">Izdaj recept</button>
    </form>
  </c:otherwise>
</c:choose>

<p><a href="<c:url value='/lekar/termini'/>">Nazad na termine</a></p>
</body>
</html>