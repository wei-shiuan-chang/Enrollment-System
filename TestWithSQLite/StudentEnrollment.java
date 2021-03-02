// package StudentEnrollment;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class StudentEnrollment {

	private JFrame frame;
	private JTextField IncommingName;
	private JTextField CurrentName;
	private JTextField CourseEnroll;
	private JTextField CourseAdd;
	private JTextField CourseStudent;
	private JTextField CourseDay;
	private JTextField CourseTime;
	
	Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentEnrollment window = new StudentEnrollment();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public StudentEnrollment() throws SQLException {
		connection();
		initialize();
		
		/*
            if (myRs != null) {
                myRs.close();
            }
            
            if (myStmt != null) {
                myStmt.close();
            }
            
            if (myConn != null) {
                myConn.close();
            }
        */
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//JLabel newStudent, currerntStudent, studentEnrollCourse, addCourse, day, time, courseList;
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 468, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 272, 454, 150);
		frame.getContentPane().add(scrollPane);
		
		JLabel ResultSpace = new JLabel("");
		scrollPane.setViewportView(ResultSpace);
		ResultSpace.setVerticalAlignment(SwingConstants.TOP);
		ResultSpace.setBackground(Color.PINK);
		
		JLabel lblNewLabel = new JLabel("Incomming Student");
		lblNewLabel.setBounds(6, 6, 130, 23);
		frame.getContentPane().add(lblNewLabel);
		
		IncommingName = new JTextField();
		IncommingName.setText("Name");
		IncommingName.setBounds(6, 27, 130, 26);
		frame.getContentPane().add(IncommingName);
		IncommingName.setColumns(10);
		
		CurrentName = new JTextField();
		CurrentName.setText("Name");
		CurrentName.setToolTipText("");
		CurrentName.setBounds(6, 86, 130, 26);
		frame.getContentPane().add(CurrentName);
		
		
		JButton EnrollButton = new JButton("Enroll");
		EnrollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
		            // 1. Get a connection to database
		            //myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root" , "weiasdfshiuan32");
		            
		            // 2. Create a statement
		            //myStmt = myConn.createStatement();
		            
		            // 3. Execute SQL query
		            /*
		            String query = "INSERT Students VALUES (DEFAULT, '"+IncommingName.getText()+"')";

		            Statement stmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root" , "weiasdfshiuan32")
		                .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		            stmt.executeUpdate(query);
		            */
		            myStmt.executeUpdate("INSERT INTO Students (name) VALUES ('"+IncommingName.getText()+"')");
		            
		            //CurrentName.addItem(IncommingName.getText());
		            //CourseStudent.addItem(IncommingName.getText());
		            
		        }
		        catch (Exception exc) {
		            exc.printStackTrace();
		        }
				
			}
		});
		EnrollButton.setBounds(148, 27, 117, 29);
		frame.getContentPane().add(EnrollButton);
		
		JComboBox comboBox = new JComboBox<>();
		comboBox.setBounds(140, 87, 117, 27);
		frame.getContentPane().add(comboBox);
		
		String[] s = {"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		for(int i = 0; i < s.length; i++) {
			comboBox.addItem(s[i]);
		}
		
		JButton ScheduleButton = new JButton("Schedule");
		ScheduleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedItem() == "All") {
					try {
						myRs = myStmt.executeQuery("SELECT name,day,time FROM Courses WHERE course_id IN ( SELECT course_id FROM Enrollment WHERE student_id =  (SELECT student_id FROM Students WHERE name = '"+CurrentName.getText()+"'))");
						StringBuilder result = new StringBuilder();
						result.append("<html>Enrolled courses:<br/>");
						System.out.println(myRs.getRow());
						
						while (myRs.next()) {
							System.out.println("here");
							result.append(myRs.getString("name") + ", " + myRs.getString("day") + ", " + myRs.getString("time")+"<br/>");
			                
							System.out.println(myRs.getString("name") + ", " + myRs.getString("day") + ", " + myRs.getString("time")+"<br/>");
							
			            }
						result.append("</html>");
						ResultSpace.setText(result.toString());
						
						/*
						ResultSpace.setText("");
						
						while (myRs.next()) {
			                
			                System.out.println(myRs.getString("name") + ", " + myRs.getString("day") + ", " + myRs.getString("time"));
			                
			                
			            }*/
			            
			        }
			        catch (Exception exc) {
			            exc.printStackTrace();
			        }
				}else{
					
					try {
						myRs = myStmt.executeQuery("SELECT name, time\n"
								+ "FROM Courses\n"
								+ "WHERE course_id IN\n"
								+ "	(\n"
								+ "	SELECT course_id\n"
								+ "	FROM Enrollment\n"
								+ "	WHERE student_id = \n"
								+ "		(SELECT student_id\n"
								+ "		FROM Students\n"
								+ "		WHERE name = '"+CurrentName.getText()+"'\n"
								+ "		)\n"
								+ "	)\n"
								+ "AND day = '"+comboBox.getSelectedItem()+"';");
						StringBuilder result = new StringBuilder();
						result.append("<html>Enrolled courses in"+comboBox.getSelectedItem()+":<br/>");
						System.out.println(myRs.getRow());
						
						while (myRs.next()) {
							System.out.println("here");
							result.append(myRs.getString("name") + ", " + myRs.getString("time")+"<br/>");
			                
							System.out.println(myRs.getString("name") + ", " + myRs.getString("time")+"<br/>");
							
			            }
						result.append("</html>");
						ResultSpace.setText(result.toString());
						
						
			            
			        }
			        catch (Exception exc) {
			            exc.printStackTrace();
			        }
					
					
				}
				
				
				
			}
		});
		ScheduleButton.setBounds(269, 86, 117, 29);
		frame.getContentPane().add(ScheduleButton);
		
		
		
		JLabel lblCurrentStudent = new JLabel("Current Student");
		lblCurrentStudent.setBounds(6, 65, 130, 23);
		frame.getContentPane().add(lblCurrentStudent);
		
		JButton EnrollCourseButton = new JButton("Enroll to a new course");
		EnrollCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
		            myStmt.executeUpdate("INSERT INTO Enrollment SELECT course_id, student_id FROM Courses c, Students s WHERE c.name = '"+CourseEnroll.getText()+"' AND s.name = '"+CurrentName.getText()+"'");
		            
		            
		            
		        }
		        catch (Exception exc) {
		            exc.printStackTrace();
		        }
			}
		});
		EnrollCourseButton.setBounds(140, 113, 163, 29);
		frame.getContentPane().add(EnrollCourseButton);
		
		CourseEnroll = new JTextField();
		CourseEnroll.setText("Course");
		CourseEnroll.setBounds(6, 113, 130, 26);
		frame.getContentPane().add(CourseEnroll);
		
		JLabel lblAddANew = new JLabel("New Course");
		lblAddANew.setBounds(6, 154, 130, 23);
		frame.getContentPane().add(lblAddANew);
		
		CourseAdd = new JTextField();
		CourseAdd.setText("Course");
		CourseAdd.setColumns(10);
		CourseAdd.setBounds(6, 175, 130, 26);
		frame.getContentPane().add(CourseAdd);
		
		JButton AddButton = new JButton("Add");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
		            myStmt.executeUpdate("INSERT INTO Courses (name, day, time) VALUES ('"+CourseAdd.getText()+"','"+CourseDay.getText()+"','"+CourseTime.getText()+"')");
		            //CourseEnroll.addItem(CourseAdd.getText());
		            //CourseStudent.addItem(CourseAdd.getText());
		        }
		        catch (Exception exc) {
		            exc.printStackTrace();
		        }
				
			}
		});
		AddButton.setBounds(352, 175, 117, 29);
		frame.getContentPane().add(AddButton);
		
		JLabel lblCurrentCourse = new JLabel("Current Course");
		lblCurrentCourse.setBounds(6, 213, 130, 23);
		frame.getContentPane().add(lblCurrentCourse);
		
		CourseStudent = new JTextField();
		CourseStudent.setText("Course");
		CourseStudent.setBounds(6, 234, 130, 26);
		frame.getContentPane().add(CourseStudent);
		
		JButton ListButton = new JButton("Take a look");
		ListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				try {
					myRs = myStmt.executeQuery("SELECT name \n"
							+ "				FROM Students\n"
							+ "				WHERE student_id IN\n"
							+ "				(\n"
							+ "				SELECT student_id\n"
							+ "				FROM Enrollment\n"
							+ "				WHERE course_id = \n"
							+ "				(\n"
							+ "				SELECT course_id\n"
							+ "				FROM Courses\n"
							+ "				WHERE name = '"+CourseStudent.getText()+"'\n"
							+ "				)\n"
							+ "				)");
					StringBuilder result = new StringBuilder();
					result.append("<html>Enrolled students:<br/>");
					System.out.println(myRs.getRow());
					
					while (myRs.next()) {
						
						result.append(myRs.getString("name")+"<br/>");
		                
		            }
					result.append("</html>");
					ResultSpace.setText(result.toString());
					
					/*
					ResultSpace.setText("");
					
					while (myRs.next()) {
		                
		                System.out.println(myRs.getString("name") + ", " + myRs.getString("day") + ", " + myRs.getString("time"));
		                
		                
		            }*/
		            
		        }
		        catch (Exception exc) {
		            exc.printStackTrace();
		        }
				
			}
		});
		ListButton.setBounds(148, 234, 117, 29);
		frame.getContentPane().add(ListButton);
		
		CourseDay = new JTextField();
		CourseDay.setText("Day");
		CourseDay.setColumns(10);
		CourseDay.setBounds(140, 175, 66, 26);
		frame.getContentPane().add(CourseDay);
		
		CourseTime = new JTextField();
		CourseTime.setText("Time");
		CourseTime.setColumns(10);
		CourseTime.setBounds(210, 175, 130, 26);
		frame.getContentPane().add(CourseTime);
		
		
		
		
		
		
		
	}
	
