<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Zakazivanje termina</title>
</head>
<body>

<h2>Zakazivanje termina kod: ${lekar.ime} ${lekar.prezime}</h2>

<c:if test="${not empty err}">
  <div style="color:red">${err}</div>
</c:if>

<c:url var="akcijaZakazivanja" value="/pacijent/termin"/>
<form method="post" action="<c:url value='/pacijent/termin'/>">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="lekarId" value="${lekar.id}" />
  
  <label>Početak:</label>
  <input type="datetime-local" name="pocetak" required /><br/><br/>
  <small>Dozvoljeno zakazivanje: pon–pet, 08:00–17:00.</small><br><br>

  <label>Trajanje (min):</label>
  <input type="number" name="trajanjeMin" value="30" min="5" step="5" /><br/><br/>

  <label>Napomena:</label>
  <input type="text" name="napomena" /><br/><br/>

  <button type="submit">Zakaži</button>
</form>
<c:url var="moji" value="/pacijent/termini/moji">
  <c:param name="pacijentId" value="${pacijentId}"/>
</c:url>
<a href="<c:url value='/pacijent/termini/moji'/>">Moji termini</a>
<p><a href="<c:url value='/pacijent/lekari'/>">Nazad na listu lekara</a></p>
</body>
</html>