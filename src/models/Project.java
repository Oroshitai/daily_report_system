package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(
			name = "getAllProjects",
			query = "select p from Project as p order by p.id desc"
			),
	@NamedQuery(
			name = "getProjectsCount",
			query = "select count(p) from Project as p"
			)
})
@Table(name = "projects")
public class Project {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "customer", nullable = false)
	private String customer;

	@Column(name = "status_flag", nullable = false)
	private Integer status_flag;

	/*@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;*/

	@Column(name = "created_at", nullable = false)
	private Timestamp created_at;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updated_at;

	@Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;


	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}

	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}

	public String getCustomer(){
		return customer;
	}
	public void setCustomer(String customer){
		this.customer = customer;
	}

	public Integer getStatus_flag(){
		return status_flag;
	}
	public void setStatus_flag(Integer status_flag){
		this.status_flag = status_flag;
	}

	/*public Employee getEmployee(){
		return employee;
	}

	public void setEmployee(Employee employee){
		this.employee = employee;
	}*/


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

	public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

}
