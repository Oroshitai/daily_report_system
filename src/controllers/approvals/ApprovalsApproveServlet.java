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
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsApproveServlet
 */
@WebServlet("/approvals/approve")
public class ApprovalsApproveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsApproveServlet() {
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

			//承認する日報情報を取得
			Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

			//承認者
			Employee approver = (Employee)request.getSession().getAttribute("login_employee");

			//承認情報を追加
			Approval a_new = new Approval();
			a_new.setApprovalStatus(2);
			a_new.setApproverComment(request.getParameter("comment"));
			a_new.setReport(r);
			a_new.setEmployee(approver);

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			a_new.setCreated_at(currentTime);

			//DBに保存
			em.getTransaction().begin();
			em.persist(a_new);
			em.getTransaction().commit();
			em.close();

			//トップページへリダイレクト
			request.getSession().setAttribute("flush", "承認しました");
			response.sendRedirect(request.getContextPath() + "/");
		}

	}

}
