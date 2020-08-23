package com.studentdatamanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;

public class StudentManager {
	
	private int regdId;
	private String studentName;
	private String address;
	private long contactNumber;
	private int courseId;
	private int feePaid;
	private String startDate;
	private String endDate;
	
	public void enrollStudent()
	{
	  SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
	  Scanner sc= new Scanner(System.in);
	  Connection con=null;
	  try 
	  {
         Class.forName("oracle.jdbc.driver.OracleDriver");
		 String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		 String username = "hr";
		 String password = "hr";
		 con = DriverManager.getConnection(url,username,password);
			
		 String sql1 = "select max(regdid) from studentdetail";
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery(sql1);
		 if(rs.next())
		 {
			regdId = rs.getInt(1);
			if(regdId==0)
			{
			   regdId = 1;
			}
			else
			{
				regdId+=1;
	    	}
	     
	 	 System.out.println("Enter Name:");
		 studentName=sc.next();
			
		 System.out.println("Enter Address:");
		 address=sc.next();
			
		 System.out.println("Enter Contact number");
		 contactNumber=sc.nextLong();
		 boolean status = false;
		 int count = 1;
		 do{
		      System.out.println("Enter the course ID: ");
		      courseId = sc.nextInt();
		      CourseValidator cv = new CourseValidator();
		      if(!cv.validateCourse(courseId))
		      {
		        System.out.println("Enter Valid Course Id");
		        count++;
		      }	
		      else{
			        status = true;
		          }
		    }while(!status && count==2);
		 if(count<=2)
		 {
			 String sql2 = "select fees from coursedetails where courseid="+courseId;
			 Statement st2 = con.createStatement();
			 ResultSet rs2 = st2.executeQuery(sql2);
			 if(rs2.next())
			 {
			    feePaid = rs2.getInt(1);
			    DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
		        Date dateOfAdmission1=new Date();
			    System.out.println("Date of Admission is:"+(df.format(dateOfAdmission1)));
			    String dateOfAdmission=dateFormat.format(dateOfAdmission1);
			    System.out.println("Enter start date of the course:");
			    String startDate1=sc.next();
			    Date startDate2=null;
			    try 
			    {
			    	startDate2=dateFormat.parse(startDate1);
			    }
			    catch(ParseException e)
			    {
				   System.out.println("parsing error");
			    }
			    if(!df.format(startDate2).equals(startDate1)) 
			    {
			       System.out.println("Invalid date format");
			    }
			    else 
			    {
			       if(startDate2.before(dateOfAdmission1)) 
			       {
			         System.out.println("Start date cannot be before date of admission");
					   //control should be transfered back to System.out.println("Enter start date of the course:");
		           }
		           else 
		           {
			           startDate=dateFormat.format(startDate2).toString();   
			           System.out.println("Enter end date of the course:");
			           String endDate1=sc.next();
			           Date endDate2=null;
			           try 
			           {
				            endDate2=dateFormat.parse(endDate1);
			           }
			           catch(ParseException e)
			           {
			             e.printStackTrace();
			           }
			           if(!df.format(endDate2).equals(endDate1)) 
			           {
			             System.out.println("Invalid date format");
		               }
			           else 
			           {
			              if(endDate2.before(startDate2)) 
			            {
				           System.out.println("End Date cannot be before start date");
			            }
			            else 
			            {  
					       endDate=dateFormat.format(endDate2);
			            }   
			           }
				       String sql = "insert into studentdetail values(?,?,?,?,?,?,?,?,?)"; 
			           PreparedStatement ps = con.prepareStatement(sql);
			           ps.setInt(1,regdId);
			           ps.setString(2,studentName);
			           ps.setString(3, address);
			           ps.setLong(4,contactNumber);
			           ps.setInt(5, courseId);
			           ps.setInt(6, feePaid);
			           ps.setString(7, dateOfAdmission);
			           ps.setString(8,startDate);
			           ps.setString(9, endDate);
			           int rows = ps.executeUpdate();
			           if(rows>0)
			           {
			        	   System.out.println("Student enrolled for the course successfully!");
			           }
		           }
			    }
		      }
		    }
		  }
	    }
	    catch (Exception e) 
	    {
		    e.printStackTrace();
	    }
	    finally
	    {
		   if(con!=null)
		   {
			 try 
			 {
			    con.close();
			 } catch (SQLException e) 
			 {
				 e.printStackTrace();
			 }
		   }		
         } 
    }
	
	public void fetchStudentDetails(int regdId) 
	{
		//select * from StudentDetails where courseId = courseId;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String username="hr";
			String password="hr";
			Connection con = DriverManager.getConnection(url,username,password);
			
			String sql = "select * from studentdetail where regdid ="+regdId;
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				System.out.println("Registeration ID :"+rs.getInt(1));
				System.out.println("Student Name: "+rs.getString(2));
				System.out.println("Address: "+rs.getString(3));
				System.out.println("Contact No: "+rs.getLong(4));
				System.out.println("Course Id: "+rs.getInt(5));
				System.out.println("Fees Paid: "+rs.getInt(6));
				System.out.println("Admission date: "+rs.getString(7));
				System.out.println("Course start date: "+rs.getString(8));
				System.out.println("Course end date: "+rs.getString(9));
				System.out.println("-----------------------------------------------------------");
			}
			else
			{
				System.out.println("Enter the registeration id again");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<Student> viewStudentDetails(int courseId)
	{
		ArrayList<Student> stud = new ArrayList<Student>();
		CourseValidator cv = new CourseValidator();
		StudentCourseService scs = new StudentCourseService();
		if(!cv.validateCourse(courseId))
		{
			System.out.println("Invalid Id");
			return null;
		}
		stud = scs.fetchStudentDetails(courseId);
		if(stud!=null)
		{
			return stud;
		}
		System.out.println("Enter valid course id");
		return null;
	}
}