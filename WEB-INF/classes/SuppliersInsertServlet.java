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

public class SuppliersInsertServlet extends HttpServlet {

    private final static String INSERT_INTO_SUPPLIERS = "INSERT INTO suppliers VALUES(?,?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String snum = req.getParameter("snum");
        String sname = req.getParameter("sname");
        int status = Integer.parseInt(req.getParameter("status"));
        String city = req.getParameter("city");

        Properties props = retrieveCredentials("./webapps/3Tier/WEB-INF/lib/dataentry.properties");
        DBConnector conn = new DBConnector(props);
        int rowsUpdated = 0;
        PreparedStatement ps;

        try {
            ps = conn.getConnection().prepareStatement(INSERT_INTO_SUPPLIERS);
            ps.setString(1, snum);
            ps.setString(2, sname);
            ps.setInt(3, status);
            ps.setString(4, city);

            rowsUpdated = ps.executeUpdate();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/dataentryHome.jsp");

        session.setAttribute("suppliers_rows_updated", rowsUpdated);
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
