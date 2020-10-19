<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${errors != null}">
	<div id="flush_error">
		入力内容にエラーがあります。<br />
		<c:forEach var="error" items="${errors}">
			・<c:out value="${error}" /><br />
		</c:forEach>
	</div>
</c:if>

<label for="title">プロジェクト名</label><br />
<input type="text" name="title" value="${sessionScope.project_editing.title}">
<br /><br />

<label for="customer">客先名</label><br />
<input type="text" name="customer" value="${sessionScope.project_editing.customer}">
<br /><br />

<label for="status_flag">プロジェクトステータス</label><br />
<select name="status_flag">
	<option value="0" <c:if test="${sessionScope.project_editing.status_flag == 0}">selected</c:if>>新規</option>
	<option value="1" <c:if test="${sessionScope.project_editing.status_flag == 1}">selected</c:if>>作業中</option>
	<option value="2" <c:if test="${sessionScope.project_editing.status_flag == 2}">selected</c:if>>停止中</option>
	<option value="3" <c:if test="${sessionScope.project_editing.status_flag == 3}">selected</c:if>>完了</option>
</select>
<br /><br />

<!-- メンバー項目 -->
<label for="member">プロジェクトメンバー</label><br />
<c:if test="${sessionScope.pes != null}">
	<table>
		<tbody>
			<tr>
				<th>PL選択</th>
				<th>社員番号</th>
				<th>名前</th>
				<th>削除選択</th>
			</tr>
			<c:forEach var="pe" items="${sessionScope.pes}" varStatus="st">
				<tr>
					<td>
						<c:choose>
							<c:when test="${pe.leader_flag == 1}">
								<input type="radio" name="leader_employee" value="${pe.employee_id.id}" checked="checked">
							</c:when>
							<c:otherwise>
								<input type="radio" name="leader_employee" value="${pe.employee_id.id}">
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:out value="${pe.employee_id.code}" />
					</td>
					<td>
						<c:out value="${pe.employee_id.name}" />
					</td>
					<td>
						<input type="checkbox" name="listIndex" value="${st.index}">
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</c:if>

<input type="button" onclick="funcClick('<c:url value='/employeeSearch'  />')" value="検索する">&nbsp;
<c:if test="${sessionScope.pes != null}">
	<input type="button" onclick="funcClick('<c:url value='/searchDestroy'  />')" value="削除する">
</c:if>
<br /><br />

<input type="hidden" name="_token" value="${_token}">
<input type="button" onclick="funcClick('<c:url value='/projects/${sessionScope.postURL}'  />')" value="投稿">
<br /><br />