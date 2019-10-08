
package utilerias;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import parametros.ParametrosDAO;
 
public class DisplayImageReporte extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
                        throws ServletException, IOException {
        
        //String id = request.getParameter("Image_id");
        System.out.println("inside servlet–> DisplayImageReporte");
        ParametrosDAO pDAO = new ParametrosDAO();
        
        pDAO.getFotoStream(request, response);
        
    }
    
    
    }
  



 
 
