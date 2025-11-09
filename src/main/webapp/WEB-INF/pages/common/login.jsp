<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--suppress HtmlUnknownTarget -->
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8"/>
		<meta
			name="viewport"
			content="width=device-width, initial-scale=1.0"
		/>
		<title>Login - Shop Manager</title>

		<link
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/tailwind.css"
		/>
		<script src="${pageContext.request.contextPath}/resources/js/darkmode.js"></script>
	</head>

	<body class="flex items-center min-h-screen justify-center relative">
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

		<div class="card flex flex-col w-full md:w-1/2 m-20">
			<header>
				<h2>Login to your account</h2>
				<p>Enter your details below to login to your account</p>
			</header>

			<section>
				<form
					class="form grid gap-6"
					method="post"
					action="${pageContext.request.contextPath}/login"
				>
					<div class="grid gap-2">
						<label for="form-username">Username</label>
						<input
							id="form-username"
							name="username"
							type="text"
							placeholder="minhnbnt"
							required
						/>
					</div>

					<div class="grid gap-2">
						<label for="form-password">Password</label>
						<input
							id="form-password"
							name="password"
							type="password"
							required
							minlength="6"
						/>
					</div>

					<c:if test="${not empty errorMessage}">
						<div class="alert-destructive">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="24" height="24" viewBox="0 0 24 24"
								fill="none" stroke="currentColor" stroke-width="2"
								stroke-linecap="round" stroke-linejoin="round"
							>
								<circle cx="12" cy="12" r="10"/>
								<line x1="12" x2="12" y1="8" y2="12"/>
								<line x1="12" x2="12.01" y1="16" y2="16"/>
							</svg>

							<h2>Login error!</h2>
							<section class="max-h-[200px] w-full overflow-scroll">${errorMessage}</section>
						</div>
					</c:if>

					<footer class="flex flex-col gap-2">
						<button type="submit" class="btn w-full">Login</button>
						<p class="mt-4 text-center text-sm">
							Don't have an account?
							<a
								href="${pageContext.request.contextPath}/signup.jsp"
								class="underline-offset-4 hover:underline"
							>
								Sign up
							</a>
						</p>
					</footer>
				</form>
			</section>
		</div>
	</body>
</html>
