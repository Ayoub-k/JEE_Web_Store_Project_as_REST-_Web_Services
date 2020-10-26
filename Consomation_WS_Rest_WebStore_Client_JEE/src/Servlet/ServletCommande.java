package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.Beans.Command;
import com.Beans.Panier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@WebServlet("/ServletCommande")
public class ServletCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServletCommande() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		HttpSession sessio = request.getSession(true);
		com.Beans.Client client=(com.Beans.Client)sessio.getAttribute("connectedUser");

		Command c = new Command();
		
		// recupere la listes des lignes panier 
		
		Client client1 = javax.ws.rs.client.ClientBuilder.newClient();


		WebTarget targeto = client1.target("http://localhost:8080/Projet_Web_store_Rest/webstore/AllPanierlivres");
		String str;
		str=targeto.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		
		
		List<Panier> listes_lignespaniers = mapper.readValue(str, new TypeReference<List<Panier>>(){});
		if(listes_lignespaniers!=null) {
			for (Panier panier : listes_lignespaniers) {
				c.setNomlivre(panier.getNom_livre());
				c.setEmailclient(client.getEmail());
				c.setQ(1);
				
				// serialisation 
				
				Jsonb jsonb = JsonbBuilder.create();
        		String json = jsonb.toJson(c);	
				// l ajout des commande
        		try {
        			URL url = new URL("http://localhost:8080/Projet_Web_store_Rest/webstore/ajoutercommand");
        	        HttpURLConnection con  = (HttpURLConnection) url.openConnection();
        	        con.setDoOutput(true);
        	        con.setRequestMethod("POST");
        	        con.setRequestProperty("Content-Type", "application/json");
//        	        con.setRequestProperty("Accept", "application/json");
        	        OutputStream os = con.getOutputStream();
        	        os.write(json.getBytes());
        	        os.close();
        	        os.flush();
        	        System.out.println(json.getBytes());
        			BufferedReader br = new BufferedReader(new InputStreamReader(
        					(con.getInputStream())));
        			String output;
        			System.out.println("Output from Server .... \n");
        			while ((output = br.readLine()) != null) {
        				System.out.println(output);
        			}
        			con.disconnect();
        		  } catch (MalformedURLException e) {
        			e.printStackTrace();
        		  } catch (IOException e) {
        			e.printStackTrace();
        		 }
        		
			}
			
			try {
				URL url = new URL("http://localhost:8080/Projet_Web_store_Rest/webstore/vider_panier");
		        HttpURLConnection con  = (HttpURLConnection) url.openConnection();
		        con.setDoOutput(true);
		        con.setRequestMethod("DELETE");
		        con.setRequestProperty("Accept", "application/json");
		        con.setRequestProperty("Content-Type", "application/json");
		    	BufferedReader br = new BufferedReader(new InputStreamReader(
						(con.getInputStream())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
				con.disconnect();
			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }
			
		}else {
			this.getServletContext().getRequestDispatcher("/panier.jsp").forward(request, response);
		}
		
		
		
		this.getServletContext().getRequestDispatcher("/command.jsp").forward(request, response);
	
		}
		
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
