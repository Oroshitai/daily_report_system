<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
			<div id="flush_success">
				<c:out value="${flush}"></c:out>
			</div>
		</c:if>
		<h2>日報管理システムへようこそ</h2>

		<c:if test="${approvals_requested.size() > 0}">
			<h3>【申請されている日報一覧】</h3>
			<table id="report_list">
				<tbody>
					<tr>
						<th class="report_name">氏名</th>
						<th class="report_date">日付</th>
						<th class="report_title">タイトル</th>
						<th class="report_action">操作</th>
					</tr>
					<c:forEach var="approval" items="${approvals_requested}" varStatus="status">
						<tr class="row${status.count % 2}">
							<td class="report_name"><c:out value="${approval.report.employee.name}" /></td>
							<td class="report_date"><fmt:formatDate value="${approval.report.report_date}" pattern='yyyy-MM-dd' /></td>
							<td class="report_title"><c:out value="${approval.report.title}" /></td>
							<td class="report_action"><a href="<c:url value='/approvals/check?id=${approval.report.id}' />">承認ページ</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			（全 ${approvals_requested.size()} 件）
		</c:if>
		<br /><br />

		<c:if test="${approvals_remanded.size() > 0}">
			<h3>【差し戻された日報一覧】</h3>
			<table id="report_list">
				<tbody>
					<tr>
						<th class="report_name">氏名</th>
						<th class="report_date">日付</th>
						<th class="report_title">タイトル</th>
						<th class="report_action">操作</th>
					</tr>
					<c:forEach var="approval" items="${approvals_remanded}" varStatus="status">
						<tr class="row${status.count % 2}">
							<td class="report_name"><c:out value="${approval.report.employee.name}" /></td>
							<td class="report_date"><fmt:formatDate value="${approval.report.report_date}" pattern='yyyy-MM-dd' /></td>
							<td class="report_title"><c:out value="${approval.report.title}" /></td>
							<td class="report_action"><a href="<c:url value='/reports/show?id=${approval.report.id}' />">詳細を見る</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			（全 ${approvals_remanded.size()} 件）
		</c:if>
		<br /><br />

		<c:if test="${approvals_editing.size() > 0}">
			<h3>【作業中の日報一覧】</h3>
			<table id="report_list">
				<tbody>
					<tr>
						<th class="report_name">氏名</th>
						<th class="report_date">日付</th>
						<th class="report_title">タイトル</th>
						<th class="report_action">操作</th>
					</tr>
					<c:forEach var="approval" items="${approvals_editing}" varStatus="status">
						<tr class="row${status.count % 2}">
							<td class="report_name"><c:out value="${approval.report.employee.name}" /></td>
							<td class="report_date"><fmt:formatDate value="${approval.report.report_date}" pattern='yyyy-MM-dd' /></td>
							<td class="report_title"><c:out value="${approval.report.title}" /></td>
							<td class="report_action"><a href="<c:url value='/reports/show?id=${approval.report.id}' />">詳細を見る</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			（全 ${approvals_editing.size()} 件）
		</c:if>
		<br /><br />

		<h3>【承認された自分の日報一覧】</h3>
		<table id="report_list">
			<tbody>
				<tr>
					<th class="report_name">氏名</th>
					<th class="report_date">日付</th>
					<th class="report_title">タイトル</th>
					<th class="report_action">操作</th>
				</tr>
				<c:forEach var="approval" items="${approvals_approved}" varStatus="status">
					<tr class="row${status.count % 2}">
						<td class="report_name"><c:out value="${approval.report.employee.name}" /></td>
						<td class="report_date"><fmt:formatDate value="${approval.report.report_date}" pattern='yyyy-MM-dd' /></td>
						<td class="report_title"><c:out value="${approval.report.title}" /></td>
						<td class="report_action"><a href="<c:url value='/reports/show?id=${approval.report.id}' />">詳細を見る</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination">
			（全 ${reports_count} 件）<br />
			<c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
				<c:choose>
					<c:when test="${i == page}">
						<c:out value="${i}" />&nbsp;
					</c:when>
					<c:otherwise>
						<a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		<p><a href="<c:url value='/reports/new' />">新規日報の登録</a>

	</c:param>
</c:import>