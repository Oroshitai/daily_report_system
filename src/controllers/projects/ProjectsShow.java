package controllers.projects;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.ProjectEmployee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ProjectsShow
 */
@WebServlet("/projects/show")
public class ProjectsShow extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsShow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//プロジェクト情報を取得
		Project p = em.find(Project.class, Integer.parseInt(request.getParameter("id")));

		//プロジェクトメンバー情報を取得
		List<ProjectEmployee> pes = em.createNamedQuery("getProjectMembers", ProjectEmployee.class)
														.setParameter("project", p)
														.getResultList();

		//プロジェクトに関わる日報を取得
		List<Report> reports = em.createNamedQuery("getProjectReports", Report.class)
									.setParameter("project", p)
									.getResultList();

		em.close();

		request.setAttribute("project", p);
		request.setAttribute("reports", reports);
		request.setAttribute("pes", pes);
		request.setAttribute("_token", request.getSession().getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/show.jsp");
		rd.forward(request, response);
	}

}
