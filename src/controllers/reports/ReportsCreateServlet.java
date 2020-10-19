package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import models.Project;
import models.ProjectEmployee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = (String)request.getParameter("_token");
		if(_token != null && _token.equals(request.getSession().getId())){
			EntityManager em = DBUtil.createEntityManager();

			Report r = new Report();

			//従業員情報を取得
			r.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

			//プロジェクト情報を取得
			Project p = em.find(Project.class, Integer.parseInt(request.getParameter("projectId")));
			r.setProject(p);

			//承認者情報を取得
			ProjectEmployee pe = em.createNamedQuery("getProjectLeader", ProjectEmployee.class)
													.setParameter("project", p)
													.getSingleResult();

			Date report_date = new Date(System.currentTimeMillis());
			String rd_str = request.getParameter("report_date");
			if(rd_str != null && !rd_str.equals("")){
				report_date = Date.valueOf(request.getParameter("report_date"));
			}
			r.setReport_date(report_date);
			r.setTitle(request.getParameter("title"));
			r.setContent(request.getParameter("content"));

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			r.setCreated_at(currentTime);
			r.setUpdated_at(currentTime);

			//承認情報を保存
			Approval a = new Approval();
			a.setApprovalStatus(0);
			a.setApproverComment("");
			a.setReport(r);
			a.setEmployee(pe.getEmployee_id());
			a.setCreated_at(currentTime);


			List<String> errors = ReportValidator.validator(r);
			if(errors.size() > 0){
				em.close();

				request.setAttribute("_token" , request.getSession().getId());
				request.setAttribute("report", r);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
				rd.forward(request, response);
			} else{
				em.getTransaction().begin();
				em.persist(r);
				em.persist(a);
				em.getTransaction().commit();

				em.close();
				request.getSession().setAttribute("flush", "登録が完了しました。");

				request.getSession().removeAttribute("postURL");

				response.sendRedirect(request.getContextPath() + "/reports/index");
			}



		}
	}

}
