
package com.monedafrontera.webcontroller;

import com.monedafrontera.dto.*;
import com.monedafrontera.exceptions.*;
import com.monedafrontera.exceptions.*;
import com.monedafrontera.factory.DolarTodayJson;
import com.monedafrontera.jdbc.*;
import com.monedafrontera.service.tcrm.request.TCRMRequest;
import com.monedafrontera.services.tcrm.action.Tcrm;
import com.monedafrontera.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Luis Fernando Ortiz |@lfernandortiz
 */

@WebServlet(name="asyncServlet",value = {"/async"},asyncSupported = true)
public class ServletControlador extends HttpServlet {
	
	Logger logger = Logger.getLogger("InfopantallasMoneda");

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	
        	//1. procesa los parametros de la peticion
            String param = request.getParameter("accion");
            long t1 = System.currentTimeMillis();
            String agent = null;
            
          
            
            if ("actualizar".equals(param)) {            	
            	agent = request.getHeader("User-Agent");            	
            	
            	
            	//System.out.println(agent);
            	
                actualizarTasas();
                if( !this.existeTRMColombia() ){                	
                	if( compareTCRM() ){                		
                		grabarTrmFromDolarToday();
                	}else{
                		grabarTrmSuperfinanciera();
                	}	
                }
            }
            
            long t2 = (System.currentTimeMillis() - t1) / 1000;
            String sec = "";
            if (t2 > 1 || t2 == 0) {
                sec = " segundos";
            } else {
                sec = " segundo";
            }
            
            Log.imprimirLog(new Date(), "INFO", "Tiempo de Ejecucion Thread "
                    + t2 + sec);
            
            //crea un objesto session al que le voy asignar los objetos de sesion
            HttpSession session = request.getSession();

            // Se establece los parametros de sesion
            //setSessionValues(request, response);// En esta Version aun no implementeado este metodo
            

            //objeto DAO usado para operaciones en la BD
            DolartodayDaoImpl daoD = new DolartodayDaoImpl();
            //obtiene el ultimo registro de la bd del dolar 
            Dolartoday dolarTodayActual = daoD.getMaxEpoch();
            //se USAN MAPAS  para imprimir los nombres de las llaves como titulos
            Map<String, Float> mapaDolarActual = new HashMap<String, Float>();//DOLAR
            Map<String, Float> mapaEuroActual = new HashMap<String, Float>();//EURO

            mapaDolarActual.put("Transferencia", dolarTodayActual.getUsdtransferencia());
            mapaDolarActual.put("Impl&iacute;cito", dolarTodayActual.getUsdefectivo());
            mapaDolarActual.put("Promedio", dolarTodayActual.getUsdpromedio());
            mapaDolarActual.put("Simadi", dolarTodayActual.getUsdpromedioreal());
            mapaDolarActual.put("Sicad 1", dolarTodayActual.getUsdsicad1());
            mapaDolarActual.put("DolarToday", dolarTodayActual.getUsddolartoday());
            mapaDolarActual.put("Cencoex", dolarTodayActual.getUsdcencoex());

            mapaEuroActual.put("Euro Today", dolarTodayActual.getEurdolartoday());
            mapaEuroActual.put("Impl&iacute;cito*", dolarTodayActual.getEurefectivo());
            mapaEuroActual.put("Promedio", dolarTodayActual.getEurpromedio());
            mapaEuroActual.put("Transferencia", dolarTodayActual.getEurtransferencia());
            //        mapaEuroActual.put("Promedio Real", dolarTodayActual.getEurpromedioreal());

            Set< String> cDolarAct = mapaDolarActual.keySet();//llaves DOLAR
            //Ordeno las llaves con un arbol binario
            TreeSet< String> clavesDolarAct = new TreeSet< String>(cDolarAct);

            Set< String> cEuroAct = mapaEuroActual.keySet();//llaves EURO
            //Ordeno las llaves con un arbol binario
            TreeSet< String> clavesEuroAct = new TreeSet< String>(cEuroAct);//Ordeno las llaves

            //obtengo el mayor registro de la fecha anterior a la actual
            //para comparar los cambios en las tasas
            //estos valores se deben obtener antes de las 5pm del dia anterior
            //antes de que se publique la TRM en Colombia

