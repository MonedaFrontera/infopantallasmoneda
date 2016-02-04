<%-- Prevencion del cache --%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //impide el almacenamiento en caché en el servidor proxy
    response.setHeader("Cache-Control", "no-store");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
    </head>
    <body>
        <c:set var="dolarAc" value="${mapaDolarActual['DolarToday']}"/>
        <c:set var="dolarAn" value="${mapaDolarAnterior['DolarToday']}"/>
        <c:set var="dif" value="${dolarAc - dolarAn}"/>
        <c:set var="dif" value="${dolarAc - dolarAn}"/>
        <div class="dolarTodayRate">
            <div class="tituloDiv">
                <div class="title">
                    D&oacute;lar Paralelo                    
                </div>
            </div>
            <div id="dolarleft">
                <fmt:formatNumber pattern="#,##0.00" value="${dolarAc}" maxFractionDigits="2" />  
                <%--<c:out value="${dolarAc}"/>--%>
            </div>
            <div id="dolarSymbol">
                <c:if test="${dolarAc != null }">
                    <c:choose>
                        <c:when test="${dolarAc == dolarAn }">
                            <img id="central" src="img/rate_igual_grande.png"  />
                        </c:when>
                        <c:when test="${dolarAc > dolarAn }">
                            <img id="central" src="img/rate_sube_grande_a.png" />
                        </c:when>
                        <c:when test="${dolarAc < dolarAn }">
                            <img id="central" src="img/rate_baja_grande_a.png"  />
                        </c:when>
                    </c:choose>
                </c:if>
            </div>
            <div id="dolarDif">
                <c:if test="${dif !=0 }">
                    <c:if test="${dif < 0}">
                        <fmt:formatNumber pattern="#,##0.00" value="${dif * (-1)}"  maxFractionDigits="2"  />   
                    </c:if>
                    <c:if test="${dif > 0}">
                        <fmt:formatNumber pattern="#,##0.00" value="${dif}"  maxFractionDigits="2"  />   
                    </c:if>
                </c:if> 
            </div>
            <div id="footerleft">
                Ultima actualizaci&oacute;n hace ${actualizado} minutos
            </div>
            <div id="footerrigth">
                <img style="float: right" src="img/dolarToday_logo2.png" width="30" height="30" alt="dolarToday_logo_trans"/>
                <br/>Suministrado por <stron>DolarToday.com&nbsp;</stron>
            </div>
        </div>
    </div>
</body>
</html>
