package controllers.approvals;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsRequestServlet
 */
@WebServlet("/approvals/request")
public class ApprovalsRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//申請する日報情報を取得
		Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

		//過去の承認情報を取得
		Approval a = em.createNamedQuery("getLatestStatus", Approval.class)
										.setParameter("report", r)
										.getSingleResult();

		//承認ステータスに申請を追加
		Approval a_new = new Approval();
		a_new.setApprovalStatus(1);
		a_new.setApproverComment("");
		a_new.setReport(r);
		a_new.setEmployee(a.getEmployee());

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		a_new.setCreated_at(currentTime);


		em.getTransaction().begin();
		em.persist(a_new);
		em.getTransaction().commit();
		em.close();

		request.getSession().setAttribute("flush", "申請しました");

		response.sendRedirect(request.getContextPath() + "/index.html");
	}

}
