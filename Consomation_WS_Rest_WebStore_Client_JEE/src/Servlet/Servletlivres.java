package Servlet;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.Beans.Livre;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/Servletlivres")
public class Servletlivres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Servletlivres() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Client client = javax.ws.rs.client.ClientBuilder.newClient();
		String nomcatalogue=request.getParameter("idcatalogue");
		HttpSession s = request.getSession();
		s.setAttribute("typecategorie", nomcatalogue);
		
		WebTarget target1 = client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/afficher_livres/"+s.getAttribute("typecategorie"));
		String str=null;
		str=target1.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		
		FileInputStream fileInputStream=null;
		try {
			FileWriter file = new FileWriter("json.json");
			file.write(str);
			file.close();
			fileInputStream = new FileInputStream("json.json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		List<Livre> listelivres = mapper.readValue(fileInputStream, new TypeReference<List<Livre>>(){});
		
		for (Livre livre : listelivres) {
			System.out.println(livre.getAnnee());
		}
		request.setAttribute("listes_livre", listelivres);
		
		this.getServletContext().getRequestDispatcher("/les_livres.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
