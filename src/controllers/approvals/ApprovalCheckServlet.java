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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalCheckServlet
 */
@WebServlet("/approvals/check")
public class ApprovalCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//承認する日報情報を取得
		Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

		//承認情報を取得
		Approval a = em.createNamedQuery("getReportToApprove", Approval.class)
										.setParameter("report", r)
										.getSingleResult();

		em.close();

		request.setAttribute("approval", a);
		request.setAttribute("report", r);
		request.setAttribute("_token", request.getSession().getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/check.jsp");
		rd.forward(request, response);

	}

}
