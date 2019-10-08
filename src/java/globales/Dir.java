/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globales;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 
 */
public class Dir {

   private final static String FACES = "/faces";

   public static String getRuta() {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      return request.getContextPath() + FACES;
   }

   public static String getContexto() {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      return request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "")
              + request.getContextPath();
   }

   public static String getContextoFaces() {
      return getContexto() + FACES;
   }

   public static String getIpAddress() {
      String[] HEADERS_TO_TRY = {"X-FORWARDED-FOR","Proxy-Client-IP","WL-Proxy-Client-IP",
         "HTTP_X_FORWARDED_FOR","HTTP_X_FORWARDED","HTTP_X_CLUSTER_CLIENT_IP","HTTP_CLIENT_IP",
         "HTTP_FORWARDED_FOR","HTTP_FORWARDED","HTTP_VIA","REMOTE_ADDR"};
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      for (String header : HEADERS_TO_TRY) {
         if (null != request.getHeader(header) && 
                 request.getHeader(header).length() > 0 && 
                 !"unknown".equalsIgnoreCase(request.getHeader(header))) {
            break;
         }
      }
      return request.getRemoteAddr();
   }
   public static void redireccionar(String url) {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      String contextURL = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "")
              + request.getContextPath()
              + FACES;
      try {
      FacesContext.getCurrentInstance().getExternalContext().redirect(contextURL + url);
      } catch (Exception e) {
            System.out.println("Error en Contexto.redireccionar(" + (contextURL + url) + ") " + e.getMessage());
         }
   }
}
