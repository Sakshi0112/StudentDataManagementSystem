
package com.studentdatamanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseValidator {

	public boolean validateCourse(int courseId)
	{
		Connection con  = null;
	    try 
	    {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String username = "hr";
			String password = "hr";
			con = DriverManager.getConnection(url, username,password);
			String sql = "select * from coursedetails where courseid ="+courseId;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) 
			{
			  return true;
		    }
		} 
	    catch (ClassNotFoundException e1) 
	    {
		    e1.printStackTrace();
		} 
	    catch (SQLException e1) 
	    {
		    e1.printStackTrace();
		} 
		finally
		{
		   if(con!=null)
		   {
			  try 
			  {
				con.close();
			  } 
			  catch (SQLException e) 
			  {
				e.printStackTrace();
			  }
			}
		}
		return false;
	}
}
