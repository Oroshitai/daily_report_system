package controllers.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ProjectEmployee;

/**
 * Servlet implementation class SearchDestroy
 */
@WebServlet("/searchDestroy")
public class SearchDestroy extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDestroy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PE情報を取得
		@SuppressWarnings("unchecked")
		List<ProjectEmployee> pes = (List<ProjectEmployee>)request.getSession().getAttribute("pes");

		if(request.getParameter("listIndex") != null){
			int i = 0;
			String[] listIndex = request.getParameterValues("listIndex");
			//該当項目削除
			for(String index: listIndex){
				pes.remove(Integer.parseInt(index) - i);
				i++;
			}
		}

		request.getSession().setAttribute("pes", pes);
		request.setAttribute("_token", request.getSession().getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/projects/new.jsp");
		rd.forward(request, response);
	}

}
