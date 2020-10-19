<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">

		<c:choose>
			<c:when test="${project != null}">
				<h2>プロジェクト　詳細</h2>
				<table>
					<tbody>
						<tr>
							<th>プロジェクト名</th>
							<td><c:out value="${project.title}" /></td>
						</tr>
						<tr>
							<th>客先名</th>
							<td><c:out value="${project.customer}" /></td>
						</tr>

						<tr>
							<th>メンバー</th>
							<td>
								<c:forEach var="pe" items="${pes}">
									<c:if test="${pe.leader_flag == 1}">
										[PL]
									</c:if>
									<c:out value="${pe.employee_id.name}" />
									/社員番号<c:out value="${pe.employee_id.code}" />&nbsp;&nbsp;
								</c:forEach>
							</td>
						</tr>

						<tr>
							<th>プロジェクトステータス</th>
							<td>
								<c:choose>
									<c:when	test="${project.status_flag == 0}">新規</c:when>
									<c:when	test="${project.status_flag == 1}">作業中</c:when>
									<c:when	test="${project.status_flag == 2}">停止中</c:when>
									<c:otherwise>完了</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>登録日時</th>
							<td><fmt:formatDate value="${project.created_at}" pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<th>更新日時</th>
							<td><fmt:formatDate value="${project.updated_at}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</tbody>
				</table>

				<p><a href="${pageContext.request.contextPath}/projects/edit?id=${project.id}">編集する</a></p>

				<h3>【プロジェクト名：<c:out value="${project.title}" /> の日報一覧】</h3>
				<table id="report_list">
				<tbody>
					<tr>
						<th class="report_name">氏名</th>
						<th class="report_date">日付</th>
						<th class="report_title">タイトル</th>
						<th class="report_action">操作</th>
					</tr>
					<c:forEach var="report" items="${reports}" varStatus="status">
						<tr class="row${status.count % 2}">
							<td class="report_name"><c:out value="${report.employee.name}" /></td>
							<td class="report_date"><fmt:formatDate value="${report.report_date}" pattern='yyyy-MM-dd' /></td>
							<td class="report_title"><c:out value="${report.title}" /></td>
							<td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}&project_id=${report.project.id}' />">詳細を見る</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			</c:when>
			<c:otherwise>
				お探しのデータは見つかりませんでした。
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>
