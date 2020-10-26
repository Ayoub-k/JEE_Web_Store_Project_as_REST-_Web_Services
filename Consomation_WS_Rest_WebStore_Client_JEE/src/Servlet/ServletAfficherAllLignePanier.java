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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.Beans.Panier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/ServletAfficherAllLignePanier")
public class ServletAfficherAllLignePanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletAfficherAllLignePanier() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = javax.ws.rs.client.ClientBuilder.newClient();

		
		WebTarget targeto = client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/AllPanierlivres");
		String str;
		str=targeto.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		
		
		List<Panier> listePanier = mapper.readValue(str, new TypeReference<List<Panier>>(){});
		
		double s=0;
		for (Panier panier : listePanier) {
			s=s+panier.getPrix_livre();
		}
		
		request.setAttribute("Prix_totale", s);
		request.setAttribute("listeslignePanier", listePanier);
		for (Panier panier : listePanier) {
			System.out.println(panier.getNom_livre());
		}
		request.getRequestDispatcher("/panier.jsp").forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
