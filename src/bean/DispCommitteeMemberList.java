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

import service.CommitteeMemberService;
import exception.YearbookException;

public class DispCommitteeMemberList {
	
	@EJB
	CommitteeMemberService memberService;
	
//	@EJB
//	Student studentBean;

	List<SelectItem> allMembers = null;
	List<CommitteeMember> listallMembers = null;
	public List<SelectItem> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(List<SelectItem> allMembers) {
		this.allMembers = allMembers;
	}
	
	public List<CommitteeMember> getListallMembers() {
		return listallMembers;
	}

	public void setListallMembers(List<CommitteeMember> listallMembers) {
		this.listallMembers = listallMembers;
	}
	

	private CommitteeMember convertToBean(entity.CommitteeMember entityBean){
		CommitteeMember committee = new CommitteeMember();
		try {			
			committee.setDeptId(entityBean.getDeptId());
			committee.setDesignation(entityBean.getDesignation());
			committee.setfName(entityBean.getfName());
			committee.setMemberId(entityBean.getMember_id());
			committee.setlName(entityBean.getlName());
			
			DropboxUploaderHelper dropboxUploader = new DropboxUploaderHelper();
			dropboxUploader.fetchFromDropBoxIntoYearbook(entityBean.getPhotoUrl());
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//images//yearbook"+entityBean.getPhotoUrl());
			File dispFile = new File(filePath);
			StreamedContent dbImage = null;
			dbImage = new DefaultStreamedContent(new FileInputStream(dispFile), "image/jpg");
			committee.setDbImage(dbImage);
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return committee;
	}
	
	//@PostConstruct
	public String init() {
		allMembers = new ArrayList<SelectItem>();
		Collection<entity.CommitteeMember> list;
		try {
		
			list = memberService.getAllCommitteeMembers();
			
			listallMembers = new ArrayList<CommitteeMember>();
			if(list!= null) {
				for(entity.CommitteeMember m : list) {
					allMembers.add(new SelectItem(m.getMember_id(),m.getfName() + " " + m.getlName() ));
					listallMembers.add(convertToBean(m));
				}
			}
			return "committee";
		} catch (YearbookException e) {
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "false";
    }
}
