

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    // Obtener la sesión actual o crear una nueva
    HttpSession session = request.getSession(true);

    // Inicializar el número si no está configurado
    if (session.getAttribute("randomNumber") == null) {
        int randomNumber = (int) (Math.random() * 100) + 1;
        session.setAttribute("randomNumber", randomNumber);
    }

    String message = "";
    if (session.getAttribute("message") != null) {
        message = (String) session.getAttribute("message");
    }

    // Generar el HTML con imagen de fondo directamente en línea
    response.getWriter().println("<html><head><title>Adivina el número</title></head>");
    response.getWriter().println("<body style='background-image: url(\"images/images.jpg\"); background-size: cover; text-align: center; color: white;'>");

    response.getWriter().println("<h1>Adivina el número entre 1 y 100</h1>");

    response.getWriter().println("<form action='game' method='POST'>");
    response.getWriter().println("Tu adivinanza: <input type='number' name='guess' required>");
    response.getWriter().println("<input type='submit' value='Adivinar'>");
    response.getWriter().println("</form>");
    response.getWriter().println("<p>" + message + "</p>");
    response.getWriter().println("</body></html>");
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int randomNumber = (int) session.getAttribute("randomNumber");

        // Obtener la adivinanza del jugador
        int guess = Integer.parseInt(request.getParameter("guess"));

        // Determinar si la adivinanza es correcta, mayor o menor
        if (guess == randomNumber) {
            session.setAttribute("message", "¡Felicidades! Has adivinado el número.");
            session.removeAttribute("randomNumber");  // Reiniciar el juego
        } else if (guess < randomNumber) {
            session.setAttribute("message", "El número es mayor. Intenta nuevamente.");
        } else {
            session.setAttribute("message", "El número es menor. Intenta nuevamente.");
        }

        // Redirigir a la misma página para mostrar el mensaje
        response.sendRedirect("game");
    }
}
