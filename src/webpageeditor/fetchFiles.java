/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author kalan
 */
public class fetchFiles {
    
    public void downloadFiles(String WebstiteUrl, List<String>files) throws MalformedURLException, IOException
    {
        for(int loop=0;loop<files.size();loop++)
        {
            Path target = Paths.get(System.getProperty("user.dir")+"\\JSON\\"+files.get(loop));
            URL website = new URL(WebstiteUrl+files.get(loop));

            try (InputStream in = website.openStream())
            {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception x)
            {
                JOptionPane.showMessageDialog(null, "Files cannot be fetched due to invalid URL.","URL not found", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    
    }
}
