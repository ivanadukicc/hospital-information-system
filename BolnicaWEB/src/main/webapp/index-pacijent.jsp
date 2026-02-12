<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pacijent-pocetna</title>
</head>
<body>
	 <header>
    <h1>Pacijent – Moji termini</h1>
    <div style="position: absolute; top: 10px; right: 10px;">
  <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <button type="submit">Odjavi se</button>
  </form>
</div>
  	</header>
	<h1>Dobrodosli, ${ime} ${prezime}!</h1>
	<ul>
		<li><a href="<c:url value='/pacijent/lekari'/>">Pregledaj
				lekare</a></li>
		<li><a href="<c:url value='/pacijent/termini/moji'/>">Moji
				termini</a></li>
		<li><a href="<c:url value='/pacijent/recepti'/>">Moji recepti</a></li>
		<li><a href="<c:url value='/pacijent/izabrani-lekar'/>">Izabrani lekar</a></li>
		
	</ul>
</body>
</html>