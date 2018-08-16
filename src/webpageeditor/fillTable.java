/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Kalana
 */
public class fillTable {
    
    //this will change tables font size
    public void initializetables(JTable[] table)
    {
        for(int loop=0;loop<table.length;loop++)
        {
            
           
            //make their cell uneditable
            //set table header size and font size
            table[loop].getTableHeader().setFont(new Font("Dialog",Font.PLAIN,15));
            //setting row sizes and font sizes
            table[loop].setFont(new Font("Dialog",Font.PLAIN,15));
            table[loop].setRowHeight(30);
        }
    }
    
    public void completeTable(JTable table,List<String> Columns, List<HashMap<String,String>> rowData)
    {
        
        System.out.println(rowData);
        Object row[] = new Object[Columns.size()];
        DefaultTableModel tmodel = (DefaultTableModel)table.getModel();
        //lets make sure we clear all the data in the table before we add new
        tmodel.setRowCount(0);
        
        for(int loop=0;loop<rowData.size();loop++)
        {
            for(int inner=0;inner<Columns.size();inner++)
                row[inner]= rowData.get(loop).get(Columns.get(inner));
            
           tmodel.addRow(row);
        }
        
    }
    
    //this will take given text areas and fill them with user clicked rows data
    public void fillTextAreas(JTable table,JTextArea areas[])
    {
        DefaultTableModel myModel = (DefaultTableModel)table.getModel();
        for(int loop=0;loop<areas.length;loop++)
        {
            areas[loop].setText((String)myModel.getValueAt(table.getSelectedRow(),loop+1));
        }
        System.err.println(table.getSelectedRow());
    }
    
    //this clear values from given text areas
    public void clearFields(JTextArea areas[])
    {
        for(int loop=0;loop<areas.length;loop++)
        {
            areas[loop].setText("");
        }
    }
    
    
    public void fillNewsLinkTable(JTable table, String JSONstring,String[] columns)
    {
        DefaultTableModel mymodel = (DefaultTableModel)table.getModel();
        JSONParser parser = new JSONParser(); 
        JSONArray a = null;
        //removing data every time
        mymodel.setRowCount(0);
        
        if(JSONstring == null || JSONstring.equals(""))
            return;
        
            try
            {
                a = (JSONArray)parser.parse(JSONstring);
                Object[] rows = new Object[columns.length];

                for(int loop=0;loop<a.size();loop++)
                {
                    for(int inner=0;inner<columns.length;inner++)
                        rows[inner] = ((JSONObject)a.get(loop)).get(columns[inner]);

                    mymodel.addRow(rows);
                }

            }catch (Exception x)
            {
                x.printStackTrace();
            }
   }
    

}
