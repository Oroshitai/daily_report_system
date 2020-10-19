package models;

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

@Table(name="project_employee")
@NamedQueries({
		@NamedQuery(
				name = "getProjectMembers",
				query = "select pe from ProjectEmployee as pe where pe.project_id = :project order by pe.id desc"
				),
		@NamedQuery(
				name = "getProjectLeader",
				query = "select pe from ProjectEmployee as pe "
						+ "where pe.project_id = :project and pe.leader_flag = 1"
						+ "order by pe.leader_flag desc"
				)
})
@Entity
public class ProjectEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project_id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee_id;

	@Column(name = "leader_flag", nullable = false)
	private Integer leader_flag;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject_id() {
		return project_id;
	}

	public void setProject_id(Project porject_id) {
		this.project_id = porject_id;
	}

	public Employee getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Employee employee_id) {
		this.employee_id = employee_id;
	}

	public Integer getLeader_flag() {
		return leader_flag;
	}

	public void setLeader_flag(Integer leader_flag) {
		this.leader_flag = leader_flag;
	}


}