public void connection() throws SQLException {
        //http://www.luv2code.com/2014/03/21/connect-to-mysql-database-with-java-jdbc/
		
        
        
        try {
            // 1. Get a connection to database
//            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/enrollment_list?serverTimezone=UTC", "root" , "weiasdfshiuan32");
        	myConn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            
            // 2. Create a statement
            myStmt = myConn.createStatement();

            myStmt.executeUpdate("drop table if exists Courses");
        	myStmt.executeUpdate("drop table if exists Students");
        	myStmt.executeUpdate("drop table if exists Enrollment");


            myStmt.executeUpdate("CREATE TABLE IF NOT EXISTS Courses ( course_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(45) NOT NULL, day VARCHAR(100) NOT NULL, time  VARCHAR(100) NOT NULL )");
            myStmt.executeUpdate("CREATE TABLE IF NOT EXISTS Students (student_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(45) NOT NULL)");
            myStmt.executeUpdate("CREATE TABLE IF NOT EXISTS Enrollment (course_id INT NOT NULL, student_id INT NOT NULL, CONSTRAINT Enrollment_fk_Courses FOREIGN KEY (course_id) REFERENCES Courses(course_id), CONSTRAINT Enrollment_fk_Students FOREIGN KEY (student_id) REFERENCES Students(student_id))");


            
            // 3. Execute SQL query
            myRs = myStmt.executeQuery("SELECT name,day,time FROM Courses WHERE course_id IN ( SELECT course_id FROM Enrollment WHERE student_id = (SELECT student_id FROM Students WHERE name = 'SHE' ) )");
            
            
            
            
            // 4. Process the result set
            while (myRs.next()) {
                
                System.out.println(myRs.getString("name") + ", " + myRs.getString("day"));
                
            }
            
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        
    }
}
