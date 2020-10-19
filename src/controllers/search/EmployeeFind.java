package controllers.search;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class EmployeeFind
 */
@WebServlet("/employeeFind")
public class EmployeeFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeFind() {
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

			String employee_name = request.getParameter("employee_name");
			String employee_code = request.getParameter("employee_code");

			//既存のメンバー情報を取得
			@SuppressWarnings("unchecked")
			List<ProjectEmployee> pes = (List<ProjectEmployee>)request.getSession().getAttribute("pes");

			if(employee_name != "" && employee_code != ""){
				List<Employee> employees = em.createNamedQuery("getSearchedEmployeeByNameAndCode", Employee.class)
						.setParameter("name", employee_name + "%")
						.setParameter("code", employee_code + "%")
						.getResultList();
				request.setAttribute("employees", employees);
				request.setAttribute("sameMemberNotices", sameMemberNotice(employees, pes));
			} else if(employee_name != ""){
				List<Employee> employees = em.createNamedQuery("getSearchedEmployeeByName", Employee.class)
						.setParameter("name", employee_name + "%")
						.getResultList();
				request.setAttribute("employees", employees);
				request.setAttribute("sameMemberNotices", sameMemberNotice(employees, pes));
			} else if(employee_code != ""){
				List<Employee> employees = em.createNamedQuery("getSearchedEmployeeByCode", Employee.class)
						.setParameter("code", employee_code + "%")
						.getResultList();
				request.setAttribute("employees", employees);
				request.setAttribute("sameMemberNotices", sameMemberNotice(employees, pes));
			} else{
				request.setAttribute("flush", "従業員名か社員番号を入力してください");
			}

			em.close();

			request.setAttribute("_token", request.getSession().getId());

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/search/employeeSearch.jsp");
			rd.forward(request, response);
		}
	}

	//すでにメンバーに加えている場合
	private static List<Integer> sameMemberNotice(List<Employee> employees, List<ProjectEmployee> pes){
		List<Integer> sameMemberNotices = new ArrayList<Integer>();
		if(employees.size() > 0 && pes.size() > 0){
			for(Employee e: employees){
				for(ProjectEmployee pe: pes){
					if(e.getId() == pe.getEmployee_id().getId()){
						sameMemberNotices.add(1);
					}
				}
			}
			sameMemberNotices.add(0);
		}

		return sameMemberNotices;
	}

}
