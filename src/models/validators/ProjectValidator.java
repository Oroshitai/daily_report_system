package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Employee;
import models.Project;
import models.ProjectEmployee;

public class ProjectValidator {
	public static List<String> validate(Project p, Employee leaderEmployee, List<ProjectEmployee> pes){
		List<String> errors = new ArrayList<String>();

		String title_error = _validateTitle(p.getTitle());
		if(!title_error.equals("")){
			errors.add(title_error);
		}

		String customer_error = _validateCustomer(p.getCustomer());
		if(!customer_error.equals("")){
			errors.add(customer_error);
		}

		String leader_error = _validateProjectLeader(leaderEmployee);
		if(!leader_error.equals("")){
			errors.add(leader_error);
		}

		String member_error = _validateProjectMember(pes);
		if(!member_error.equals("")){
			errors.add(member_error);
		}

		return errors;
	}

	private static String _validateTitle(String title){
		if(title == null || title.equals("")){
			return "プロジェクト名を入力してください";
		}
		return "";
	}

	private static String _validateCustomer(String customer){
		if(customer == null || customer.equals("")){
			return "客先名を入力してください";
		}
		return "";
	}

	private static String _validateProjectLeader(Employee leaderEmployee){
		if(leaderEmployee.getId() == null || leaderEmployee.getId().equals("")){
			return "プロジェクトリーダーを選択してください";
		}
		return "";
	}

	private static String _validateProjectMember(List<ProjectEmployee> pes){
		if(pes.size() == 0){
			return "プロジェクトメンバーを追加してください";
		}
		return "";
	}

}
