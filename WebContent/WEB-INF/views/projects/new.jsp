<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h1>プロジェクト新規登録</h1>

		<form method="POST" name="form_new" action="">
			<c:import url="_form.jsp" />

		</form>

		<script>
			function funcClick(url){
				document.form_new.action = url;
				document.form_new.submit();
			}
		</script>

		<a href="${pageContext.request.contextPath}/projects/index">一覧へ戻る</a>

	</c:param>
</c:import>