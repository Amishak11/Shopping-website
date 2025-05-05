import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("root");
        String password = request.getParameter("Amisha2005");

        // Simulate login validation
        if ("validUsername".equals(username) && "validPassword".equals(password)) {
            // Redirect to the Shop Now page
            response.sendRedirect("home.html");
        } else {
            // Redirect back to the login page with an error message
            response.sendRedirect("login.html?error=1");
        }
    }
}