            //1. Identifico si la fecha anterior a la actual es entre Lunes y Viernes

            //Dolartoday temp = daoD.getDolarTodayDiaAnterior();

            Dolartoday dolarTodayAnterior = daoD.getDolarTodayDiaAnteriorHabil();
            System.out.println(dolarTodayAnterior.getEpoch());
            //se crean las colecciones de tasas anteriores para comparar
            Map<String, Float> mapaDolarAnterior = new HashMap<String, Float>();
            Map<String, Float> mapaEuroAnterior = new HashMap<String, Float>();

            mapaDolarAnterior.put("Transferencia", dolarTodayAnterior.getUsdtransferencia());
            mapaDolarAnterior.put("Impl&iacute;cito", dolarTodayAnterior.getUsdefectivo());
            mapaDolarAnterior.put("Promedio", dolarTodayAnterior.getUsdpromedio());
            mapaDolarAnterior.put("Simadi", dolarTodayAnterior.getUsdpromedioreal());
            mapaDolarAnterior.put("Sicad 1", dolarTodayAnterior.getUsdsicad1());
            mapaDolarAnterior.put("DolarToday", dolarTodayAnterior.getUsddolartoday());
            mapaDolarAnterior.put("Cencoex", dolarTodayAnterior.getUsdcencoex());

            mapaEuroAnterior.put("Euro Today", dolarTodayAnterior.getEurdolartoday());
            mapaEuroAnterior.put("Impl&iacute;cito*", dolarTodayAnterior.getEurefectivo());
            mapaEuroAnterior.put("Promedio", dolarTodayAnterior.getEurpromedio());
            mapaEuroAnterior.put("Transferencia", dolarTodayAnterior.getEurtransferencia());
//            mapaEuroAnterior.put("Promedio Real", dolarTodayAnterior.getEurpromedioreal());
        
            //ubicamos la TRM actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
            Date currentDate = sdf.parse(sdf.format(new Date()));
            //objeto DAO usado para operaciones en la BD
            TasadolarDaoImpl daoTasa = new TasadolarDaoImpl();

            //TRM actual
            Tasadolar trmActual = daoTasa.findByPrimaryKey("CO", currentDate);
            //TRM anterior
            Tasadolar trmAnterior = daoTasa.getTrmAnterior();
            
            //Maneja una validacion en caso de ser el TRM del fin de semana
            int finDeSemana = this.esFds(trmAnterior);
            
            if (finDeSemana != 0) {
                trmAnterior = daoTasa.getTrmViernes(
                        trmAnterior.getFecha(), finDeSemana);
                if(trmAnterior == null){
                	trmAnterior = daoTasa.getTrmAnterior();
                }
                
            }


            if (trmActual != null) {
                float dif = trmActual.getTrm() - trmAnterior.getTrm();
            } else {
                request.setAttribute("mensajeErrorDolar",
                        "Hay Problemas con la TCRM.<br/>Contacte al proveedor.");
            }

            TasadebolivaroficinaDaoImpl daoBolivar = new TasadebolivaroficinaDaoImpl();
            //Bolivar Oficina Actual

            Tasadebolivaroficina bsActualDepo = daoBolivar.findByPrimaryKey(currentDate, "D");
            Tasadebolivaroficina bsActualTrasfer = daoBolivar.findByPrimaryKey(currentDate, "T");
            Tasadebolivaroficina bsActualElect = daoBolivar.findByPrimaryKey(currentDate, "E");
            //Bolivar Oficina Anterior
            Tasadebolivaroficina bsAnteriorDepo = daoBolivar.getTasaBolivarAnteriorDeposito();
            Tasadebolivaroficina bsAnteriorTrasfer = daoBolivar.getTasaBolivarAnteriorTransferencia();
            Tasadebolivaroficina bsAnteriorElect = daoBolivar.getTasaBolivarAnteriorElectronico();

