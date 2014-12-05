package bean;

import helper.DropboxUploaderHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import service.CommitteeMemberService;
import exception.YearbookException;

public class CommitteeMemberList {
	
	@EJB
	CommitteeMemberService memberService;
	
//	@EJB
//	Student studentBean;

	List<SelectItem> allMembers = null;
	List<entity.CommitteeMember> listallMembers = null;
	public List<SelectItem> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(List<SelectItem> allMembers) {
		this.allMembers = allMembers;
	}
	
	public List<entity.CommitteeMember> getListallMembers() {
		return listallMembers;
	}

	public void setListallMembers(List<entity.CommitteeMember> listallMembers) {
		this.listallMembers = listallMembers;
	}

	@PostConstruct
	public void init() {
		allMembers = new ArrayList<SelectItem>();
		Collection<entity.CommitteeMember> list;
		try {
		
			list = memberService.getAllCommitteeMembers();

			listallMembers = new ArrayList<entity.CommitteeMember>(list);
			if(list!= null) {
				
				
				
				for(entity.CommitteeMember m : list) {
					allMembers.add(new SelectItem(m.getMember_id(),m.getfName() + " " + m.getlName() ));
				
					DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
					dropboxUploader.fetchFromDropBoxIntoYearbook(m.getPhotoUrl());
					ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
					String filePath = extContext.getRealPath("//images//yearbook"+m.getPhotoUrl());
					m.setPhotoUrl(filePath);
					
				
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
