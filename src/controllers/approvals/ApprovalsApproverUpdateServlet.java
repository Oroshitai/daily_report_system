package controllers.approvals;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsApproverUpdateServlet
 */
@WebServlet("/approvals/approverUpdate")
public class ApprovalsApproverUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsApproverUpdateServlet() {
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

			//変更した承認者を情報を取得
			Employee approver = em.find(Employee.class, Integer.parseInt(request.getParameter("approver_id")));

			//承認者を変更
			a.setEmployee(approver);

			em.getTransaction().begin();
			em.getTransaction().commit();
			em.close();

			request.setAttribute("report", r);
			request.setAttribute("approval", a);
			request.setAttribute("_token", request.getSession().getId());

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
			rd.forward(request, response);
		}
	}

}
