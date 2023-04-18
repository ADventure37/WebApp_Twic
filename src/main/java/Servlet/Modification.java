package Servlet;

import beans.Ville;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.core.io.NumberInput.parseDouble;

@WebServlet(name = "Modification", value = "/Modification")
public class Modification extends HttpServlet {

    public Modification(){super();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeCommune = request.getParameter("codeCommune");

        Ville ville = recupereVilleCC(codeCommune);

        request.setAttribute("codeCommune", ville.getCodeCommune());
        request.setAttribute("nom", ville.getNom());
        request.setAttribute("codePostal", ville.getCodePostal());
        request.setAttribute("latitude", ville.getLatitude());
        request.setAttribute("longitude", ville.getLongitude());

        this.getServletContext().getRequestDispatcher("/WEB-INF/Modification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ville ville = new Ville();
        System.out.println(request.getParameter("codeCommune"));
        ville.setCodeCommune(request.getParameter("codeCommune"));
        ville.setNom(request.getParameter("nom"));
        ville.setCodePostal(request.getParameter("codePostal"));
        ville.setLatitude(parseDouble(request.getParameter("latitude")));
        ville.setLongitude(parseDouble(request.getParameter("longitude")));

        modifieVilleCC(ville);

        request.setAttribute("codeCommune", ville.getCodeCommune());
        request.setAttribute("nom", ville.getNom());
        request.setAttribute("codePostal", ville.getCodePostal());
        request.setAttribute("latitude", ville.getLatitude());
        request.setAttribute("longitude", ville.getLongitude());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Modification.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    public Ville recupereVilleCC(String codeCommune){
        Ville ville = new Ville();
        try {
            String urlbase = "http://localhost:8080/ville";
            URL url = new URL(urlbase +"?codeCommuneINSEE="+codeCommune);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            System.out.println("Response status: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String[] sep = content.toString().split(",");
            ville.setCodeCommune(sep[0].substring(2));
            ville.setNom(sep[1]);
            ville.setCodePostal(sep[2]);

            String end = sep[4].substring(0, sep[4].length()-2);

            ville.setLatitude(parseDouble(sep[3]));
            ville.setLongitude(parseDouble(end));

        } catch (IOException e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        return ville;
    }
    public Ville modifieVilleCC(Ville ville){
        try {
            String urlbase = "http://localhost:8080/ville/";
            URL url = new URL(urlbase +ville.getCodeCommune()+"?nom="+ville.getNom()+"&codePostal=" +
                    ville.getCodePostal() +"&latitude="+ville.getLatitude()+"&longitude="+ville.getLongitude());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");

            int status = con.getResponseCode();
            System.out.println("Response status: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String[] sep = content.toString().split(",");
            ville.setCodeCommune(sep[0].substring(2));
            ville.setNom(sep[1]);
            ville.setCodePostal(sep[2]);

            String end = sep[4].substring(0, sep[4].length()-2);

            ville.setLatitude(parseDouble(sep[3]));
            ville.setLongitude(parseDouble(end));

        } catch (IOException e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        return ville;
    }
}
