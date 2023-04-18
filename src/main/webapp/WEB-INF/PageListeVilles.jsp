<%--
  Created by IntelliJ IDEA.
  User: adrie
  Date: 15/04/2023
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Liste de l'ensemble des villes</title>
</head>
<body>
    <h1>
        <%="Liste complète des villes"%>
    </h1>
    <h2>
        <c:out value="Page ${page} " />
    </h2>

    <form method="post" action="PageListeVilles">
        <p>
            <label for="page">Numéro de page</label>
            <input type = "text" id="page" name="page" />
            <input type = "submit" name= "bouton" value="afficher" />
            <c:out value=" Il y a actuellement un total de ${ totalPages } page pour ${elementTotal} de ville" />
        </p>
    </form>
    <c:forEach items="${ pageObjects }" var="ville" varStatus="status">
        <p>
            <c:out value="Code Commune: ${ ville.codeCommune }  ,Nom: ${ ville.nom }  ,Code Postal: ${ ville.codePostal }  ,Latitude: ${ ville.latitude }  ,Longitude: ${ ville.longitude }" />
            <input type="submit" id="modifier" onclick="window.location.href = 'Modification?codeCommune=${ville.codeCommune}';" value="Modifier" />
        </p>
    </c:forEach>

</body>
</html>
