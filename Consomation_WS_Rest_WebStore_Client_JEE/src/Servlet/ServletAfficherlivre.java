package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.Beans.Livre;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@WebServlet("/ServletAfficherlivre")
public class ServletAfficherlivre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ServletAfficherlivre() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		//recuprer livre Id
		
		int id = Integer.parseInt((String) request.getParameter("id"));
		String stl;
		Client client = javax.ws.rs.client.ClientBuilder.newClient();
		WebTarget target2 = client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/afficherlivre/"+id);
		stl=target2.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);

		Jsonb jsonb = JsonbBuilder.create();
		Livre livre1=jsonb.fromJson(stl,Livre.class);
		
		
		
		
		

		HttpSession session = request.getSession(true);
//		request.getParameter("client");
		if(session.getAttribute("connectedUser")!=null) {
			request.setAttribute("Livre", livre1);
			this.getServletContext().getRequestDispatcher("/achterlivre.jsp").forward(request, response);
		}else {
			this.getServletContext().getRequestDispatcher("/buy.jsp").forward(request, response);
		}
		


		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
