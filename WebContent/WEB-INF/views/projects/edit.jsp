<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h1>プロジェクト編集</h1>

		<form method="POST" name="form_update" action="">
			<c:import url="_form.jsp" />

		</form>

		<script>
			function funcClick(url){
				document.form_update.action = url;
				document.form_update.submit();
			}
		</script>

		<p><a href="#" onclick="confirmDestroy();">このプロジェクト情報を削除する</a></p>
		<form method="POST" action="<c:url value='/projects/destroy' />">
			<input type="hidden" name="_token" value="${_token}" />
		</form>
		<script>
			function confirmDestroy(){
				if(confirm("本当に削除してよろしいですか？")){
					document.forms[1].submit();
				}
			}
		</script>

		<a href="${pageContext.request.contextPath}/projects/index">一覧へ戻る</a>
	</c:param>
</c:import>