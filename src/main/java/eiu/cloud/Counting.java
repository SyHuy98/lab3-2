package eiu.cloud;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/count" })
public class Counting  extends HttpServlet  {
    //  private final static String JDBC_URL = "jdbc:mysql://ec2-13-229-201-166.ap-southeast-1.compute.amazonaws.com:3306/" + "lab3-2";
    //private final static String DB_USER = "admin";
    //private final static String DB_PASSWORD = "Syhuy@98";
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/" + "cloudcomputing";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Syhuy@98";
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(resp.getOutputStream(), "UTF-8")
        );
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection mySQLClient = DriverManager.getConnection(
                    JDBC_URL,
                    DB_USER,
                    DB_PASSWORD
            );
            String query = "SELECT code  FROM course";
            PreparedStatement st = mySQLClient.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            String report = "";
            if (rs.next()) {
                // Retrieve data from the result set
                // System.out.println(rs.getString("count"));
                report = rs.getString("code");
            }
            resp.setContentType("text/plain");
            resp.setStatus(200);
            writer.write(report);
            writer.flush();
            writer.close();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


