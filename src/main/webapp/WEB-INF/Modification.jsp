<%--
  Created by IntelliJ IDEA.
  User: adrie
  Date: 18/04/2023
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Modifier les informations d'une ville</title>
</head>
<body>
    <h1>
        <%="Modifier les information d'une ville"%>
    </h1>

    <p>
    <form method="post" action="Modification">
        <p>
            <label>Code Commune: </label>
            <c:out value=" ${ codeCommune }" />
            <input type="hidden" name="codeCommune" value="${ codeCommune }">
        </p>
        <p>
            <label for="nom">Nom: </label>
            <input type = "text" id="nom" name="nom" value="${nom}"/>
            <label for="codePostal">Code Postal: </label>
            <input type = "text" id="codePostal" name="codePostal" value="${codePostal}"/>
        </p>
        <p>
            <label for="latitude">Latitude: </label>
            <input type = "text" id="latitude" name="latitude" value="${latitude}"/>
            <label for="longitude">Longitude: </label>
            <input type = "text" id="longitude" name="longitude" value="${longitude}"/>
        </p>
        <p>
            <input type = "submit" name= "bouton" value="valider" />
        </p>
    </form>
    </p>

</body>
</html>
