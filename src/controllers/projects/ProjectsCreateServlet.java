package controllers.projects;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Project;
import models.ProjectEmployee;
import models.validators.ProjectValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ProjectsCreateServlet
 */
@WebServlet("/projects/create")
public class ProjectsCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsCreateServlet() {
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

			Project p = new Project();
			Employee leaderEmployee = new Employee();

			//プロジェクト情報を格納
			p.setTitle(request.getParameter("title"));
			p.setCustomer(request.getParameter("customer"));
			p.setStatus_flag(Integer.parseInt(request.getParameter("status_flag")));

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			p.setCreated_at(currentTime);
			p.setUpdated_at(currentTime);
			p.setDelete_flag(0);

			//メンバー情報を取得
			@SuppressWarnings("unchecked")
			List<ProjectEmployee> pes = (List<ProjectEmployee>)request.getSession().getAttribute("pes");

			//プロジェクトリーダー情報を取得
			if(request.getParameter("leader_employee") != null){
				leaderEmployee = em.find(Employee.class, Integer.parseInt(request.getParameter("leader_employee")));
			}

			//エラー評価
			List<String> errors = ProjectValidator.validate(p, leaderEmployee, pes);

			if(errors.size() > 0){
				em.close();

				request.setAttribute("_token", _token);
				request.setAttribute("project", p);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/new.jsp");
				rd.forward(request, response);
			} else{
				em.getTransaction().begin();
				em.persist(p);
				em.getTransaction().commit();
				for(ProjectEmployee pe: pes){
					em.getTransaction().begin();

					ProjectEmployee pe_new = new ProjectEmployee();
					pe_new.setEmployee_id(pe.getEmployee_id());
					pe_new.setProject_id(p);
					if(pe.getEmployee_id().getId() == leaderEmployee.getId()){
						pe_new.setLeader_flag(1);
					} else{
						pe_new.setLeader_flag(0);
					}

					em.persist(pe_new);
					em.getTransaction().commit();
				}

				em.close();

				request.getSession().setAttribute("flush", "登録が完了しました");

				//セッションスコープの削除
				request.getSession().removeAttribute("project_editing");
				request.getSession().removeAttribute("postURL");
				request.getSession().removeAttribute("getURL");
				request.getSession().removeAttribute("pes");

				//プロジェクトインデックスへリダイレクト
				response.sendRedirect(request.getContextPath() + "/projects/index");
			}

		}

	}

}
