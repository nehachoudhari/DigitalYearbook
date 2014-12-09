package bean;

import helper.DropboxUploaderHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.ResearchGroupService;
import exception.YearbookException;

public class DispResearchGroupList {
	
	@EJB
	ResearchGroupService researchService;
	
//	@EJB
//	Student studentBean;

	List<SelectItem> allGroups = null;
	
	List<ResearchGroup> rGroupList = null;
	
	public List<ResearchGroup> getrGroupList() {
		return rGroupList;
	}

	public void setrGroupList(List<ResearchGroup> rGroupList) {
		this.rGroupList = rGroupList;
	}

	public List<SelectItem> getAllGroups() {
		return allGroups;
	}

	public void setAllGroups(List<SelectItem> allGroups) {
		this.allGroups = allGroups;
	}
	private StreamedContent dbImage;
	
	public StreamedContent getDbImage() {
		return dbImage;
	}
	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}
	
	private ResearchGroup convertToBean(entity.ResearchGroup entityBean){
		ResearchGroup research = new ResearchGroup();
		try {			
			research.setDeptId(entityBean.getDeptId());
			research.setDescription(entityBean.getDescription());
			research.setGroupId(entityBean.getGroupId());
			research.setName(entityBean.getName());
			research.setUrl(entityBean.getUrl());
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBoxIntoYearbook(entityBean.getPhotoUrl());
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//yearbook"+entityBean.getPhotoUrl());
			File dispFile = new File(filePath);
			StreamedContent dbImage = null;
			dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
			research.setDbImage(dbImage);
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return research;
	}
	//@PostConstruct
	public String init() {
		allGroups = new ArrayList<SelectItem>();
		Collection<entity.ResearchGroup> list;
		try {
			list = researchService.getAllResearchGroups();
			if(list!= null) {
				rGroupList = new ArrayList<ResearchGroup>();
				for(entity.ResearchGroup r : list) {
					allGroups.add(new SelectItem(r.getGroupId(),r.getName()));
					
					rGroupList.add(convertToBean(r));
					
					
				}
			}else {
				System.out.println("No research groups found");
			}
			return "research";
		} catch (YearbookException e) {
			e.printStackTrace();
		}
		return "false";
    }
}
