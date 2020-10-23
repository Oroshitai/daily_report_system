<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h2>日報承認ページ</h2>

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
						<c:choose>
							<c:when test="${report.project == null}">
								プロジェクト無所属
							</c:when>
							<c:otherwise>
								<c:out value="${report.project.title}" />(客先：<c:out value="${report.project.customer}" />)
							</c:otherwise>
						</c:choose>
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
					<td>
						<c:out value="${approval.employee.name}" />/
						社員番号:<c:out value="${approval.employee.code}" />
					</td>
				</tr>
			</tbody>
		</table>

		<form method="POST" name="form_approval" action="">
			<label for="comment">コメント</label><br />
			<textarea name="comment" rows="10" cols="50"></textarea><br />

			<input type="hidden" name="_token" value="${_token}">
			<input type="hidden" name="id" value="${report.id}">
			<input type="button" onclick="funcClick('<c:url value='/approvals/approve'  />')" value="承認する">
			<input type="button" onclick="funcClick('<c:url value='/approvals/remand'  />')" value="差し戻す">
		</form>

		<script>
			function funcClick(url){
				document.form_approval.action = url;
				document.form_approval.submit();
			}
		</script>

		<p><a href="${pageContext.request.contextPath}/">トップへ戻る</a></p>

	</c:param>
</c:import>