            long ultActualizacion = (System.currentTimeMillis() - dolarTodayActual.getEpoch()) / 1000 / 60;
            //objetos de sesion
            request.setAttribute("mapaDolarActual", mapaDolarActual);
            request.setAttribute("clavesDolarActual", clavesDolarAct);
            request.setAttribute("mapaDolarAnterior", mapaDolarAnterior);
            request.setAttribute("mapaEuroActual", mapaEuroActual);
            request.setAttribute("clavesEuroActual", clavesEuroAct);
            request.setAttribute("mapaEuroAnterior", mapaEuroAnterior);
            request.setAttribute("actualizado", ultActualizacion);
            request.setAttribute("dolarAnterior", dolarTodayAnterior);
            request.setAttribute("dolarActual", dolarTodayActual);
            request.setAttribute("trmActualValor", trmActual);
            request.setAttribute("trmAnteriorValor", trmAnterior);

            request.setAttribute("bsfActualDepo", bsActualDepo);
            request.setAttribute("bsfActualTrans", bsActualTrasfer);
            request.setAttribute("bsfActualElec", bsActualElect);
            request.setAttribute("bsfAnteriorDepo", bsAnteriorDepo);
            request.setAttribute("bsfAnteriorTrans", bsAnteriorTrasfer);
            request.setAttribute("bsfAnteriorElec", bsAnteriorElect);
            
            
            //redireccionamos al home si el request es desde un browser, si el request 
            //es desde una consola debemos desplegar un json.
            if(agent.contains("curl") ){
            	Log.imprimirLog(new Date(), "INFO",
                        "Request hecho desde consola");      
            	sendResponseToConsole(request, response);
            }else{
            	request.getRequestDispatcher("home.jsp").forward(request, response);
            }
            
        } catch (Exception ex) {
            Log.imprimirLog(new Date(), "Exceptions",
                    ex.getMessage());
            //System.out.println("Atrape la excepcion en el controlador principal");
            request.getRequestDispatcher("/WEB-INF/pages/commons/error.jsp").forward(request, response);
            ex.printStackTrace();
        }

    }

    
    
    public void actualizarTasas() {

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        Log.imprimirLog(new Date(), "CONX",
                "Conectando al Web Services DolarToday");
        //obtengo el objeto de entidad (DTO) Java DolarToday del Web Services 
        //desde la URL "https://s3.amazonaws.com/dolartoday/data.json"
        Dolartoday dolar = DolarTodayJson.getDolartoday2Object();

        //objeto DAO para las operaciones de base de datos (CRUD)
        DolartodayDaoImpl daoD = new DolartodayDaoImpl();

        try {

            //valida si el objeto DolarToday actual ya esta Registrado en la base
            Dolartoday temp = daoD.findByPrimaryKey(dolar.getEpoch());
            if (temp != null) {
                //obtiene el ultimo registro de la base
                temp = daoD.getMaxEpoch();
                //graba el valor si es mayor que el ultimo epoc
                if (dolar.getEpoch() > temp.getEpoch()) {
                    daoD.insert(dolar);
                    Log.imprimirLog(new Date(dolar.getEpoch()), "ACT",
                            "Tasas Actualizadas en la base.");
                }
            } else {//sino existe el valor del actual epoch lo persiste en la base
                daoD.insert(dolar);
                Log.imprimirLog(new Date(dolar.getEpoch()), "ACT",
                        "Tasas Actualizadas en la base.");
            }
        } catch (DolartodayDaoException e) {
            Log.imprimirLog(new Date(), "Exceptions",
                    e.getMessage());
            e.printStackTrace();
        }
    }//fin del metodo actualizarTasas
    
    
    
    /**
     * Graba la TCRM desde DolarToday
     */
    public void grabarTrmFromDolarToday(){        
        DolartodayDaoImpl daoD = new DolartodayDaoImpl();
        Dolartoday dolarTodayTRM = daoD.getDolarTodayActualTRM();        
        try{
            
            //ubicamos la TRM actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
            Date currentDate = sdf.parse(sdf.format(new Date()));
            //objeto DAO usado para operaciones en la BD
            TasadolarDaoImpl daoTasa = new TasadolarDaoImpl();
            //TRM actual
            Tasadolar trmActual = new Tasadolar();
            trmActual.setCodigopais("CO");
            trmActual.setFecha(currentDate);
            trmActual.setTasa( Math.round(dolarTodayTRM.getUsdratetrm()) );
            trmActual.setTasadolar( Math.round(dolarTodayTRM.getUsdratetrm()) );
            trmActual.setTrm(dolarTodayTRM.getUsdratetrm() );
            
            daoTasa.insert(trmActual);
             Log.imprimirLog(new Date(), "INFO", "TRM Actualizada en la base de datos");
        }catch(Exception e){}
    }
    
    
    /**
     * Graba la TCRM desde la Superfinanciera
     */
    public void grabarTrmSuperfinanciera() throws RemoteException, ParseException{
    	Tcrm trm = TCRMRequest.getTCRMCurrentDate();    
        try{
            
            //ubicamos la TRM actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
            Date currentDate = sdf.parse(sdf.format(new Date()));
            //objeto DAO usado para operaciones en la BD
            TasadolarDaoImpl daoTasa = new TasadolarDaoImpl();
            //TRM actual
            Tasadolar trmActual = new Tasadolar();
            trmActual.setCodigopais("CO");
            trmActual.setFecha(currentDate);
            trmActual.setTasa( Math.round(trm.getValue()) );
            trmActual.setTasadolar( Math.round(trm.getValue()) );
            trmActual.setTrm(trm.getValue() );
            
            daoTasa.insert(trmActual);
             Log.imprimirLog(new Date(), "INFO", "TRM Actualizada en la base de datos "
             		+ "desde Superfinanciera");
        }catch(Exception e){}
    	
    }

    
    
    public void setSessionValues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    
    /**
     * Valida que la TRM no corresorponda a la del Sabado o Domingo anterior
     * esto con el fin de que si la fecha actual es un lunes se compare con
     * la TRM del viernes anterior.
     * @param tasa
     * @return 
     */
    public int esFds(Tasadolar tasa) {
        int diasARestar = 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(tasa.getFecha());

//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
        
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //System.out.println("Tasa Anterior dia: " + day);

        switch (day) {
            case 1://si es dominigo
                diasARestar = 2;
                break;
            case 7://si es sabado
                diasARestar = 1;
                break;                
        }

        return diasARestar;
    }
    
    /**
     * Retorna un valor <tt>boolean</tt> <tt>true</tt> si existe la TCRM para la fecha
     * actual en la base de datos de Moneda. Esta TRM es la usada para liquidar
     * transacciones.
     * @return boolean value
     */
    public boolean existeTRMColombia(){
    	Tasadolar trmActual = null;        
    	try{
            //ubicamos la TRM actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
            Date currentDate = sdf.parse(sdf.format(new Date()));
            //objeto DAO usado para operaciones en la BD
            TasadolarDaoImpl daoTasa = new TasadolarDaoImpl();
            //TRM actual
            trmActual = daoTasa.findByPrimaryKey("CO", currentDate);
            
            if(trmActual == null)
                return false;            
            else
                return true;
            
        }catch(Exception e){
            Log.imprimirLog(new Date(), "Exceptions",
                    e.getMessage());
        }
        if(trmActual == null)
            return false;            
        else
            return true;
    }

    
    public boolean compareTCRM() throws RemoteException, ParseException{
         //objeto DAO para las operaciones de base de datos
         DolartodayDaoImpl daoD = new DolartodayDaoImpl();
         Dolartoday dolar = daoD.getMaxEpoch();
         Tcrm trm = TCRMRequest.getTCRMCurrentDate();
         
         if( trm != null ){
        	 if(dolar.getUsdratetrm() == trm.getValue()){
            	 Log.imprimirLog(new Date(), "INFO",
                         "Comparacion de la TRM: " + (dolar.getUsdratetrm() == trm.getValue()) );
            	 return true;
             }else{
            	 
            	 return false;
            	 
             }        	 
         }else{
        	 Log.imprimirLog(new Date(), "INFO",
                     "Web Services Superfinanciera no responde" );
        	 //aca debo enviar un correo informando al administrador del sistema.
        	 return true;
         }
         
         
        	 
    }

    protected void sendResponseToConsole( HttpServletRequest request, HttpServletResponse response){
    	response.setContentType("text;charset=UTF-8");//MIME
        PrintWriter out;
        System.out.println("\tServidor Moneda Frontera: Response 200");
		try {
			out = response.getWriter();
		    out.println("\tServidor Moneda Frontera: Response 200");
	        out.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}  
        
    }
   
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

