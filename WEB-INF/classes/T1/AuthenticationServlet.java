package T1;

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
    final static String CREDENTIALS = "credentials.txt";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        boolean validated = false;
        List<String> lines = new ArrayList<>();

        ServletContext ctx = getServletContext();
        String path = ctx.getRealPath(CREDENTIALS);
        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line =br.readLine())!=null) {
                lines.add(line);
            }
            if (lines.size() > 0) {
                validated = true;
            }
        } catch (Exception e) {
            System.err.println("Error: cannot find file");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>num in cred: " + lines.size() + "</p>");
        for (String i : lines) {
            out.println("<p>" + i + "</p>");
        }
        out.println("<h1>Hello " + firstName + " " + lastName + " World!</h1>");
        out.println("<p>" + (validated ? "Matched!" : "No Match :(") + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}