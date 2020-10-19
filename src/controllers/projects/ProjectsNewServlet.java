package controllers.projects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.ProjectEmployee;

/**
 * Servlet implementation class ProjectsNewServlet
 */
@WebServlet("/projects/new")
public class ProjectsNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("_token", request.getSession().getId());

		//プロジェクトの初期値
		Project p = new Project();
		List<ProjectEmployee> pes = new ArrayList<ProjectEmployee>();
		request.getSession().setAttribute("project_editing", p);
		request.getSession().setAttribute("pes", pes);

		//URLをセッションスコープに保存
		request.getSession().setAttribute("postURL", "create");
		request.getSession().setAttribute("getURL", "new.jsp");

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/new.jsp");
		rd.forward(request, response);
	}

}
