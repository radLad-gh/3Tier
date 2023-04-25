import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PartsInsertServlet extends HttpServlet {

    private final static String INSERT_INTO_PARTS = "INSERT INTO parts VALUES(?,?,?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pnum = req.getParameter("pnum");
        String pname = req.getParameter("pname");
        String color = req.getParameter("color");
        int weight = Integer.parseInt(req.getParameter("weight"));
        String city = req.getParameter("city");

        Properties props = retrieveCredentials("./webapps/3Tier/WEB-INF/lib/dataentry.properties");
        DBConnector conn = new DBConnector(props);
        int rowsUpdated = 0;
        PreparedStatement ps;

        try {
            ps = conn.getConnection().prepareStatement(INSERT_INTO_PARTS);
            ps.setString(1, pnum);
            ps.setString(2, pname);
            ps.setString(3, color);
            ps.setInt(4, weight);
            ps.setString(5, city);

            rowsUpdated = ps.executeUpdate();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/dataentryHome.jsp");

        session.setAttribute("parts_rows_updated", rowsUpdated);
        dispatcher.forward(req, resp);
    }

    public Properties retrieveCredentials(String file) {
        Properties props = null;
        try {
            FileInputStream fin;
            props = new Properties();
            fin = new FileInputStream(file);
            props.load(fin);
        } catch (final IOException readErr) {
            System.err.println("Failed to load credentials");
        }

        return props;
    }
}
