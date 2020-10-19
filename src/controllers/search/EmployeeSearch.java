package controllers.search;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Project;
import models.ProjectEmployee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeSearch
 */
@WebServlet("/employeeSearch")
public class EmployeeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//PE情報取得
		@SuppressWarnings("unchecked")
		List<ProjectEmployee> pes = (List<ProjectEmployee>)request.getSession().getAttribute("pes");

		//リーダー情報を取得
		if(request.getParameter("leader_employee") != null){
			Employee leaderEmployee = em.find(Employee.class, Integer.parseInt(request.getParameter("leader_employee")));
			for(ProjectEmployee pe: pes){
				if(pe.getEmployee_id().getId() == leaderEmployee.getId()){
					pe.setLeader_flag(1);
				} else{
					pe.setLeader_flag(0);
				}

			}
		}


		Project p = new Project();

		p.setTitle(request.getParameter("title"));
		p.setCustomer(request.getParameter("customer"));
		p.setStatus_flag(Integer.parseInt(request.getParameter("status_flag")));

		request.getSession().setAttribute("project_editing", p);
		request.getSession().setAttribute("pes", pes);
		request.setAttribute("_token", request.getSession().getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/search/employeeSearch.jsp");
		rd.forward(request, response);

	}

}
