/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import com.sun.javafx.PlatformUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kalan
 */
public class fileOperations {
    public void addData(JTable table,String fileLocation, String header, String[] ColumnNames, JTextArea[] textboxes) 
    {
        int loop=0;
        DefaultTableModel myModel = (DefaultTableModel)table.getModel();
        ArrayList<String> possibleIDs = new ArrayList<String>();
        //just a save procedure to make sure we dont repeat IDs 
        for(loop=0;loop<table.getRowCount();loop++)
            possibleIDs.add((String)myModel.getValueAt(loop, 0));
        
        int nextId =0;
        
        if(table.getRowCount() == 0)
            nextId = 1;
        else
            nextId = Integer.parseInt((String)myModel.getValueAt(loop-1, 0));
        
        while(true)
        {
            if(!possibleIDs.contains(Integer.toString(nextId)))
                break;
                
            nextId++;   
        }
        //at this poin we have a unique id
        Object[] row = new Object[textboxes.length+1];
        row[0]= Integer.toString(nextId); //adding ID
        for(int curr=1;curr<row.length;curr++)
            row[curr]=textboxes[curr-1].getText();

        myModel.addRow(row);
        
        
        writetoFile(JSONmaker(table,header,ColumnNames), fileLocation);
    }
    
    //edit button logic
    public void editData(JTable table,String fileLocation, String header, String[] ColumnNames, JTextArea[] textboxes) 
    {
        //if table is empty theres nothing to delete
        if(table.getRowCount() == 0)
            return;
        
        if(table.getSelectedRow() == -1)
        {
             JOptionPane.showMessageDialog(null, "Please select a row first.","Row not selected", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        DefaultTableModel myModel = (DefaultTableModel)table.getModel();
        for(int loop=1;loop<table.getColumnCount();loop++)
            myModel.setValueAt(textboxes[loop-1].getText(), table.getSelectedRow(), loop);
        
        writetoFile(JSONmaker(table,header,ColumnNames), fileLocation);
    }
    public void removeData(JTable table,String fileLocation, String header, String[] ColumnNames) 
    {
        //if table is empty theres nothing to delete
        if(table.getRowCount() == 0)
            return;
        
        if(table.getSelectedRow() == -1)
        {
             JOptionPane.showMessageDialog(null, "Please select a row first.","Row not selected", JOptionPane.ERROR_MESSAGE);
             return;
        }
            
        DefaultTableModel myModel = (DefaultTableModel)table.getModel();
        myModel.removeRow(table.getSelectedRow());
        
        
        
        if(table.getRowCount() == 0)
        {
            switch(header)
            {
                case "links" :  writetoFile("[{\"links\":[]},200]" , fileLocation);
                                return;
                                
                case "research" : writetoFile("[{\"research\":[]},200]" , fileLocation);
                                  return;  
                
                case "publications" : writetoFile("[{\"publications\":[]},200]" , fileLocation);
                                  return;
                                  
                case "tools" : writetoFile("[{\"tools\":[]},200]" , fileLocation);
                                  return;
                                  
                case "courses" : writetoFile("[{\"courses\":[]},200]" , fileLocation);
                                  return;
                                  
                case "people" : writetoFile("[{\"people\":[]},200]" , fileLocation);
                                  return;
                                  
                case "news" : writetoFile("[{\"news\":[]},200]" , fileLocation);
                                  return;                    
            }
            
        }
        

        writetoFile(JSONmaker(table,header,ColumnNames), fileLocation);

    }
    
    //this will create a JSON string according to the given jtable
    private String JSONmaker(JTable table,String header,String[] ColumnNames)
    {
        DefaultTableModel myModel = (DefaultTableModel)table.getModel();
        String JSONstring="[{\""+header+"\": [";
        String JSONpacket ="";
        
        for(int loop=0;loop<table.getRowCount();loop++)
        {
            JSONpacket = JSONpacket+"{" ;
            for(int innerLoop=0;innerLoop<ColumnNames.length;innerLoop++)
            {
                
                
                if(innerLoop == 0)
                    JSONpacket = JSONpacket + "\""+ ColumnNames[innerLoop]+"\": " +specialCharacterHandler((String)myModel.getValueAt(loop,innerLoop))+",";
                else
                    if(specialCharacterHandler((String)myModel.getValueAt(loop,innerLoop)) != null)
                        JSONpacket = JSONpacket + "\""+ ColumnNames[innerLoop]+"\": "+"\"" +specialCharacterHandler((String)myModel.getValueAt(loop,innerLoop))+"\",";
                    else
                        JSONpacket = JSONpacket + "\""+ ColumnNames[innerLoop]+"\": "+"" +specialCharacterHandler((String)myModel.getValueAt(loop,innerLoop))+",";
            }
            JSONpacket = removeLastChar(JSONpacket);
            JSONpacket = JSONpacket+"},";
            
        }
        JSONpacket = removeLastChar(JSONpacket);
        JSONpacket = JSONpacket + "]}, 200]";
        JSONstring = JSONstring+JSONpacket;
        
        return JSONstring;
    }
    
    private void writetoFile(String JSONstring, String fileLocation)
    {
        try
        {
            File myFile = new File(System.getProperty("user.dir")+OSPathHandler()+fileLocation);
            FileWriter  mywriter = new FileWriter(myFile,false); //overwrite the file
            BufferedWriter buffer = new BufferedWriter(mywriter);
            buffer.write(JSONstring);
            buffer.close();
            
        }catch (IOException x)
        {
             JOptionPane.showMessageDialog(null, fileLocation+" cannot be found under"+System.getProperty("user.dir")+"\\JSON"+" directory","File not found", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    public String removeLastChar(String phrase)
    {
        String rephrase = null;
        if (phrase != null && phrase.length() > 1)
            rephrase = phrase.substring(0, phrase.length() - 1);


            return rephrase;
    }
    
    
    public String specialCharacterHandler(String value)
    {
        String temp= null;
        //handling " , ' and \
        if(value != null)
        {
            //.replaceAll("\\\\", "\\\\\\\\")
            temp = value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll("\b", "\\\\b").replaceAll("\r", "\\\\r").replaceAll("\f", "\\\\f");
            return temp;
        }
        else
            return temp;
        
    }
    
    
    
  private String OSPathHandler()
  {
      String path;
      if(PlatformUtil.isMac() || PlatformUtil.isLinux() || PlatformUtil.isUnix())
            return "/JSON/";
        else
            return "\\JSON\\";
  }

}
