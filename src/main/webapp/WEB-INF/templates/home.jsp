<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.minhnbnt.shopmanager.models.User" %>

<%
	User user = (User) request.getAttribute("user");

	String redirectUrl = "/login.jsp";
	if (user != null) {
		redirectUrl = "/dashboard.jsp";
	}

	request.setAttribute("redirectUrl", request.getContextPath() + redirectUrl);
%>

<html lang="en">
	<head>
		<meta charset="UTF-8"/>
		<meta
			name="viewport"
			content="width=device-width, initial-scale=1.0"
		/>
		<meta http-equiv="refresh" content="2; URL=${redirectUrl}">

		<title>Shop Manager</title>

		<link
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/tailwind.css"
		/>
		<script src="${pageContext.request.contextPath}/resources/js/darkmode.js"></script>
	</head>

	<body class="flex relative h-full items-center justify-center">
		<button
			type="button"
			aria-label="Toggle dark mode"
			data-tooltip="Toggle dark mode"
			data-side="bottom" data-align="end"
			onclick="document.dispatchEvent(new CustomEvent('basecoat:theme'))"
			class="btn-icon-outline size-8 absolute top-5 right-5"
		>
			<span class="hidden dark:block">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24" height="24"
					viewBox="0 0 24 24" fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
				>
					<circle cx="12" cy="12" r="4"/>
					<path d="M12 2v2"/>
					<path d="M12 20v2"/>
					<path d="m4.93 4.93 1.41 1.41"/>
					<path d="m17.66 17.66 1.41 1.41"/>
					<path d="M2 12h2"/>
					<path d="M20 12h2"/>
					<path d="m6.34 17.66-1.41 1.41"/>
					<path d="m19.07 4.93-1.41 1.41"/>
				</svg>
			</span>

			<span class="block dark:hidden">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="24" height="24"
					viewBox="0 0 24 24" fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
				>
					<path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z"/>
				</svg>
			</span>
		</button>

		<div class="alert flex flex-col w-full md:w-1/2 m-20">
			<c:choose>
				<c:when test="${not empty user}">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						width="24" height="24"
						viewBox="0 0 24 24" fill="none"
						stroke="currentColor"
						stroke-width="2"
						stroke-linecap="round"
						stroke-linejoin="round"
					>
						<circle cx="12" cy="12" r="10"/>
						<path d="m9 12 2 2 4-4"/>
					</svg>
					<h2>You are logged in as user ${user.username}!</h2>
					<section>Will redirect you after 2 seconds.</section>
				</c:when>

				<c:otherwise>
					<h2>You are not logged in.</h2>
					<section>Will redirect you to login page after 2 seconds.</section>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>