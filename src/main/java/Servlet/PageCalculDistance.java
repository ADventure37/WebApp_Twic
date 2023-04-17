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
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.core.io.NumberInput.parseDouble;
import static java.lang.Math.*;

@WebServlet(name = "PageCalculDistance", value = "/PageCalculDistance")
public class PageCalculDistance extends HttpServlet {

    public PageCalculDistance(){super();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("villes",recupVilles());
        this.getServletContext().getRequestDispatcher("/WEB-INF/PageCalculDistance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String villeD = request.getParameter("villeD");
        String villeA = request.getParameter("villeA");
        String distance = "" + calculDistance(villeD,villeA);
        request.setAttribute("distance",distance);
        request.setAttribute("villes",recupVilles());
        request.setAttribute("villeD", villeD);
        request.setAttribute("villeA", villeA);
        this.getServletContext().getRequestDispatcher("/WEB-INF/PageCalculDistance.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private List<Ville> recupVilles(){
        List<Ville> villes = new ArrayList<>();
        List<String> list = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8080/villes/");
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

            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(content.toString(), List.class);

//            System.out.println("Response content: " + content.toString());
        } catch (IOException e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        // Traitement des données
        for(String v: list){
            String[] sep = v.split(",");
            Ville ville = new Ville();
            ville.setCodeCommune(sep[0]);
            ville.setNom(sep[1]);
            ville.setCodePostal(sep[2]);
            double latitude = parseDouble(sep[3]);
            double longitude = parseDouble(sep[4]);
            ville.setLatitude(latitude);
            ville.setLongitude(longitude);
            villes.add(ville);
        }
        return villes;
    }

    public Ville recupereVilleN(String nom){
        Ville ville = new Ville();
        try {
            String urlbase = "http://localhost:8080/ville";
            URL url = new URL(urlbase +"?nom="+nom);
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
            ville.setCodeCommune(sep[0]);
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

    public double calculDistance(String villeD, String villeA){
        Ville villeDepart = recupereVilleN(villeD);
        Ville villeArrive = recupereVilleN(villeA);

        double x = (villeArrive.getLatitude() - villeDepart.getLatitude()) * cos((villeArrive.getLongitude() + villeDepart.getLongitude())/2);
        double y = villeArrive.getLongitude() - villeDepart.getLongitude();
        double z = sqrt(pow(x,2) + pow(y,2));
        double distance = 1.852 * 60 * z;
        System.out.println("distance: "+ distance);
        return distance;
    }
}
