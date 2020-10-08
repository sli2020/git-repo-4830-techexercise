
/**
 * @file SimpleFormInsertLi.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsertLi")
public class SimpleFormInsertLi extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsertLi() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String fullname = request.getParameter("fullname");
      String companyname = request.getParameter("companyname");
      String cellphone = request.getParameter("cellphone");
      String workphone = request.getParameter("workphone");
      String email = request.getParameter("email");

      Connection connection = null;
      String insertSql = " INSERT INTO myTable (id, NAME, COMPANY, CELLPHONE, WORKPHONE, EMAIL) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionLi.getDBConnection(getServletContext());
         connection = DBConnectionLi.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, fullname);
         preparedStmt.setString(2, companyname);
         preparedStmt.setString(3, cellphone);
         preparedStmt.setString(4, workphone);
         preparedStmt.setString(5, email);


         preparedStmt.execute();
         System.out.println("inserted into table");
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Contact to the Contact List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Name</b>: " + fullname + "\n" + //
            "  <li><b>Company</b>: " + companyname + "\n" + //
            "  <li><b>Work Phone</b>: " + cellphone + "\n" + //
            "  <li><b>Cell PhoneAddress</b>: " + workphone + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //

            

            "</ul>\n");

      out.println("<a href=/webproject001/simpleFormSearchLi.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
