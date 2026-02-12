<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lekar-pocetna</title>

</head>
<body>
	<div
		style="display: flex; justify-content: space-between; align-items: center;">
		<h1>Dobrodošli, doktore!</h1>
		<form action="<c:url value='/logout'/>" method="post"
			style="display: inline;">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button type="submit">Odjavi se</button>
		</form>
	</div>

	<ul>
  <li>
    <c:url var="danasUrl" value="/lekar/pregledi/dan">
      <c:param name="dan" value="${danas}"/>
    </c:url>
    <a href="${danasUrl}">Termini za danas</a>
  </li>

  <li><a href="<c:url value='/lekar/termini'/>">Svi termini</a></li>
  <li><a href="<c:url value='/lekar/termini?samoAktivni=true'/>">Samo aktivni</a></li>
  <li>
    <form action="<c:url value='/lekar/pregledi/dan'/>" method="get">
      <input type="date" name="dan" value="${danas}" required />
      <button type="submit">Prikaži za dan</button>
    </form>
  </li>
  
</ul>
</body>
</html>