package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Approval;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet Filter implementation class ApprovalNoticeFilter
 */
@WebFilter("/*")
public class ApprovalNoticeFilter implements Filter {

    /**
     * Default constructor.
     */
    public ApprovalNoticeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager em = DBUtil.createEntityManager();

		HttpSession session = ((HttpServletRequest)request).getSession();

		Employee e = (Employee)session.getAttribute("login_employee");

		List<String> approvalNoticeFlushes = new ArrayList<String>();

		//差し戻しの通知
		List<Approval> remanded = em.createNamedQuery("getRemandedReports", Approval.class)
										.setParameter("employee", e)
										.getResultList();

		if(remanded.size() > 0){
			approvalNoticeFlushes.add("申請した日報が" + remanded.size() + "件差し戻されています");
		}


		//申請の通知
		List<Approval> requested = em.createNamedQuery("getRequestedReports", Approval.class)
													.setParameter("employee", e)
													.getResultList();

		if(requested.size() > 0){
			approvalNoticeFlushes.add("日報が" + requested.size() + "件申請されています");
		}


		//承認の通知

		request.setAttribute("approvalNoticeFlushes", approvalNoticeFlushes);

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
