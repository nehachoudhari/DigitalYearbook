package bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;


public class Photograph {
		
	protected String type;
	
	protected long typeId;
	
	protected String localFileUrl;
	
	public String getLocalFileUrl() {
		return localFileUrl;
	}
	public void setLocalFileUrl(String localFileUrl) {
		this.localFileUrl = localFileUrl;
	}

	protected UploadedFile  file;
	
	public UploadedFile  getFile() {
		return file;
	}
	public void setFile(UploadedFile  file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	protected void copyFile(String fileName,  InputStream in, String type) {
		try {
			ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
			String filePath = extContext.getRealPath("//WEB-INF//images//uploads//"+type+"//" + fileName);
			System.out.println("File is "+extContext.getRealPath("//WEB-INF//images//uploads//"+type+"//" + fileName));
			OutputStream out = new FileOutputStream(new File(filePath));
			System.out.println("Ready to write file");
			int read = 0;
			byte[] bytes = new byte[6124];
			while (true) {
				read = in.read(bytes);
				if (read < 0) {
					break;
				}
				out.write(bytes, 0, read);
				out.flush();
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("New file created!");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
}
