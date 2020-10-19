package controllers.approvals;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import models.Employee;
import models.ProjectEmployee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsApproverSelectServlet
 */
@WebServlet("/approvals/approverSelect")
public class ApprovalsApproverSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsApproverSelectServlet() {
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

			//日報情報を取得
			Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));

			//承認情報を取得
			Approval a = em.find(Approval.class, Integer.parseInt(request.getParameter("approval_id")));

			//部長情報を取得
			List<Employee> directors = em.createNamedQuery("getDirectors", Employee.class)
					.getResultList();

			//PL情報を取得
			ProjectEmployee pe = em.createNamedQuery("getProjectLeader", ProjectEmployee.class)
					.setParameter("project", r.getProject())
					.getSingleResult();

			request.setAttribute("_token", request.getSession().getId());
			request.setAttribute("report", r);
			request.setAttribute("approval", a);
			request.setAttribute("directors", directors);
			request.setAttribute("pe", pe);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/approverSelect.jsp");
			rd.forward(request, response);
		}
	}

}
