package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.PropertyValueException;

import models.Approval;
import models.Project;
import models.ProjectEmployee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsUpdateServlet() {
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

			//編集する日報情報を取得
			Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));

			//承認情報を取得
			Approval a = em.createNamedQuery("getLatestStatus", Approval.class)
							.setParameter("report", r)
							.getSingleResult();

			r.setReport_date(Date.valueOf(request.getParameter("report_date")));
			r.setTitle(request.getParameter("title"));
			r.setContent(request.getParameter("content"));
			r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

			//プロジェクト情報を取得
			Project p = new Project();
			ProjectEmployee pe = new ProjectEmployee();
			try{
				p = em.find(Project.class, Integer.parseInt(request.getParameter("projectId")));

				//承認者情報を取得
				pe = em.createNamedQuery("getProjectLeader", ProjectEmployee.class)
						.setParameter("project", p)
						.getSingleResult();

				//承認者を更新
				if(!p.equals(r.getProject())){
					a.setEmployee(pe.getEmployee_id());
				}



			} catch(NumberFormatException nfe){
			} catch(PropertyValueException pve){
			} catch(PersistenceException pex){}

			if(request.getParameter("projectId") == "0"){
				r.setProject(null);
			} else{
				r.setProject(p);
			}


			List<String> errors = ReportValidator.validator(r);
			if(errors.size() > 0){
				em.close();

				request.setAttribute("_token", request.getSession().getId());
				request.setAttribute("report", r);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
				rd.forward(request, response);
			} else{
				em.getTransaction().begin();
				em.getTransaction().commit();
				em.close();
				request.getSession().setAttribute("flush",  "更新が完了しました。");

				request.getSession().removeAttribute("report_id");
				request.getSession().removeAttribute("postURL");

				response.sendRedirect(request.getContextPath() + "/reports/index");
			}


		}
	}

}
