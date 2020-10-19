package controllers.projects;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import utils.DBUtil;

/**
 * Servlet implementation class ProjectsDestroyServlet
 */
@WebServlet("/projects/destroy")
public class ProjectsDestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = request.getParameter("_token");
		if(_token != null && _token.equals(request.getSession().getId())){
			EntityManager em = DBUtil.createEntityManager();

			//編集中のプロジェクト情報を取得
			Project project_editing = (Project)request.getSession().getAttribute("project_editing");
			Project p = em.find(Project.class, project_editing.getId());

			//プロジェクト情報を論理削除
			p.setDelete_flag(1);
			p.setUpdated_at(new Timestamp(System.currentTimeMillis()));

			em.getTransaction().begin();
			em.getTransaction().commit();
			em.close();

			request.getSession().removeAttribute("project_editing");

			request.setAttribute("flush", "プロジェクトを削除しました");

			response.sendRedirect(request.getContextPath() + "/projects/index");

		}
	}

}
