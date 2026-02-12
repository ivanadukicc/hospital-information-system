<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registracija pacijenta</title>
</head>
<body>
	<h2>Registracija korisnika</h2>

	<c:if test="${not empty err}">
		<p style="color: red">
			<strong>${err}</strong>
		</p>
	</c:if>

	<form method="post" action="<c:url value='/register'/>">

		<br><label>Korisničko ime</label><br>
		<input name="username" required minlength="3" maxlength="30" /><br>
		<br><label>Lozinka</label><br>
		<input type="password" name="password" required minlength="5" maxlength="72" /><br>
		<br><label>Potvrda lozinke</label><br> 
		<input type="password" name="confirmPassword" required /><br>
		<br><label>Ime</label><br>
		<input name="ime" required minlength="2" maxlength="50" /><br>
		<br><label>Prezime</label><br>
		<input name="prezime" required minlength="2" maxlength="50" /><br>
		<br><label>Adresa</label><br>
		<input name="adresa" required minlength="5" maxlength="100" /><br> 
		<br><label>Email</label><br>
		<input name="email" required minlength="5" maxlength="50" /><br> 
		<br><label>JMBG</label><br>
		<input name="jmbg" required /><br>
		<br><label>Telefon</label><br>
		<input name="telefon" required maxlength="20" /><br> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<br>
		<button type="submit">Registruj se</button>
	</form>

	<p>
		Već imaš nalog? <a href="<c:url value='/login'/>">Prijava</a>
	</p>
</body>
</html>