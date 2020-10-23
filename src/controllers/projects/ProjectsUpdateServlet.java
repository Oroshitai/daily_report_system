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
 * Servlet implementation class ProjectsUpdateServlet
 */
@WebServlet("/projects/update")
public class ProjectsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsUpdateServlet() {
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

			//編集するプロジェクト情報を取得
			Project p = em.find(Project.class, ((Project)request.getSession().getAttribute("project_editing")).getId());

			Employee leaderEmployee = new Employee();

			//古いPE情報を取得
			List<ProjectEmployee> pes_old = em.createNamedQuery("getProjectMembers", ProjectEmployee.class)
												.setParameter("project", p)
												.getResultList();

			//project-employeeインスタンスを作成
			@SuppressWarnings("unchecked")
			List<ProjectEmployee> pes = (List<ProjectEmployee>) request.getSession().getAttribute("pes");

			//編集した情報をプロジェクトへ保存
			p.setTitle(request.getParameter("title"));
			p.setCustomer(request.getParameter("customer"));
			p.setStatus_flag(Integer.parseInt(request.getParameter("status_flag")));

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			p.setUpdated_at(currentTime);
			p.setDelete_flag(0);

			//プロジェクトリーダー情報を取得
			if(request.getParameter("leader_employee") != null){
				leaderEmployee = em.find(Employee.class, Integer.parseInt(request.getParameter("leader_employee")));
			}

			List<String> errors = ProjectValidator.validate(p, leaderEmployee, pes);
			if(errors.size() > 0){
				em.close();

				request.setAttribute("_token", _token);
				request.setAttribute("errors", errors);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/new.jsp");
				rd.forward(request, response);
			} else{
				//テーブルから一度メンバー情報を削除
				for(ProjectEmployee pe: pes_old){
					em.getTransaction().begin();
					em.remove(pe);
					em.getTransaction().commit();
				}

				//テーブルにプロジェクト情報を保存
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
