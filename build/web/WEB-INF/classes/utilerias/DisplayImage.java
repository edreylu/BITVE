
package utilerias;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import eventos.EventoDAO;
 
public class DisplayImage extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
                        throws ServletException, IOException {
        
        String id = request.getParameter("Image_id");
        //System.out.println("inside servlet–>" + id);
        EventoDAO evDAO = new EventoDAO();
        
        //evDAO.getFotoStream(request, response, id);
        
    }
    
    
    }
  



 
 
