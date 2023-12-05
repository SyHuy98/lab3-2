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
public class Counting extends HttpServlet {
	/*
	 * private final static String JDBC_URL =
	 * "jdbc:mysql://cloudcomp.cxevftgvdyfx.ap-southeast-1.rds.amazonaws.com:3306/cloudcomp1011";
	 * private final static String DB_USER = "admin"; private final static String
	 * DB_PASSWORD = "Syhuy1998";
	 */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));

        try {
            Connection mySQLClient = RDSConnectionRole.getDBConnectionUsingIamRole();
            String query = "SELECT code, name FROM course"; // Update the query to select both columns
            PreparedStatement st = mySQLClient.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            StringBuilder report = new StringBuilder();
            while (rs.next()) {
                // Retrieve data from the result set for each row
                String code = rs.getString("code");
                String name = rs.getString("name");

                // Append the code and name to the response
                report.append("Code: ").append(code).append(", Name: ").append(name).append("\n");
            }

            resp.setContentType("text/plain");
            resp.setStatus(200);
            writer.write(report.toString());
            writer.flush();
            writer.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}


