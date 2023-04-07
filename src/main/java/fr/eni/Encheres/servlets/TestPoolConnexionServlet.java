package fr.eni.Encheres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestPoolConnexionServlet")
public class TestPoolConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestPoolConnexionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Context context = new InitialContext();
			//Recherche de la DataSource (context du META-INF)
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/pool_cnx");
			//Demande une connexion. La methode getConnection met la demande en attente tant qu'il n'y a pas de connexions disponibles dans le pool.
			Connection cnx = dataSource.getConnection();
			out.print("La connexion est " + (cnx.isClosed()?"ferm�e":"ouverte")+".");
			//Lib�rer la connexion lorsque l'on en a plus besoin:
			cnx.close(); //La connexion n'est pas ferm�e mais remise dans le pool.	
		} catch (NamingException | SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("Une erreur est survenue lors de l'utilisation de la base de données : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}