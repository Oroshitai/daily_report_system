package controllers.toppage;

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
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

		//自分の日報を取得
		int page;
		try{
			page = Integer.parseInt(request.getParameter("page"));
		} catch(Exception e){
			page = 1;
		}

		//申請された日報に関する承認情報を取得
		List<Approval> approvals_requested = em.createNamedQuery("getRequestedReports", Approval.class)
													.setParameter("employee", login_employee)
													.getResultList();

		//差し戻しされたに関する承認情報を取得
		List<Approval> approvals_remanded = em.createNamedQuery("getRemandedReports", Approval.class)
													.setParameter("employee", login_employee)
													.getResultList();


		//作業中のに関する承認情報を取得
		List<Approval> approvals_editing = em.createNamedQuery("getEditingReports", Approval.class)
												.setParameter("employee", login_employee)
												.getResultList();

		//承認されたに関する承認情報を取得
		List<Approval> approvals_approved = em.createNamedQuery("getApprovedReports", Approval.class)
												.setParameter("employee", login_employee)
												.setFirstResult(15 * (page - 1))
												.setMaxResults(15)
												.getResultList();

		//承認された日報の数を取得
		long reports_count = approvals_approved.size();


		em.close();

		request.setAttribute("reports_count", reports_count);
		request.setAttribute("page", page);
		request.setAttribute("approvals_requested", approvals_requested);
		request.setAttribute("approvals_remanded", approvals_remanded);
		request.setAttribute("approvals_editing", approvals_editing);
		request.setAttribute("approvals_approved", approvals_approved);

		if(request.getSession().getAttribute("flush") != null){
			request.setAttribute("flush", request.getSession().getAttribute("flush"));
			request.getSession().removeAttribute("flush");
		}


		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
		rd.forward(request, response);
	}

}
