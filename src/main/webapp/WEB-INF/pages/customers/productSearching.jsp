<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--suppress HtmlUnknownTarget -->
<html lang="en">
	<head>
		<meta charset="UTF-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<title>Product Search</title>
		<link
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/tailwind.css"
		/>
		<script src="${pageContext.request.contextPath}/resources/js/darkmode.js"></script>
	</head>

	<body class="flex min-h-screen items-center justify-center">
		<div class="m-10 flex w-[800px] flex-col-reverse gap-5">
			<div class="card w-full">
				<header class="flex flex-col">
					<h2>Product Search</h2>
					<p>Please enter the keyword and choose the product.</p>
				</header>

				<section>
					<form
						class="flex items-end space-x-5" method="get"
						action="<%= request.getContextPath() %>/customer/productSearching"
					>
						<div class="flex grow flex-col gap-3">
							<label for="keyword" class="label">Keyword</label>
							<input
								class="input" type="text" name="keyword" id="keyword"
								value="${keyword}"
							/>
						</div>
						<button type="submit" class="btn">Submit</button>
					</form>
				</section>

				<section class="overflow-x-auto mt-4">
					<div class="rounded-lg border">
						<table class="table">
							<thead>
								<tr>
									<th class="pl-3">ID</th>
									<th>Name</th>
									<th>Producer</th>
									<th class="text-right pr-3">Price</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${keyword.isEmpty()}">
										<tr>
											<td colspan="4" class="text-center p-4">
												Found products will be here.
											</td>
										</tr>
									</c:when>

									<c:when test="${searchResult.isEmpty()}">
										<tr>
											<td colspan="4" class="text-center p-4">
												No results for "${keyword}"
											</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="product" items="${searchResult}">
											<tr>
												<td class="pl-3 font-medium">${product.id}</td>
												<td>
													<a href="${pageContext.request.contextPath}/customer/product/${product.id}">
														${product.name}
													</a>
												</td>
												<td>${product.producer}</td>
												<td class="pr-3 text-right">
													<fmt:formatNumber
														value="${product.price}"
														minFractionDigits="2"
														maxFractionDigits="2"
													/>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</section>
			</div>

			<div class="card flex-row items-center justify-between p-2">
				<ol
					class="text-muted-foreground ml-3 flex flex-wrap items-center gap-1.5 text-sm break-words sm:gap-2.5"
				>
					<li class="inline-flex items-center gap-1.5">
						<a
							href="${pageContext.request.contextPath}/customer/functions"
							class="hover:text-foreground transition-colors"
						>
							Functions
						</a>
					</li>
					<li>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							stroke-linecap="round"
							stroke-linejoin="round"
							class="size-3.5"
						>
							<path d="m9 18 6-6-6-6"/>
						</svg>
					</li>
					<li class="inline-flex items-center gap-1.5">
						<a
							href="${pageContext.request.contextPath}/customer/productSearching"
							class="text-foreground transition-colors"
						>
							Product Search
						</a>
					</li>
				</ol>

				<button
					type="button"
					aria-label="Toggle dark mode"
					data-tooltip="Toggle dark mode"
					data-side="bottom"
					data-align="end"
					onclick="document.dispatchEvent(new CustomEvent('basecoat:theme'))"
					class="btn-icon-outline size-8"
				>
					<span class="hidden dark:block">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
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
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							stroke-linecap="round"
							stroke-linejoin="round"
						>
							<path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z"/>
						</svg>
					</span>
				</button>
			</div>
		</div>
	</body>
</html>
