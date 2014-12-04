package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import service.CommitteeMemberService;
import service.DepartmentService;
import entity.CommitteeMember;
import exception.YearbookException;

public class CommitteeMemberList {
	
	@EJB
	CommitteeMemberService memberService;

	List<SelectItem> allMembers = null;
		
	public List<SelectItem> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(List<SelectItem> allMembers) {
		this.allMembers = allMembers;
	}

	@PostConstruct
	public void init() {
		allMembers = new ArrayList<SelectItem>();
		Collection<entity.CommitteeMember> list;
		try {
			list = memberService.getAllCommitteeMembers();
			if(list!= null) {
				for(entity.CommitteeMember m : list) {
					allMembers.add(new SelectItem(m.getMember_id(),m.getfName() + " " + m.getlName() ));
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
