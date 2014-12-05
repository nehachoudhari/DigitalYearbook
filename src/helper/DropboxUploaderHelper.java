package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class DropboxUploaderHelper {
	private final static String APP_KEY = "cuvou2l62fc1fob";
    private final static String APP_SECRET = "bowvh8nteerroyw";
    private static DbxClient client;
    public DropboxUploaderHelper(){
    	try{   	
	        DbxRequestConfig config = new DbxRequestConfig(
	            "JavaTutorial/1.0", Locale.getDefault().toString());
	       
	        // The following code was used to authenticate this app
	        /*
    		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET); 
    		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	        String authorizeUrl = webAuth.start();
	        System.out.println("1. Go to: " + authorizeUrl);
	        System.out.println("2. Click \"Allow\" (you might have to log in first)");
	        System.out.println("3. Copy the authorization code.");
	        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	        DbxAuthFinish authFinish = webAuth.finish(code);
	        String accessToken = authFinish.accessToken;
	        */
	        
	        String accessToken = "xFhDt2Na8_AAAAAAAAAABhs8u_h4r9oKOZwqp2Z3Uxs7qSI9tJ5boIFjcKX3kDwb";
	        client = new DbxClient(config, accessToken);
	        System.out.println("Linked account: " + client.getAccountInfo().displayName);
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    public String uploadToDropBox(String inputFileName, String type){
    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
		String filePath = extContext.getRealPath("//images//uploads//"+type+"//" + inputFileName);
    	File inputFile = new File(filePath);
    	FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
            DbxEntry.File uploadedFile = client.uploadFile("/"+type+"/"+inputFileName,
                DbxWriteMode.add(), inputFile.length(), inputStream);
            String sharedUrl = client.createShareableUrl("/"+type+"/"+inputFileName);
            System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl);
            return "/"+type+"/"+uploadedFile.name;
        }catch(Exception e){
    		System.out.println(e.getMessage());
    	} finally {
            if(inputStream != null){
            	try{
            		inputStream.close();
            	}catch(Exception e1){
            		System.out.println(e1.getMessage());
            	}
            }
        }
        return null;
    	
    }
    
    
    public DbxEntry.File fetchFromDropBox(String remoteUrl){
		 DbxEntry.File md = null;
    	ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
		String filePath = extContext.getRealPath("//images//downloads//"+remoteUrl);
		 File target = new File(filePath);
		 try {
			 OutputStream out = new FileOutputStream(target);
		     md = client.getFile(remoteUrl, null, out);
		     if(out!=null){
		    	 out.close();
		     }
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
    	return md;
    }
    
    public DbxEntry.File fetchFromDropBoxIntoYearbook(String remoteUrl){
		 DbxEntry.File md = null;
		 ExternalContext extContext =FacesContext.getCurrentInstance().getExternalContext();
		 String filePath = extContext.getRealPath("//images//yearbook//"+remoteUrl);
		 File target = new File(filePath);
		 try {
			 OutputStream out = new FileOutputStream(target);
		     md = client.getFile(remoteUrl, null, out);
		     if(out!=null){
		    	 out.close();
		     }
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
   	return md;
   }
   
}
