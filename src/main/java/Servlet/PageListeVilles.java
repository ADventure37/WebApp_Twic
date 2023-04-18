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

@WebServlet(name = "PageListeVilles", value = "/PageListeVilles")
public class PageListeVilles extends HttpServlet {

    public PageListeVilles(){super();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ville> villes = recupVilles();
        int nbElement = 50;
        int elementTotal = villes.size();
        int totalPages = elementTotal/nbElement + 1;

        List<Ville> pageObjects = villes.subList(0, 49);
        request.setAttribute("pageObjects", pageObjects);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("elementTotal", elementTotal);
        request.setAttribute("page", 1);

        this.getServletContext().getRequestDispatcher("/WEB-INF/PageListeVilles.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ville> villes = recupVilles();

        int nbElement = 50;
        int elementTotal = villes.size();
        int totalPages = elementTotal/nbElement + 1;

        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        int start = (currentPage - 1) * nbElement;
        int end = Math.min(start + nbElement, elementTotal);
        List<Ville> pageObjects = villes.subList(start, end);

        // Passage des données à la JSP
        request.setAttribute("pageObjects", pageObjects);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);

        this.getServletContext().getRequestDispatcher("/WEB-INF/PageListeVilles.jsp").forward(request, response);
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
}
