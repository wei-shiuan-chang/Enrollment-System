//package databaseprogram;
import java.sql.*;
import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;
public class main extends Frame{
    public static void main(String[] args) throws SQLException {
        
        JFrame frame = new JFrame("EnrollDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(800, 500));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        try {
            // 1. Get a connection to database
//            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root" , "weiasdfshiuan32");
            
            // 2. Create a statement
            myStmt = myConn.createStatement();
            
            // 3. Execute SQL query
            myRs = myStmt.executeQuery("SELECT * FROM testtable1");
            
            // 4. Process the result set
            while (myRs.next()) {
                
                System.out.println(myRs.getString("id") + ", " + myRs.getString("name"));
                
            }
            
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (myRs != null) {
                myRs.close();
            }
            
            if (myStmt != null) {
                myStmt.close();
            }
            
            if (myConn != null) {
                myConn.close();
            }
        }
    }
}