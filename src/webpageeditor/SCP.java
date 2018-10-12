/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author kalan
 */
public class SCP {

    private String UserName="";
    private String Password="";
    private String host = "labs.ece.uw.edu";
    private String destination = "/var/www/html/research/nsl/faculty/radha/assets/json/";
    private boolean loginPassed = false;
    private JSch jsch=new JSch();
         
    public boolean connectTest(String username, String password ) 
    {
        System.out.println("Running SCP");
         try
        {


          Session session=jsch.getSession(username, host, 22);

          java.util.Properties config = new java.util.Properties(); 
          config.put("StrictHostKeyChecking", "no");
          session.setPassword(password);
          session.setConfig(config);
          session.connect();
          session.disconnect();
          
          //keeping a copy for future use
          UserName = username;
          Password=password;
          loginPassed = true;
          
        }catch (Exception x)
        {
            System.err.println("failed");
            return false;

        }
	 
         return true;
    }
    
    
    public void uploadFile(String fileName)
    {
        try
        {
            Session session = jsch.getSession(UserName, host);
            session.setPassword(Password);
            session.connect();
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            sftpChannel.put(System.getProperty("user.dir")+"\\JSON\\"+fileName, destination+fileName,ChannelSftp.OVERWRITE);
            sftpChannel.disconnect();
            session.disconnect();
            

        }catch (Exception x)
        {
            JOptionPane.showMessageDialog(null, "Failed to commit \""+fileName+"\" file. Please try again !","Commit failed !", JOptionPane.ERROR_MESSAGE);
        }
        
        
        JOptionPane.showMessageDialog(null, "Web Page has been successfully updated !","Web Page Successfully Updated!", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    
    public void uploadFliles(String filePath,String destination_) //destination always end with a /
    {
        try
        {
            Session session = jsch.getSession(UserName, host);
            session.setPassword(Password);
            session.connect();
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            sftpChannel.put(filePath, destination_+new File(filePath).getName(),ChannelSftp.OVERWRITE);
            sftpChannel.disconnect();
            session.disconnect();
        }catch (Exception x)
        {
            JOptionPane.showMessageDialog(null, "Failed to upload selected file. Please try again !","Commit failed !", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }
    
    public boolean getLoginStatus()
    {
        return loginPassed;
    }
    
    
    public String getUserName()
    {
        return UserName;
    }
    
    public static boolean openWebpage(URI uri) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(uri);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return false;
}

public static boolean openWebpage(URL url) {
    try {
        return openWebpage(url.toURI());
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
    return false;
}
    
            
    

}
