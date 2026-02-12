<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prijava</title>
</head>
<body>
	<h2>Prijava</h2>

	<c:if test="${param.error ne null}">
		<p style="color: red;">Neispravan username ili lozinka.</p>
	</c:if>
	<c:if test="${param.logout ne null}">
		<p style="color: green;">Uspešno ste se odjavili.</p>
	</c:if>
	<c:if test="${param.registered ne null}">
		<p style="color: green">Registracija uspešna. Uloguj se.</p>
	</c:if>
	<form method="post" action="<c:url value='/login'/>">
		<label>Korisničko ime</label> <input type="text" name="username"
			required /> <label>Lozinka</label> <input type="password"
			name="password" required /> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}" />

		<button type="submit">Uloguj se</button>
		<p style="margin-top: 12px">
			Nemate nalog? <a href="<c:url value='/register'/>">Registrujte se</a>
		</p>
		<c:if test="${param.error ne null}">
			<div style="color: red">Pogrešno korisničko ime ili lozinka.</div>
		</c:if>
	</form>

</body>
</html>