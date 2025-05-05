import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get user input from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        // Database credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/shop";
        String dbUser = "root";
        String dbPassword = "Amisha2005"; // Replace with your MySQL password

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Check if the email already exists
            String checkQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            if (checkStmt.executeQuery().next()) {
                out.println("<script>alert('Email already registered!'); window.location='signup.html';</script>");
                return;
            }

            // SQL query to insert new user
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password); // In production, use password hashing (e.g., BCrypt)

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                out.println("<script>alert('Signup Successful!'); window.location='login.html';</script>");
            } else {
                out.println("<script>alert('Signup Failed! Try again.'); window.location='signup.html';</script>");
            }

            // Close connections
            pst.close();
            con.close();

        } catch (Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='signup.html';</script>");
            e.printStackTrace();
        }
    }
}
