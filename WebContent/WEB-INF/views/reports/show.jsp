<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<c:choose>
			<c:when test="${report != null}" >
				<h2>日報　詳細ページ</h2>

				<table>
					<tbody>
						<tr>
							<th>氏名</th>
							<td><c:out value="${report.employee.name}" /></td>
						</tr>
						<tr>
							<th>日付</th>
							<td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<th>プロジェクト情報</th>
							<td>
								<c:out value="${report.project.title}" />(客先：<c:out value="${report.project.customer}" />)
							</td>
						</tr>
						<tr>
							<th>内容</th>
							<td>
								<pre><c:out value="${report.content}" /></pre>
							</td>
						</tr>
						<tr>
							<th>登録日時</th>
							<td><fmt:formatDate value="${report.created_at}" /></td>
						</tr>
						<tr>
							<th>更新日時</th>
							<td><fmt:formatDate value="${report.updated_at}" /></td>
						</tr>
						<tr>
							<th>承認ステータス</th>
							<td>
								<c:choose>
									<c:when	test="${approval.approvalStatus == 0}">作成中</c:when>
									<c:when	test="${approval.approvalStatus == 1}">申請中</c:when>
									<c:when	test="${approval.approvalStatus == 2}">承認</c:when>
									<c:otherwise>差し戻し</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>承認者</th>
							<td class="approver-show">
								<div class="approver-name">
									<p><c:out value="${approval.employee.name}" /></p>
								</div>
								<div class="approver-button">
									<form method="POST" action="${pageContext.request.contextPath}/approvals/approverSelect">
										<input type="hidden" name="report_id" value="${report.id}">
										<input type="hidden" name="approval_id" value="${approval.id}">
										<input type="hidden" name="_token" value="${_token}">
										<button type="submit">承認者変更</button>
									</form>
								</div>
							</td>
						</tr>
						<c:if test="${approval.approvalStatus == 2 || approval.approvalStatus == 3}">
							<tr>
								<th>承認者コメント</th>
								<td><c:out value="${approval.approverComment}" /></td>
							</tr>
						</c:if>
					</tbody>
				</table>

				<c:if  test="${sessionScope.login_employee.id == report.employee.id}">
					<!-- 承認後、申請中は編集不可にする -->
					<c:if test="${approval.approvalStatus == 0 || approval.approvalStatus == 3}">
						<p><a href="<c:url value='/approvals/request?id=${report.id}' />">この日報を申請する</a></p>
						<p><a href="<c:url value='/reports/edit?id=${report.id}' />">この日報を編集する</a></p>
					</c:if>
				</c:if>
			</c:when>
			<c:otherwise>
				<h2>お探しのデータは見つかりませんでした。</h2>
			</c:otherwise>
		</c:choose>

		<c:if test="${project_id != null}">
			<p><a href="${pageContext.request.contextPath}/projects/show?id=${project_id}">プロジェクト詳細へ戻る</a></p>
		</c:if>

		<p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
	</c:param>
</c:import>
