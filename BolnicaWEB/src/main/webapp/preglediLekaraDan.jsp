<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Termini lekara</title>
</head>
<body>
	<h1>Termini lekara #${lekId} za dan ${dan}</h1>

	<c:if test="${not empty ok}">
		<p style="color: green;">
			<strong>${ok}</strong>
		</p>
	</c:if>
	<c:if test="${not empty err}">
		<p style="color: red;">
			<strong>${err}</strong>
		</p>
	</c:if>

	<h3>Zauzeti termini</h3>
	<jsp:useBean id="now" class="java.util.Date" />
	<table border="1">
		<tr>
			<th>Početak</th>
			<th>Kraj</th>
			<th>Pacijent</th>
		</tr>
		<c:forEach var="t" items="${termini}">
			<tr>
				<td><fmt:formatDate value="${t.pocetak}"
						pattern="yyyy-MM-dd HH:mm" /></td>
				<td><fmt:formatDate value="${t.kraj}"
						pattern="yyyy-MM-dd HH:mm" /></td>
				<td>${t.pacijent.ime}${t.pacijent.prezime}</td>
				<td><c:if test="${t.pocetak.time <= now.time}">
						<c:choose>
							<c:when test="${not empty t.pregled}">
								<a
									href="<c:url value='/lekar/recept/nov'>
                   <c:param name='pregledId' value='${t.pregled.id}'/>
                 </c:url>">
									Izdaj recept </a>
							</c:when>
							<c:otherwise>
								<a
									href="<c:url value='/lekar/pregled/nov'>
                   <c:param name='terminId' value='${t.id}'/>
                   <c:param name='next' value='recept'/>
                 </c:url>">
									Izdaj recept </a>
							</c:otherwise>
						</c:choose>
					</c:if></td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${empty termini}">
		<p>Nema zakazanih termina za ovaj dan.</p>
	</c:if>
	<hr />



	<p style="margin-top: 8px;">
		<a href="${pageContext.request.contextPath}/lekar">Nazad</a>
	</p>


</body>
</html>