<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novi recept</title>
</head>
<body>
	<c:if test="${not empty err}">
		<div style="color: red">${err}</div>
	</c:if>
	<c:if test="${not empty ok}">
		<div style="color: green">${ok}</div>
	</c:if>

	<form action="${pageContext.request.contextPath}/lekar/recept/izdaj" method="post">
  <input type="hidden" name="pregledId" value="${pregled.id}" />
  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 

  <label>Lek</label>
  <select name="lekId" required>
    <c:forEach items="${lekovi}" var="lek">
      <option value="${lek.id}">${lek.naziv}</option>
    </c:forEach>
  </select>

  <label>Doza</label>
  <input type="text" name="doza" required />

  <label>Količina</label>
  <input type="number" name="kolicina" min="1" />

  <label>Trajanje</label>
  <input type="text" name="trajanje" />

  <label>Učestalost</label>
  <input type="text" name="ucestalost" />

  <label>Napomena (opciono)</label>
  <input type="text" name="napomena" />

  <button type="submit">Izdaj recept</button>
</form>


</body>
</html>