package bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import service.ResearchGroupService;
import exception.YearbookException;

public class ResearchGroupList {
	
	@EJB
	ResearchGroupService researchService;

	List<SelectItem> allGroups = null;
	
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
				for(entity.ResearchGroup r : list) {
					allGroups.add(new SelectItem(r.getGroupId(),r.getName()));
				}
			}
		} catch (YearbookException e) {
			e.printStackTrace();
		}
    }
}
