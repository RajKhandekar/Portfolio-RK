package com.registration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		Connection con = null;
		try {
			// load and register the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establish the Connection
			String url="jdbc:mysql://localhost:3306/company?useSSL=false";
			String user="root";
			String password="root1369";
			con=DriverManager.getConnection(url,user,password);
			
			PreparedStatement pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");
			//jdbc from 1 indexing start
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int rowCount=pst.executeUpdate();
			dispatcher=request.getRequestDispatcher("registration.jsp");
			if(rowCount>0) {
				request.setAttribute("status","success");
			}
			else {
				request.setAttribute("status","failed");
			}
			dispatcher.forward(request, response);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
		    try {
		        if (con != null) {
		            con.close();
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		}	
	}
}
