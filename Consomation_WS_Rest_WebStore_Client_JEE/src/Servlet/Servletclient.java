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

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@WebServlet("/servletclient")
public class Servletclient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Servletclient() {
        super();
    	
    	}

    	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
	
    		String nom = (String) request.getParameter("nom");
    		String prenom = (String) request.getParameter("prenom");
    		String nombre = (String) request.getParameter("nombre");
    		String email = (String) request.getParameter("email");
    		String password = (String) request.getParameter("password");
    		if(nom!="" && prenom!="" && nombre!="" && email!="" && password!="") {
    			com.Beans.Client c = new com.Beans.Client();
        		c.setNom(nom);
        		c.setPrenom(prenom);
        		c.setPhone(nombre);
        		c.setEmail(email);
        		c.setPassword(password);
        		
        		Jsonb jsonb = JsonbBuilder.create();
        		String json = jsonb.toJson(c);
        		
        		
        		try {
        			URL url = new URL("http://localhost:8080/Projet_Web_store_Rest/webstore/ajouterclient");
        	        HttpURLConnection con  = (HttpURLConnection) url.openConnection();
        	        con.setDoOutput(true);
        	        con.setRequestMethod("POST");
        	        con.setRequestProperty("Content-Type", "application/json");
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
        		System.out.println("connetsd");
        		this.getServletContext().getRequestDispatcher("/buy.jsp").forward(request, response);
        	
        		
    		}else {
    			this.getServletContext().getRequestDispatcher("/buy.jsp").forward(request, response);
    		}
    		

    		System.out.println("connetsd");

    		
    	}

    	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {

    		String login = request.getParameter("textemail");
    		String password = request.getParameter("textpassword");

    		request.setAttribute("login", login);
    		request.setAttribute("password", password);
    		
    		String stl;
    		Client client = javax.ws.rs.client.ClientBuilder.newClient();
    		WebTarget target2 = client.target("http://localhost:8080/Projet_Web_store_Rest/webstore/isValidLogin/"+login+"/"+password);
			stl=target2.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);

    		Jsonb jsonb = JsonbBuilder.create();
    		
    		
    		
    		if(!stl.equals("")) {
    		com.Beans.Client clt=jsonb.fromJson(stl, com.Beans.Client.class);
    		
    		String type=(String)request.getParameter("idcatalogue");
    		com.Beans.Client connectedUser = (com.Beans.Client) clt;
    		if (connectedUser != null) {

//    			HttpSession session = request.getSession(true);
//    			session.setAttribute("connectedUser", connectedUser);
//    			
    			HttpSession s = request.getSession(true);
    			s.setAttribute("typecategorie", type);
//    			if(s.getAttribute("typecategorie")!=null)
//    				
//    				request.getRequestDispatcher("/Servletlivres?idcatalogue="+s.getAttribute("typecategorie")+"").forward(request, response);
//    			else {
//    				s.setAttribute("typecategorie", "Science");
//    				request.getRequestDispatcher("/Servletlivres?idcatalogue=Science").forward(request, response);
//    			}
    			
    			
    			
    			
    			HttpSession session = request.getSession(true);
    			session.setAttribute("connectedUser", connectedUser);
    			if(connectedUser.getEmail().equals("olephina@gmail.com")) {
    				request.getRequestDispatcher("/ServletAdmin").forward(request, response);
    			}else {
        			if(s.getAttribute("typecategorie")!=null)
        				request.getRequestDispatcher("/les_livres.jsp?idcatalogue="+s.getAttribute("typecategorie")+"").forward(request, response);
        			else
        				request.getRequestDispatcher("/les_livres.jsp?idcatalogue=Science").forward(request, response);
    			}	
    		} else {

    			request.setAttribute("errorMessage", "Bad identity");
    			request.getRequestDispatcher("/buy.jsp").forward(request, response);

    		}}else {
    			request.getRequestDispatcher("/buy.jsp").forward(request, response);
    		}
    	}

    }