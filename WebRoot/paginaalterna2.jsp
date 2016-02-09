
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //impide el almacenamiento en caché en el servidor proxy
	response.setHeader("Cache-Control", "no-store");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Formulario Procesado</title>
<meta http-equiv="refresh" content="300" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		var url = 'http://www.google.com';
		$.get(url, function(response) {
			$('div#external').html(response);
		});
	});
</script>

</head>
<body>
	
	<div>
		<object type="text/html" 
			data="http://dolar.wilkinsonpc.com.co/#indicador_vigente_index" width="1000px"
			height="900px" style="overflow: auto; border: 1px gray">
		</object>
	</div>


</body>
</html>
