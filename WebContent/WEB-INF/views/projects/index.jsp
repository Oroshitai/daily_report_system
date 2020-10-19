<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
			<div id="flush_success">
				<c:out value="${flush}" />
			</div>
		</c:if>

		<h2>プロジェクト一覧</h2>

		<table>
			<tbody>
				<tr>
					<th>プロジェクト名</th>
					<th>客先名</th>
					<th>操作</th>
				</tr>
				<c:forEach var="project" items="${projects}">
					<tr>
						<td><c:out value="${project.title}" /></td>
						<td><c:out value="${project.customer}" /></td>
						<td>
							<c:choose>
								<c:when test="${project.delete_flag == 1}">
									(削除済み)
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/projects/show?id=${project.id}">
										詳細を表示
									</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination">
			<c:forEach var="i" begin="1" end="${((projects_count - 1) / 15) + 1}" step="1">
				<c:choose>
					<c:when test="${i == page}">
						<c:out value="${i}" />&nbsp;
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath}/projects/index?page=${i}">&nbsp;
							<c:out value="${i}" />
						</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>

		<p><a href="${pageContext.request.contextPath}/projects/new">新規プロジェクトの登録</a>
	</c:param>
</c:import>