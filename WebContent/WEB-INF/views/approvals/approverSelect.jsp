<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h2>承認者編集</h2>
		<p>承認者をPLか部長の中から選択してください</p>

		<form method="POST" action="${pageContext.request.contextPath}/approvals/approverUpdate">
			<table>
				<tbody>
					<tr>
						<th>承認者選択</th>
						<th>社員番号</th>
						<th>役職</th>
						<th>社員名</th>
					</tr>
					<tr>
						<td>
							<c:choose>
								<c:when test="${pe.employee_id.id == approval.employee.id}">
									<input type="radio" name="approver_id" value="${pe.employee_id.id}" checked="checked">
								</c:when>
								<c:otherwise>
									<input type="radio" name="approver_id" value="${pe.employee_id.id}">
								</c:otherwise>
							</c:choose>
						</td>
						<td><c:out value="${pe.employee_id.code}" /></td>
						<td>PL</td>
						<td><c:out value="${pe.employee_id.name}" /></td>
					</tr>
					<c:forEach var="director" items="${directors}">
					<c:choose>
						<c:when test="${director.id != approval.employee.id}">
							<tr>
								<td><input type="radio" name="approver_id" value="${director.id}"></td>
								<td><c:out value="${director.code}" /></td>
								<td>部長</td>
								<td><c:out value="${director.name}" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>
									<input type="radio" name="approver_id" value="${director.id}" checked="checked">
								</td>
								<td><c:out value="${director.code}" /></td>
								<td>部長</td>
								<td><c:out value="${director.name}" /></td>
							</tr>
						</c:otherwise>
					</c:choose>


					</c:forEach>
				</tbody>
			</table>

			<input type="hidden" name="_token" value="${_token}">
			<input type="hidden" name="report_id" value="${report.id}">
			<input type="hidden" name="approval_id" value="${approval.id}">
			<button type="submit">変更する</button>
		</form>

	</c:param>
</c:import>