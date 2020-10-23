package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "approvals")
@NamedQueries({
	@NamedQuery(
			name = "getRequestedReports",
			query = "SELECT a FROM Approval AS a "
					+ "WHERE a.created_at = "
					+ "(SELECT max(ap.created_at) FROM Approval AS ap "
					+ "WHERE a.report = ap.report "
					+ "AND a.employee = :employee) "
					+ "AND a.approvalStatus = 1"
					+ "ORDER BY a.created_at"
			),
	@NamedQuery(
			name = "getEditingReports",
			query = "SELECT a FROM Approval AS a "
					+ "WHERE a.created_at = "
					+ "(SELECT max(ap.created_at) FROM Approval AS ap "
					+ "WHERE a.report = ap.report "
					+ "AND a.report.employee = :employee) "
					+ "And a.approvalStatus = 0"
					+ "ORDER BY a.created_at"
			),
	@NamedQuery(
			name = "getRemandedReports",
			query = "SELECT a FROM Approval AS a "
					+ "WHERE a.created_at = "
					+ "(SELECT max(ap.created_at) FROM Approval AS ap "
					+ "WHERE a.report = ap.report "
					+ "AND ap.report.employee = :employee) "
					+ "And a.approvalStatus = 3"
					+ "ORDER BY a.created_at"
			),
	@NamedQuery(
			name = "getLatestStatus",
			query = "select a from Approval as a "
					+ "where a.created_at = (select max(ap.created_at) from Approval as ap "
					+ "where ap.report = :report)"
			),
	@NamedQuery(
			name = "getReportToApprove",
			query = "SELECT a FROM Approval AS a "
					+ "WHERE a.created_at = "
					+ "(SELECT max(ap.created_at) FROM Approval AS ap "
					+ "WHERE ap.report = :report "
					+ "AND ap.approvalStatus = 1) "
			),
	@NamedQuery(
			name = "getApprovedReports",
			query = "SELECT a FROM Approval AS a "
					+ "WHERE a.created_at = "
					+ "(SELECT max(ap.created_at) FROM Approval AS ap "
					+ "WHERE a.report = ap.report "
					+ "AND ap.report.employee = :employee) "
					+ "And a.approvalStatus = 2"
			)
})
@Entity
public class Approval {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "approval_status")
	private Integer approvalStatus;

	@Column(name = "approver_comment")
	private String approverComment;

	@ManyToOne
	@JoinColumn(name = "approver")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "report_id")
	private Report report;

	@Column(name = "created_at")
	private Timestamp created_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApproverComment() {
		return approverComment;
	}

	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
}
