import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Redirect Servlet based on credentials.txt verification
 * of user login request
 */

public class AuthenticationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ctx = request.getContextPath();

        String credentialsPath = "/WEB-INF/lib/credentials.txt";
        InputStream input = getServletContext().getResourceAsStream(credentialsPath);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            while(br.ready()) {
                String line = br.readLine();
                String[] auth = line.split(",", 2);

                if (auth[0].equals(username) && auth[1].equals(password)) {
                    if (username.equals("root")) {
                        response.sendRedirect(ctx + "/rootHome.jsp");
                    }

                    if (username.equals("client")) {
                        response.sendRedirect(ctx + "/clientHome.jsp");
                    }

                    if (username.equals("dataentry")) {
                        response.sendRedirect(ctx + "/dataentryHome.jsp");
                    }
                }
            }
            response.sendRedirect(ctx + "/errorpage.jsp");
        } catch (Exception e) {
            System.err.println("Error: cannot find file");
        }
    }
}