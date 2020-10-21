package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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

@Table(name = "reports")
@NamedQueries({
	@NamedQuery(
			name = "getAllReports",
			query = "select r from Report as r order by r.id desc"
			),
	@NamedQuery(
			name = "getReportsCount",
			query = "select count(r) from Report as r"
			),
	@NamedQuery(
			name = "getMyAllReports",
			query = "select r from Report as r where r.employee = :employee order by r.id desc"
			),
	@NamedQuery(
			name = "getMyReportsCount",
			query = "select count(r) from Report as r where r.employee = :employee"
			),
	@NamedQuery(
			name = "getProjectReports",
			query = "select r from Report as r where r.project = :project")
})

@Entity
public class Report {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "report_date", nullable = false)
	private Date report_date;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "created_at", nullable = false)
	private Timestamp created_at;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updated_at;

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}

	public Employee getEmployee(){
		return employee;
	}
	public void setEmployee(Employee employee){
		this.employee = employee;
	}

	public Date getReport_date(){
		return report_date;
	}
	public void setReport_date(Date report_date){
		this.report_date = report_date;
	}

	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}

	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}

	public Timestamp getCreated_at(){
		return created_at;
	}
	public void setCreated_at(Timestamp created_at){
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at(){
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at){
		this.updated_at = updated_at;
	}
}
