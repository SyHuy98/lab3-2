package eiu.cloud;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/rdbconnectrole" })

public class TestRDBConnectRole extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));

		Connection connection;

		try {

			connection = RDSConnectionRole.getDBConnectionUsingIamRole();

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT 'Success!' FROM DUAL;");

			while (rs.next()) {

				String id = rs.getString(1);

				System.out.println(id); // Should print "Success!"

				writer.write(id);

				writer.flush();

			}

			// rs.close();

			stmt.close();

			connection.close();

			writer.close();

			// RDSConnection.clearSslProperties();

		} catch (Exception e) {

			writer.write(e.toString());

			writer.flush();

			writer.close();

		}

	}

}