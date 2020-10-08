import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearchLi")
public class SimpleFormSearchLi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormSearchLi() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name_keyword = request.getParameter("name_keyword");
		search(name_keyword, response);
	}

	void search(String namekeywordIn,HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Contact List Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transitional//en\">\n"; //
		out.println(docType + //
				"<html>\n" + //
				"<head><title>" + title + "</title></head>\n" + //
				"<body bgcolor=\"#f0f0f0\">\n" + //
				"<h1 align=\"center\">" + title + "</h1>\n");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			DBConnectionLi.getDBConnection(getServletContext());
			connection = DBConnectionLi.connection;
			
			String selectSQL = "SELECT * FROM myTable WHERE NAME LIKE ?";
			String name = "%" +namekeywordIn + "%";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, name);



			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String fullname = rs.getString("name").trim();
				String companyname = rs.getString("company").trim();
				String cellphone = rs.getString("cellphone").trim();
				String workphone = rs.getString("workphone").trim();
				String email = rs.getString("email").trim();


				if (namekeywordIn.isEmpty())
				{
					out.println("ID: " + id + "<br>");
					out.println("Name: " + fullname + "<br>");
					out.println("Company: " + companyname + "<br>");
					out.println("Cell Phone: " + cellphone + "<br>");
					out.println("Work Phone: " + workphone + "<br>");
					out.println("Email: " + email + "<br>");
					out.println("<br>");

				} 
				else if ((name.contains(namekeywordIn))) 
				{
					out.println("ID: " + id + "<br>");
					out.println("Name: " + fullname + "<br>");
					out.println("Company: " + companyname + "<br>");
					out.println("Cell Phone: " + cellphone + "<br>");
					out.println("Work Phone: " + workphone + "<br>");
					out.println("Email: " + email + "<br>");
					out.println("Email: " + email + "<br>");
					out.println("<br>");

				}
				
			}
			out.println("<a href=/webproject001/simpleFormSearchLi.html>Search Contact List</a> <br>");
			out.println("</body></html>");
			rs.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
