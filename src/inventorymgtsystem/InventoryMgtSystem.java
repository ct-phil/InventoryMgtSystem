package inventorymgtsystem;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventoryMgtSystem extends JFrame implements ActionListener {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    JButton save,delete,update,newrec,back,next,exit;
    JTextField productname,quantity,price,sales,productid;
    JLabel inventory,productnamelbl,quantitylbl,pricelbl,saleslbl,productidlbl;
    
    public void DoConnect(){
      try{

            Class.forName("com.mysql.jdbc.Driver");
            
            String host = "jdbc:mysql://localhost/inventory"; //Database URL
            String uName = "root";
            String uPass = "";
 
           conn = DriverManager.getConnection(host,uName,uPass);
          
            
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            

            String SQL = "Select * from inventory";
            rs = stmt.executeQuery(SQL);
        
//Extract data from the result set/Process the results
            rs.next();
            productname.setText(rs.getString("product_name"));
            quantity.setText(Integer.toString(rs.getInt("quantity")));
            price.setText(Integer.toString(rs.getInt("price")));
            sales.setText(Integer.toString(rs.getInt("sales")));
            productid.setText(rs.getString("product_id"));
        }
        catch (SQLException | ClassNotFoundException err){
            JOptionPane.showMessageDialog(InventoryMgtSystem.this,err.getMessage());
        }
    }
         
    InventoryMgtSystem(){
        setDefaultCloseOperation(InventoryMgtSystem.EXIT_ON_CLOSE);
        setSize(500,500);
        setLayout(null);
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize(); this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
//Labels
        inventory=new JLabel("INVENTORY"); inventory.setBounds(200,0,100,25); add(inventory);
        productnamelbl=new JLabel("Product Name"); productnamelbl.setBounds(10,50,100,25); add(productnamelbl);
        quantitylbl=new JLabel("Quantity"); quantitylbl.setBounds(10,100,100,25); add(quantitylbl);
        pricelbl=new JLabel("Price"); pricelbl.setBounds(10,150,100,25); add(pricelbl);
        saleslbl=new JLabel("Sales"); saleslbl.setBounds(10,200,100,25); add(saleslbl);
        productidlbl=new JLabel("Assigned product ID"); productidlbl.setBounds(10,350,150,25); add(productidlbl);
        
//Text Fields
     productname=new JTextField(); productname.setBounds(151,50,100,25); add(productname);
        quantity=new JTextField(); quantity.setBounds(151,100,100,25); add(quantity); 
        price=new JTextField(); price.setBounds(151,150,100,25); add(price);
        sales=new JTextField(); sales.setBounds(151,200,100,25); add(sales);
       productid=new JTextField(); productid.setBounds(151,350,100,25); productid.setEditable(false); add(productid); 
                

//Buttons
        save=new JButton("Save"); save.setBounds(300,50,100,25); add(save); save.addActionListener(this);
        delete=new JButton("Delete"); delete.setBounds(300,100,100,25); add(delete); delete.addActionListener(this);
        update=new JButton("Update"); update.setBounds(300,150,100,25); add(update); update.addActionListener(this);
        newrec=new JButton("New"); newrec.setBounds(300,200,100,25); add(newrec); newrec.addActionListener(this);
        back=new JButton("Back"); back.setBounds(300,250,100,25); add(back); back.addActionListener(this);
        next=new JButton("Next"); next.setBounds(300,300,100,25); add(next); next.addActionListener(this);
        exit=new JButton("Exit"); exit.setBounds(300,400,100,25); add(exit); exit.addActionListener(this); 
        
        DoConnect();
        
        
    }
   
   
 public static void main (String[] args){
        InventoryMgtSystem sts= new InventoryMgtSystem();
        sts.setVisible(true);
    }
 
 public void actionPerformed(ActionEvent evt){
     //Save Button
        if(evt.getSource()==save){
            try{
                int qty = Integer.parseInt(quantity.getText());
                int prce = Integer.parseInt(price.getText());
                int sls = Integer.parseInt(sales.getText());
                
                stmt.executeUpdate("INSERT INTO inventory ( product_name, quantity, price, sales) VALUES ('"+productname.getText()+"','"+qty+"','"+prce+"','"+sls+"')");
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, "Record saved!");
                
                save.setEnabled(false);
            }
            catch(SQLException err){
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, err.getMessage());
                System.out.println(err.getMessage());
            }
            catch(NumberFormatException ev){
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, "Check that the quantity,price and sales are numbers only.");
                System.out.println(ev.getMessage());
            }
        }
//Delete Button
        if(evt.getSource()==delete){
            try{
                stmt.executeUpdate("delete from inventory where product_id='"+productid.getText()+"'");
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, "Record deleted!");
            
        
                rs=stmt.executeQuery("select * from inventory");
                rs.next();
            

                productname.setText(rs.getString("product_name"));
                quantity.setText(Integer.toString(rs.getInt("quantity")));
                price.setText(Integer.toString(rs.getInt("price")));
                sales.setText(Integer.toString(rs.getInt("sales")));
                productid.setText(rs.getString("product_id"));
            }
            catch(SQLException err){
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, err.getMessage());
                System.out.println(err.getMessage());
            }
        }
        
//Update Button
        if(evt.getSource()==update){
            try{
                stmt.executeUpdate("update inventory set product_name='"+productname.getText()+"',quantity='"+quantity.getText()+"',price='"+price.getText()+"',sales='"+sales.getText()+"' WHERE product_id ='"+productid.getText()+"'");
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, "Record updated!");
            }
            catch(SQLException err){
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, err.getMessage());
                System.out.println(err.getMessage());
            }
        }
        
//New Button
        if(evt.getSource()==newrec){
            productname.setText("");
            quantity.setText("");
            price.setText("");
            sales.setText("");
            productid.setText("");
            save.setEnabled(true);
        }
        
//Back Button
        if(evt.getSource()==back){
            try {
                if(rs.previous()){
                    productname.setText(rs.getString("product_name"));
                    quantity.setText(Integer.toString(rs.getInt("quantity")));
                    price.setText(Integer.toString(rs.getInt("price")));
                    sales.setText(Integer.toString(rs.getInt("sales")));
                    productid.setText(rs.getString("product_id"));
                }
                else{
                    rs.next();
                    JOptionPane.showMessageDialog(InventoryMgtSystem.this,"Start of File.");
                }
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, ex.getMessage());
            }
        }
        
//Next Button
        if(evt.getSource()==next){
            try {
                if(rs.next()){
                    productname.setText(rs.getString("product_name"));
                    quantity.setText(Integer.toString(rs.getInt("quantity")));
                    price.setText(Integer.toString(rs.getInt("price")));
                    sales.setText(Integer.toString(rs.getInt("sales")));
                    productid.setText(rs.getString("product_id"));
                }
                else{
                    rs.previous();
                    JOptionPane.showMessageDialog(InventoryMgtSystem.this,"End of File.");
                }
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(InventoryMgtSystem.this, ex.getMessage());
            }
        }
        
//Exit Button
        if(evt.getSource()==exit){
            System.exit(0);
        }
 
 }
}
