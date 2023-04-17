<%--
  Created by IntelliJ IDEA.
  User: adrie
  Date: 15/04/2023
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Calculer la distance entre deux villes</title>
</head>
<body>
    <h1>
        <%="Calculer une distance entre deux villes"%>
    </h1>

    <form method="post" action="PageCalculDistance">
        <p>
            <label for="villeD">Ville de départ</label>
            <select name="villeD" id="villeD">
                <option value="">Sélectionner une ville</option>
                <c:forEach items="${ villes }" var="ville" varStatus="status">
                    <p>
                        <option value="${ ville.nom }">${ ville.nom}</option>
                    </p>
                </c:forEach>
            </select>
        </p>
        <p>
            <label for="villeA">Ville d'arrivé</label>
            <select name="villeA" id="villeA">
                <option value="">Sélectionner une ville</option>
                <c:forEach items="${ villes }" var="ville" varStatus="status">
                    <p>
                        <option value="${ ville.nom }">${ ville.nom }</option>
                    </p>
                </c:forEach>
            </select>
        </p>
        <p><input type = "submit" name= "bouton" value="calculer" /></p>

        <p>
            <c:if test="${ !empty distance }">
                <p><c:out value="${villeD} et ${villeA} sont séparées par une distance de ${distance} km!" /></p>
            </c:if>
        </p>

    </form>

</body>
</html>
