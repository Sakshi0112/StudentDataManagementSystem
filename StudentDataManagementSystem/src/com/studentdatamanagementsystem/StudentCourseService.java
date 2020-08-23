package com.studentdatamanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentCourseService 
{	
	public void launchCourse() {
		Connection con=null;
		int courseId;
		Scanner sc = new Scanner(System.in);
		try {
			int count=0;
			boolean status=false;
			do
			{
		     System.out.println("Enter the Course Name out of following list: \nC \nJava \nJ2EE \nSpring \nHibernate");
	         String courseName = sc.next();
	         CourseName[] cn = CourseName.values();
	 		 for(CourseName c : cn)
	 		 {
	 			String s = c.toString();
	 			if(s.equalsIgnoreCase(courseName))
	 			{
	 				status=true;
	 				break;
	 			}
	 		 } 
	 	    if(!status)
	 	    {
	 	    	System.out.println("Enter Valid Course Name");
	 	    	count++;
	 	    }
	 	    else
	 	    {
	         System.out.println("Enter the duration for which you want to register for the course:");
	         int duration = sc.nextInt();
	         System.out.println("Enter the fees for the course:");
	         int fees = sc.nextInt();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String username = "hr";
			String password = "hr";
			con = DriverManager.getConnection(url,username,password);
			
			String sql1 = "select max(courseid) from coursedetails";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			if(rs.next())
			{
				courseId = rs.getInt(1);
				if(courseId==0)
				{
					courseId = 1;
				}
				else
				{
					courseId+=1;
				}
		 
			String sql = "insert into coursedetails values(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
		    ps.setInt(1,courseId);
		    ps.setString(2, courseName);
		    ps.setInt(3, duration);
		    ps.setInt(4, fees);
			int rows = ps.executeUpdate();
			if(rows>0){
				System.out.println("New Course added successfully!");
			}
	       }
	 	  }
		}while(status!=true && count<=1);
		} 
		catch(InputMismatchException e1) {
			System.out.println("Invalid Input...Please enter a valid input...!!!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
	
					e.printStackTrace();
				}
			}
		}
	
	}
	public ArrayList<Course> fetchAllCourseDetails()
	{
		ArrayList<Course> list =  new ArrayList<Course>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String username="hr";
			String password="hr";
			Connection con = DriverManager.getConnection(url,username,password);
			
			String sql = "select * from coursedetails";
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				Course c = new Course();
				c.setCourseId(rs.getInt(1));
				String name = (rs.getString(2));
				CourseName[] cn = CourseName.values();
		 		 for(CourseName cname : cn)
		 		 {
		 			String s = cname.toString();
		 			if(s.equalsIgnoreCase(name))
		 			{
		 				c.setCourseName(cname);
		 			}
		 		 } 
				c.setDuration(rs.getInt(3));
				c.setFees(rs.getInt(4));
				list.add(c);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		
	public Course fetchCourseDetails(int courseId) {
		
	try 
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String username="hr";
		String password="hr";
		Connection con = DriverManager.getConnection(url,username,password);
		String sql = "select * from coursedetails where courseid ="+courseId;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			Course c = new Course();
			c.setCourseId(rs.getInt(1));
			String name = (rs.getString(2));
			CourseName[] cn = CourseName.values();
		 	for(CourseName cname : cn)
		 	{
		 	    String s = cname.toString();
		 		if(s.equalsIgnoreCase(name))
		 		{
		 			c.setCourseName(cname);
		 		}
		   } 
		   c.setDuration(rs.getInt(3));
		   c.setFees(rs.getInt(4));
		   return c;
		}
	} 
	catch(InputMismatchException e1) {
		System.out.println("You have entered a wrong input please check your input...!!!");
	}
	catch (ClassNotFoundException e) 
	{
		e.printStackTrace();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	return null;
 }

	public ArrayList<Student> fetchStudentDetails(int courseId) {
	
		ArrayList<Student> list = new ArrayList<Student>();

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url="jdbc:oracle:thin:@localhost:1521:orcl";
				String username="hr";
				String password="hr";
				Connection con = DriverManager.getConnection(url,username,password);
				
				String sql = "select * from studentdetail where courseid ="+courseId;
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
				  while(rs.next())
				  {	
				   Student s = new Student();
				   s.setRegdId(rs.getInt(1));
				   s.setStudentName(rs.getString(2));
				   s.setAddress(rs.getString(3));
				   s.setContactNumber(rs.getLong(4));
				   s.setCourseId(rs.getInt(5));
				   s.setFeesPaid(rs.getInt(6));      
				   s.setDateOfAdmission(df.parse(rs.getString(7)));
				   s.setStartDate(df.parse(rs.getString(8)));
				   s.setEndDate(df.parse(rs.getString(9)));
				   list.add(s);
				 }  	
				return list;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	} 
}  