package com.studentdatamanagementsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentDataManagementSystemMain 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int choice=5,ch;
		do
		{
		
		 try
		 {
			System.out.println("Menu :");
			System.out.println(" 1.Launch New Course"+"\n 2.Enroll Student"+"\n 3.View Student Details"+"\n 4.View Course Details \n 5.Exit");
			System.out.println("Enter your choice :");
			choice = sc.nextInt();
			switch(choice)
			{
			    case 1 : StudentCourseService scs = new StudentCourseService();
				         scs.launchCourse();
				         
						 break;
					
				case 2 : StudentManager sm = new StudentManager();
					     sm.enrollStudent();
					    
		                 break;
					
				case 3 :
					System.out.println("Choose an option :");
					System.out.println(" 1. View details using Course ID"+"\n 2. View details using Registration ID");
					ch = sc.nextInt();
					switch(ch)
					{
						
					   case 1 : System.out.println("Enter the Course Id");
	           		            int courseId = sc.nextInt();	  
								sm = new StudentManager();
								ArrayList<Student> student =sm.viewStudentDetails(courseId);
								if(student!=null)
								{
									
						         for(Student s : student)
						         {
						           int day = s.getDateOfAdmission().getDate(); 
						           int month = s.getDateOfAdmission().getMonth()+1;
						           int year = s.getDateOfAdmission().getYear()+1900;
						           int day1 = s.getStartDate().getDate(); 
						           int month1 = s.getStartDate().getMonth()+1;
						           int year1 = s.getStartDate().getYear()+1900;
						           int day2 = s.getEndDate().getDate(); 
						           int month2 = s.getEndDate().getMonth()+1;
						           int year2 = s.getEndDate().getYear()+1900;
						           System.out.println("Registration ID: "+s.getRegdId()+
						            	  		      "\nStudent Name: "+s.getStudentName()+
						            	  		      "\nAddress: "+s.getAddress()+
						            	  		      "\nContact Number:"+s.getContactNumber()+
						            	  		      "\nFees Paid:"+s.getFeesPaid()+
						            	  		      "\nDate of admission:"+day+"-"+month+"-"+year+
						            	  		      "\nStart Date:"+day1+"-"+month1+"-"+year1+
						            	  		      "\nEnd Date:"+day2+"-"+month2+"-"+year2);
						       	   System.out.println("--------------------------------------------------------------");
						          }
								}
								 break;
							
					    case 2 : System.out.println("Enter the Register Id");
 		                         int regdId = sc.nextInt();
 		                         sm = new StudentManager();
	                             sm.fetchStudentDetails(regdId);
							     break;
				 	}
				    break;
			   
					
			   case 4 : 
				       System.out.println("Choose an option :");
				       System.out.println(" 1. View details of all courses"+"\n 2. View details of any one course using courseId");
				       ch = sc.nextInt();
				       switch(ch)
				       {
					      case 1 : scs = new StudentCourseService();
					               ArrayList<Course> course =scs.fetchAllCourseDetails();
					               for(Course c : course)
					               {
					            	 System.out.println();System.out.println("Course ID: "+c.getCourseId()+" \nCourse Name: "+c.getCourseName()+" \nDuration: "+c.getDuration()+" \nFees:"+c.getFees());
					       			 System.out.println("--------------------------------------------------------------");
					               }
							       break;
					
					      case 2 : int count=0;
					               boolean status=false;
							       do
							       { 
							    	  System.out.println("Enter the Course Id");
	                                  int courseId = sc.nextInt();
				                      scs = new StudentCourseService();
                                      Course c = scs.fetchCourseDetails(courseId);
                                      if(c!=null)
                                      {
                                    	 status=true;
                                	     System.out.println();System.out.println("Course ID: "+c.getCourseId()+" \nCourse Name: "+c.getCourseName()+" \nDuration: "+c.getDuration()+" \nFees:"+c.getFees());
							          }
							          if(!status)
								 	        {
								 	    	   System.out.println("Enter Valid Course Name");
								 	    	   count++;
								 	        }	 
							       }while(status!=true && count<=1);
							  	  break;
				       }
			}
		 }
		 catch (InputMismatchException e)
		 {
	        System.out.println("Invalid Input...!!!!");
		 }
		}while(choice<5);
		System.out.println("Thank you for using the application!");
	}
}
