<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--suppress HtmlUnknownTarget -->
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Document</title>

		<link
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/tailwind.css"
		/>
		<script src="${pageContext.request.contextPath}/resources/js/darkmode.js"></script>
	</head>

	<body class="flex min-h-screen items-center justify-center">
		<div class="m-10 flex w-fit flex-col-reverse gap-5">
			<div class="card w-full">
				<header class="flex flex-col">
					<h2>Review the bill</h2>
					<p>Please review the bill and choose a delivery staff.</p>
				</header>

				<section
					class="space-y-4 overflow-x-auto lg:grid lg:grid-cols-3 lg:gap-5 lg:space-y-0"
				>
					<div class="flex w-full flex-col space-y-3">
						<label for="customer-info-table" class="label"> Bill info </label>

						<div class="h-fit w-full overflow-scroll rounded-lg border">
							<table id="customer-info-table" class="table">
								<thead>
									<tr>
										<th class="pl-3">Field</th>
										<th>Value</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="pl-3">Customer name</td>
										<td>${bill.customer.fullName}</td>
									</tr>
									<tr>
										<td class="pl-3">Address</td>
										<td>${bill.customer.address}</td>
									</tr>
									<tr>
										<td class="pl-3">Số điện thoại</td>
										<td>${bill.customer.phoneNumber}</td>
									</tr>
									<tr>
										<td class="pl-3">Date created</td>
										<td>${bill.createOn}</td>
									</tr>
									<tr>
										<td class="pl-3">Status</td>
										<td>
											<c:choose>
												<c:when test="${bill.status == 'Delivered'}">
													<span class="badge bg-green-700 dark:bg-green-400">
														Delivered
													</span>
												</c:when>
												<c:when test="${bill.status == 'In transit'}">
													<span class="badge bg-blue-700 dark:bg-blue-400">
														In transit
													</span>
												</c:when>
												<c:otherwise>
													<span class="badge bg-yellow-700 dark:bg-yellow-400">
														Pending
													</span>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<c:if test="${bill.deliveryStaff != null}">
										<tr>
											<td class="pl-3">Delivery Staff</td>
											<td>${bill.deliveryStaff.fullName}</td>
										</tr>
										<tr>
											<td class="pl-3">Staff Phone</td>
											<td>${bill.deliveryStaff.phoneNumber}</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>

					<div class="col-span-2 space-y-3">
						<label for="bill-detail-table" class="label col-span-2">
							Bill details
						</label>
						<div class="h-fit w-full overflow-scroll rounded-lg border">
							<table id="bill-detail-table" class="table">
								<thead>
									<tr>
										<th class="pl-4">#</th>
										<th>Name</th>
										<th class="text-right">Quantity</th>
										<th class="text-right">Price</th>
										<th class="pr-4 text-right">Subtotal</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bill.details}" var="detail" varStatus="status">
										<tr>
											<td class="pl-4 font-medium">${status.index + 1}</td>
											<td>${detail.product.name}</td>
											<td class="text-right">${detail.quantity}</td>
											<td class="text-right">${detail.price}</td>
											<td class="pr-4 text-right">${detail.subTotal}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td class="pl-4" colspan="4">Total</td>
										<td class="pr-4 text-right">${bill.total}</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</section>

				<c:if test="${bill.status == 'Pending'}">
					<section>
						<label for="delivery-staff-table" class="label col-span-2 mb-3">
							Delivery staffs
						</label>
						<div class="overflow-hidden rounded-lg border">
							<table id="delivery-staff-table" class="table">
								<thead>
									<tr>
										<th class="pl-3">ID</th>
										<th>Username</th>
										<th>Full name</th>
										<th>Phone number</th>
										<th>Email</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${deliveryStaffs}" var="staff">
										<tr style="cursor: pointer;" onclick="window.location='${pageContext.request.contextPath}/saleAgent/billReview?billId=${bill.id}&deliveryStaffId=${staff.id}'">
											<td class="pl-3">${staff.id}</td>
											<td>${staff.username}</td>
											<td>${staff.fullName}</td>
											<td>${staff.phoneNumber}</td>
											<td>${staff.email}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
				</c:if>
			</div>

			<div class="card flex-row items-center justify-between p-2">
				<ol
					class="text-muted-foreground ml-3 flex flex-wrap items-center gap-1.5 text-sm break-words sm:gap-2.5"
				>
					<li class="inline-flex items-center gap-1.5">
						<a href="${pageContext.request.contextPath}/saleAgent/functions" class="hover:text-foreground transition-colors">Functions</a>
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
							<path d="m9 18 6-6-6-6" />
						</svg>
					</li>
					<li class="inline-flex items-center gap-1.5">
						<a href="${pageContext.request.contextPath}/saleAgent/billSearching" class="hover:text-foreground transition-colors">Bill Management</a>
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
							<path d="m9 18 6-6-6-6" />
						</svg>
					</li>
					<li class="inline-flex items-center gap-1.5">
						<span class="text-foreground font-normal">Bill #${bill.id}</span>
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
							<circle cx="12" cy="12" r="4" />
							<path d="M12 2v2" />
							<path d="M12 20v2" />
							<path d="m4.93 4.93 1.41 1.41" />
							<path d="m17.66 17.66 1.41 1.41" />
							<path d="M2 12h2" />
							<path d="M20 12h2" />
							<path d="m6.34 17.66-1.41 1.41" />
							<path d="m19.07 4.93-1.41 1.41" />
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
							<path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z" />
						</svg>
					</span>
				</button>
			</div>
		</div>
	</body>
</html>
