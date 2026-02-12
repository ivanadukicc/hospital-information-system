<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novi pregled</title>
</head>
<body>
	<h2>Unos pregleda za termin #${termin.id}</h2>

	<form action="${pageContext.request.contextPath}/lekar/pregled/sacuvaj"
		method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
		<input type="hidden" name="terminId" value="${termin.id}" /> 
		<input type="hidden" name="next" value="${next}" />

		<label>Dijagnoza*</label><br /> <input type="text" name="dijagnoza"
			required /><br /> <br /> <label>Preporuke</label><br />
		<textarea name="preporuke" rows="3" cols="40"></textarea>
		<br /> <br />

		<button type="submit">Sačuvaj pregled</button>
	</form>

	<p>
		<a
			href="${pageContext.request.contextPath}/lekar/termini?lekarId=${termin.lekar.id}">Nazad</a>
	</p>
</body>
</html>