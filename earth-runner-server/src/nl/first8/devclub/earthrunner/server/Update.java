package nl.first8.devclub.earthrunner.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Call with GET or POST and parameter 'speed' to change speed at the server.
 */
@WebServlet("/update")
public class Update extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PARAM_SPEED = "speed";
    private static final String PARAM_UUID = "uuid";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter(PARAM_UUID);
        String speed = req.getParameter(PARAM_SPEED);
        Float floatSpeed = Float.valueOf(speed);
        State.setSpeed(uuid, floatSpeed);
    }

}
