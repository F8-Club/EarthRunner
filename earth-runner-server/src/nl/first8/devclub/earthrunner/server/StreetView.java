package nl.first8.devclub.earthrunner.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@WebServlet(urlPatterns="*.html")
public class StreetView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init() throws ServletException {
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		Template t = Velocity.getTemplate("streetview.vm");
		VelocityContext context = new VelocityContext();
		t.merge(context, resp.getWriter());
		resp.getWriter().close();
	}

}
