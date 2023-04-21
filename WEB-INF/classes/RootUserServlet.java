import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.Properties;

/**
 * Class responsible for root user database access
 */
public class RootUserServlet extends HttpServlet {

    private DBConnector conn;
    private static final String ctx = "/3Tier";

    public RootUserServlet() {

    }

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        Properties props = retrieveCredentials(ctx + "/WEB-INF/lib/root.properties");
        conn = new DBConnector(props);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResultSetMetaData meta = conn.query(req.getParameter("query"));
        if (meta == null) {
            resp.setStatus(500);
            resp.sendRedirect(ctx + "/errorpage.jsp");
        }
        String res = conn.getResultSet();
        conn.disconnect();
    }

    public Properties retrieveCredentials(String file) {
        Properties props = null;
        try {
            FileInputStream fin;
            props = new Properties();
            fin = new FileInputStream(file);
            props.load(fin);
        } catch (final IOException readErr) {
            JOptionPane.showMessageDialog(null, readErr.getMessage(), "Could not load credentials from file", JOptionPane.ERROR_MESSAGE);
        }

        return props;
    }
}