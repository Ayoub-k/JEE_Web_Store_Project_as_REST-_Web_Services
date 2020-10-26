package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/ServletAdmin")
public class ServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletAdmin() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Client client = javax.ws.rs.client.ClientBuilder.newClient();
		WebTarget targeto =client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/afficherTClint");
		String str;
		str=targeto.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		List<com.Beans.Client> listeClient = mapper.readValue(str, new TypeReference<List<com.Beans.Client>>(){});
		
		Client client1 = javax.ws.rs.client.ClientBuilder.newClient();
		WebTarget target =client1.target("http://localhost:8080/Projet_Web_store_Rest/webstore/afficherTlivre");
		String str1;
		str1=target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(str1);
		
		List<com.Beans.Livre> listeLivre = mapper.readValue(str1, new TypeReference<List<com.Beans.Livre>>(){});
		
		
		request.setAttribute("listeClient", listeClient);
		request.setAttribute("listeLivre", listeLivre);
		
		
		request.getRequestDispatcher("/Admin.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
