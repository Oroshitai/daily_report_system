<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
			<div id="flush_error">
				<c:out value="${flush}"></c:out>
			</div>
		</c:if>
		<h2>従業員検索ページ</h2><br />

		<form method="POST" action="${pageContext.request.contextPath}/employeeFind">
			<label for="employee_name">従業員名</label><br />
			<input type="text" name="employee_name">
			<br /><br />

			<label for="employee_code">従業員番号</label><br />
			<input type="number" name="employee_code">
			<br /><br />

			<button type="submit">検索</button>
			<br /><br />
		</form>

		<form method="POST" action="${pageContext.request.contextPath}/employeeAdd">
			<c:if test="${employees != null}">
				<table>
					<tbody>
						<tr>
							<th>メンバー選択</th>
							<th>社員番号</th>
							<th>名前</th>
						</tr>
						<tr>
							<c:forEach var="employee" items="${employees}" varStatus="st">
								<tr>
									<c:choose>
										<c:when test="${sameMemberNotices[st.index] == 1}">
											<td>(メンバー追加済み)</td>
											<td><c:out value="${employee.code}" /></td>
											<td><c:out value="${employee.name}" /></td>
										</c:when>
										<c:otherwise>
											<td><input type="checkbox" name="checkedEmployeeId" value="${employee.id}"></td>
											<td><c:out value="${employee.code}" /></td>
											<td><c:out value="${employee.name}" /></td>
										</c:otherwise>
									</c:choose>
								</tr>

							</c:forEach>

						</tr>
					</tbody>
				</table>
				<ul>
				</ul>
				<br /><br />
				<button type="submit">追加</button>
			</c:if>



		</form>


	</c:param>
</c:import>
