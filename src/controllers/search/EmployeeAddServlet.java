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
import models.ProjectEmployee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeAddServlet
 */
@WebServlet("/employeeAdd")
public class EmployeeAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		String[] checkedEmployeeIds = null;

		//チェックが入った従業員番号を取得
		if(request.getParameterValues("checkedEmployeeId") != null){
			checkedEmployeeIds = request.getParameterValues("checkedEmployeeId");
		} else{
			request.setAttribute("flush", "追加する従業員を選択してください");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/search/employeeSearch.jsp");
			rd.forward(request, response);
		}

		@SuppressWarnings("unchecked")
		List<ProjectEmployee> pes = (List<ProjectEmployee>)request.getSession().getAttribute("pes");


		for(String employeeId: checkedEmployeeIds){
			Employee e = em.find(Employee.class, Integer.parseInt(employeeId));
			ProjectEmployee pe = new ProjectEmployee();
			pe.setEmployee_id(e);
			pes.add(pe);
		}

		em.close();

		request.setAttribute("_token", request.getSession().getId());
		request.getSession().setAttribute("pes", pes);

		String getURL = (String)request.getSession().getAttribute("getURL");

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/" + getURL);
		rd.forward(request, response);



	}

}
