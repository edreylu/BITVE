
package utilerias;


import eventos.EventoDAO;
import java.io.*;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
@WebServlet(name = "DisplayPDF")
public class DisplayPDF extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;
    private final EventoDAO evDAO = new EventoDAO();
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
                        throws ServletException, IOException {
        
        String id=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_pdf");
        System.out.println("inside servlet–>" + id);
        //evDAO.getPDFStream(request, response, id);
    }
    
    
    }
  



 
 
