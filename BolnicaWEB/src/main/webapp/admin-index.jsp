<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-pocetna</title>
</head>
<body>

	<h2>Admin dashboard</h2>

	<form action="<c:url value='/logout'/>" method="post"
		style="display: inline;">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<button type="submit">Odjavi se</button>
	</form>
	<div>
		<strong>Termini danas:</strong> ${danasBroj}
	</div>

	<h3>Top lekari (poslednjih 7 dana)</h3>
	<table border="1" cellpadding="6">
		<tr>
			<th>Lekar</th>
			<th>Broj termina</th>
		</tr>
		<c:forEach items="${top7}" var="l">
			<tr>
				<td>${l.ime}</td>
				<td>${l.prezime}</td>
				<td>${l.brojTermina}</td>
			</tr>
		</c:forEach>
	</table>

	<h3>Rang lekara (PDF)</h3>
	<form action="<c:url value='/admin/getRangLekara.pdf'/>" method="get"
		style="margin: 12px 0;">
		<label>Od: <input type="date" name="od" value="${od}" />
		</label> <label style="margin-left: 10px;">Do: <input type="date"
			name="doo" value="${doo}" />
		</label>
		<button type="submit" style="margin-left: 10px;">Preuzmi PDF</button>
	</form>
	<p>
		<a href="<c:url value='/admin/lekari/nov'/>">Dodaj lekara</a> | <a
			href="<c:url value='/admin/termini'/>">Svi termini</a> | <a
			href="<c:url value='/admin/getSpisakPacijenata.pdf'/>"> Spisak
			pacijenata- izvestaj</a> | <a
			href="<c:url value='/admin/getSpecMesecStat.pdf'/>"> Statistika termina po specijalizaciji(mesecno) - izvestaj </a>
	</p>
</body>
</html>