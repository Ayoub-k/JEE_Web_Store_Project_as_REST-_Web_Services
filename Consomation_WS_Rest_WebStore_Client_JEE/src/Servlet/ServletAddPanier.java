package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.Beans.Livre;
import com.Beans.Panier;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@WebServlet("/ServletAddPanier")
public class ServletAddPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletAddPanier() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id =Integer.parseInt(request.getParameter("id"));

		String jsonlivre;
		Client client = javax.ws.rs.client.ClientBuilder.newClient();
		WebTarget target2 = client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/afficherlivre/"+id);
		jsonlivre=target2.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);


		Jsonb jsonb = JsonbBuilder.create();
		Livre livre1=jsonb.fromJson(jsonlivre,Livre.class);
		
		
		
		
		
		String nom=request.getParameter("nom");
		double prix=Double.parseDouble(livre1.getPrix());
		System.out.println(nom+prix+id);
		Panier p= new Panier(id,livre1.getNom(),prix);

		String jsonpanier=jsonb.toJson(p, Panier.class);
		
		
		try {
			URL url = new URL("http://localhost:8080/Projet_Web_store_Rest/webstore/ajouterLivreAUP");
	        HttpURLConnection con  = (HttpURLConnection) url.openConnection();
	        con.setDoOutput(true);
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-Type", "application/json");
	        OutputStream os = con.getOutputStream();
	        os.write(jsonpanier.getBytes());
	        os.close();
	        os.flush();
	        System.out.println(jsonpanier.getBytes());
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
		
		HttpSession s = request.getSession();
		request.getRequestDispatcher("Servletlivres?idcatalogue="+s.getAttribute("typecategorie")+"").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
