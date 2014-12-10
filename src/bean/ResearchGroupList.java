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

import service.ResearchGroupService;
import exception.YearbookException;

public class ResearchGroupList {
	
	@EJB
	ResearchGroupService researchService;
	
//	@EJB
//	Student studentBean;

	List<SelectItem> allGroups = null;
	
	List<entity.ResearchGroup> rGroupList = null;
	
	public List<entity.ResearchGroup> getrGroupList() {
		return rGroupList;
	}

	public void setrGroupList(List<entity.ResearchGroup> rGroupList) {
		this.rGroupList = rGroupList;
	}

	public List<SelectItem> getAllGroups() {
		return allGroups;
	}

	public void setAllGroups(List<SelectItem> allGroups) {
		this.allGroups = allGroups;
	}

	@PostConstruct
	public void init() {
		allGroups = new ArrayList<SelectItem>();
		Collection<entity.ResearchGroup> list;
		try {
			list = researchService.getAllResearchGroups();
			if(list!= null) {
				rGroupList = new ArrayList<entity.ResearchGroup>(list);
				for(entity.ResearchGroup r : list) {
					allGroups.add(new SelectItem(r.getGroupId(),r.getName()));				
					
				}
			}else {
				System.out.println("No research groups found");
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
