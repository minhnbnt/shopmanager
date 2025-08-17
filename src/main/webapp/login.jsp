<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="en">
	<head>
		<meta charset="UTF-8"/>
		<meta
			name="viewport"
			content="width=device-width, initial-scale=1.0"
		/>
		<title>Shop Manager</title>

		<link
			rel="stylesheet"
			href="${pageContext.request.contextPath}/assets/css/tailwind.css"
		/>
	</head>

	<body>
		<div class="flex flex-col items-center">
			<form
				method="post"
				action="${pageContext.request.contextPath}/login"
				class="form grid gap-6 rounded-xl border max-w-[500px] p-5 m-10"
			>
				<div class="grid gap-2">
					<label for="username">Username</label>
					<input type="text" id="username" name="username" placeholder="minhnbnt" required/>
				</div>

				<div class="grid gap-2">
					<label for="password">Password</label>
					<input id="password" name="password" type="password" required minlength="6"/>
				</div>

				<button type="submit" class="btn">Submit</button>
			</form>
		</div>
	</body>
</html>
