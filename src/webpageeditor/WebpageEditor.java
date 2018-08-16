/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import com.alee.laf.WebLookAndFeel;

import java.io.IOException;


/**
 *
 * @author Kalana
 */
public class WebpageEditor {



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        WebLookAndFeel.install();
        login user = new login();
        //setting SCP and saving user name and pass
        user.setVisible(true);
        
        

}
    
    
}
