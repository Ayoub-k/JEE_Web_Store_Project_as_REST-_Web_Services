
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Beans.Client;
import Beans.Command;
import Beans.Livre;
import Beans.Panier;

@Path("/webstore")
public class CataloguelivreImpl {

//	@WebMethod(operationName = "getconnection")
	public static Connection getconnection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "");
			System.out.println("connection");
			return con;
		} catch (Exception e) {
			return null;
		}
	}

	@POST
	@Path("/ajouterlivre")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouterlivre( Livre l) {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");

			String query = "INSERT INTO Livre(nom,nbrpage,langue,prix,autheur,annee,path,nomcatalogue)values(?,?,?,?,?,?,?,?)";
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(query);
			if (l != null) {
				pr.setString(1, l.getNom());
				pr.setString(2, l.getNbrpage());
				pr.setString(3, l.getLangue());
				pr.setString(4, l.getPrix());
				pr.setString(5, l.getAutheur());
				pr.setString(6, l.getAnnee());
				pr.setString(7, l.getPath());
				pr.setString(8, l.getNomcatalogue());
				pr.execute();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName());
		}
	}

	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("afficherlivre/{id}")
	public Livre afficher(@PathParam(value="id")int id) {
		Livre l = new Livre();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			String query = "select * from Livre where id=?";
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(query);
			pr.setInt(1, id);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				l.setNom(rs.getString("nom"));
				l.setNbrpage(rs.getString("nbrpage"));
				l.setLangue(rs.getString("langue"));
				l.setPrix(rs.getString("prix"));
				l.setAutheur(rs.getString("autheur"));
				l.setAnnee(rs.getString("annee"));
				l.setPath(rs.getString("path"));
				l.setNomcatalogue(rs.getString("nomcatalogue"));
				l.setId(rs.getInt("id"));
			}
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName());
		}
		return l;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("afficherparnom/{nom}")
	public Livre afficherparnom(@PathParam("nom")String nom) {
		Livre l = new Livre();

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			String query = "select * from Livre where nom=?";
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(query);
			pr.setString(1, nom);
			ResultSet rs = pr.executeQuery();
			if (rs.next()) {
				l.setNom(rs.getString("nom"));
				l.setNbrpage(rs.getString("nbrpage"));
				l.setLangue(rs.getString("langue"));
				l.setPrix(rs.getString("prix"));
				l.setAutheur(rs.getString("autheur"));
				l.setAnnee(rs.getString("annee"));
				l.setPath(rs.getString("path"));
				l.setNomcatalogue(rs.getString("nomcatalogue"));
			}
			pr.close();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName());
		}
		return null;
	}

	
	
	@POST
	@Path("/ajouterclient")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouterClient(Client c) {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			String query = "INSERT INTO client(first_name,last_name,phone,email,password) values(?,?,?,?,?)";
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(query);
			pr.setString(1, c.getNom());
			pr.setString(2, c.getPrenom());
			pr.setString(3, c.getPhone());
			pr.setString(4, c.getEmail());
			pr.setString(5, c.getPassword());
			pr.execute();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isValidLogin/{email}/{password}")
	public Client isValidLogin(@PathParam("email")String email, @PathParam("password")String password) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "")) {
			String strSql = "SELECT * FROM client WHERE email=? AND password=?";
			try (PreparedStatement statement = con.prepareStatement(strSql)) {
				statement.setString(1, email);
				statement.setString(2, password);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						return new Client(resultSet.getString("first_name"), resultSet.getString("last_name"),
								resultSet.getString("phone"), resultSet.getString("email"),"");
					} else {
						return null;
					}
				}
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	
	@POST
	@Path("/ajoutercommand")
	@Produces(MediaType.APPLICATION_JSON)
	public void command(Command c) {

		String query = " INSERT INTO command(email,nomlivre,Q) values(?,?,?)";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
				PreparedStatement pr = con.prepareStatement(query)) {

			pr.setString(1, c.getEmailclient());
			pr.setString(2, c.getNomlivre());
			pr.setInt(3, c.getQ());
			pr.execute();
			System.out.println("correct");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("afficherimage1/{categorie}")
	public ArrayList<String> afficherimage1(@PathParam("categorie")String categorie) {
		ArrayList<String> path = new ArrayList<>();
		String aide;
		String query = "select path from Livre where nomcatalogue=?";

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "");
			PreparedStatement pr = con.prepareStatement(query);
			pr.setString(1, categorie);
			ResultSet re = pr.executeQuery();
			while (re.next()) {
				aide = (String) re.getString("path");
				path.add(aide);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("imagelivre/{categorie}")
	public String afficherimage(@PathParam("categorie")String categorie) {
		String path = null;

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			String query = "select path from Livre where nomcatalogue=?";
			PreparedStatement pr = con.prepareStatement(query);
			pr.setString(1, categorie);
			ResultSet re = pr.executeQuery();
			if (re.next())
				path = re.getString("path");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("afficher_livres/{nomcatalogue}")
	public ArrayList<Livre> affichier_livre(@PathParam("nomcatalogue")String nomcatalogue) {
//		ArrayList<Livre> liste_livre=null;
		ArrayList<Livre> liste_livre = new ArrayList<>();

		String query = "select * from Livre where nomcatalogue=?";
//		String query = "select * from Livre where nomcatalogue='"+nomcatalogue+"'";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			PreparedStatement pr = con.prepareStatement(query);
//			jdbc:mysql://localhost:3306/Book", "root", "ayoubhero"
			pr.setString(1, nomcatalogue);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				Livre l = new Livre();
				l.setNom(rs.getString("nom"));
				l.setNbrpage(rs.getString("nbrpage"));
				l.setLangue(rs.getString("langue"));
				l.setPrix(rs.getString("prix"));
				l.setAutheur(rs.getString("autheur"));
				l.setAnnee(rs.getString("annee"));
				l.setPath(rs.getString("path"));
				l.setNomcatalogue(rs.getString("nomcatalogue"));
				l.setId(rs.getInt("id"));
				liste_livre.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste_livre;
	}

	/****************************************************
	 * panier
	 *****************************************************/

	// Ajouter au panier bien fait
	@POST
	@Path("/ajouterLivreAUP")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouterLivreAUP(Panier a) {

		String sql = "insert into panier(nomlivre,prixlivre) values(?,?)";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "")) {
			PreparedStatement pr = con.prepareStatement(sql);
			
			pr.setString(1, a.getNom_livre());
			pr.setDouble(2, a.getPrix_livre());
			pr.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//	bien fait	
	@DELETE
	@Path("/vider_panier")
	@Produces(MediaType.APPLICATION_JSON)
	public void vider_panier() {

		String sql = "TRUNCATE TABLE panier";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "")) {
			PreparedStatement pr = con.prepareStatement(sql);
			pr.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//	bien fait
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("AllPanierlivres")
	public List<Panier> Alllivres() {
		List<Panier> ps = new ArrayList<>();

		String sql = "select * from panier ";

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			PreparedStatement pr = con.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				Panier l = new Panier();
				l.setId(rs.getInt("id"));
				l.setNom_livre(rs.getString("nomlivre"));
				l.setPrix_livre(rs.getDouble("prixlivre"));
				ps.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;

	}

	@DELETE
	@Path("/deletelivre/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletelivre(@PathParam("code")int code) {

		String sql = "delete from panier where id=?";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(sql);
			pr.setInt(1, code);
			pr.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	pour admin 
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/afficherTlivre")
	public ArrayList<Livre> afficherTlivre() {
		ArrayList<Livre> liste_livre = new ArrayList<>();

		String query = "select * from Livre";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			PreparedStatement pr = con.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				Livre l = new Livre();
				l.setNom(rs.getString("nom"));
				l.setNbrpage(rs.getString("nbrpage"));
				l.setLangue(rs.getString("langue"));
				l.setPrix(rs.getString("prix"));
				l.setAutheur(rs.getString("autheur"));
				l.setAnnee(rs.getString("annee"));
				l.setPath(rs.getString("path"));
				l.setNomcatalogue(rs.getString("nomcatalogue"));
				l.setId(rs.getInt("id"));
				liste_livre.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste_livre;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("afficherTClint")
	public ArrayList<Client> afficherTClint() {
//		ArrayList<Livre> liste_livre=null;
		ArrayList<Client> liste_client = new ArrayList<>();

		String query = "select * from client";
//		String query = "select * from Livre where nomcatalogue='"+nomcatalogue+"'";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book", "root", "");
			PreparedStatement pr = con.prepareStatement(query);
			ResultSet resultSet = pr.executeQuery();
			while (resultSet.next()) {
				Client client = new Client();
				client.setEmail(resultSet.getString("email"));
				
				client.setPrenom(resultSet.getString("first_name"));; 
				client.setNom(resultSet.getString("last_name"));;
				client.setPhone(resultSet.getString("phone")); 
				client.setPassword("VID >>H<< VIDE");
				client.setId(resultSet.getInt("id"));
				liste_client.add(client);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste_client;
	}
	
	
	
	@DELETE
	@Path("/deleteClient/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteClient(int id) {

		String sql = "delete from client where id=?";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book",  "root", "");
			PreparedStatement pr = (PreparedStatement) con.prepareStatement(sql);
			pr.setInt(1, id);
			pr.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}


