package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsNewServlet
 */
@WebServlet("/reports/new")
public class ReportsNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//プロジェクト情報を取得
		List<Project> projects = em.createNamedQuery("getOpenProjects", Project.class)
													.getResultList();

		em.close();

		Report r = new Report();

		//現在の日付を保存
		r.setReport_date(new Date(System.currentTimeMillis()));

		//リクエストスコープに値を格納
		request.setAttribute("report", r);
		request.setAttribute("projects", projects);
		request.setAttribute("_token", request.getSession().getId());

		request.getSession().setAttribute("postURL", "create");

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
		rd.forward(request, response);

	}

}
