
package conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import oracle.sql.CLOB;



public class ConexionOracle {

   private Connection conn = null;
   private final String nombreRecurso;
   private final boolean modoDepurar = false;
   public ConexionOracle() {
      this("jdbc/bitveDbDev");
   }

   public ConexionOracle(String nombreRecurso) {
      this.nombreRecurso = nombreRecurso;
   }

   public void abrirConexion() {
      try {
         conn = new conexion.DriverConexion(nombreRecurso).getOracleConnection();
      } catch (Exception e) {
         System.out.println("Error en ConexionOracle.abrirConexion()\n" + e.getMessage());
      }
   }

   public void cerrarConexion() {
      try {
         if (conn != null) {
            conn.close();
            conn = null;
         }
      } catch (SQLException e) {
         System.out.println("Error en ConexionOracle.cerrarConexion()\n" + e.getMessage());
      }
   }

   protected PreparedStatement getPreparedStatement(String query) {
      PreparedStatement pstmt = null;
      try {
         if (null != conn) {
            pstmt = conn.prepareStatement(query);
         }
         if (modoDepurar) {
            pintaQuery(query);
         }
      } catch (SQLException e) {
         System.out.println("Error en ConexionOracle.getPrepareStatement()\n" + query + "\n" + e.getMessage());
         try {
            if (null != pstmt) {
               pstmt.close();
               pstmt = null;
            }
         } catch (SQLException ex) {
            System.out.println("Error en ConexionOracle.getPrepareStatement().catch\n" + ex.getMessage());
         }
      }
      return pstmt;
   }
   
   private void pintaQuery(String query) {
      query = query.trim();
      while (query.contains("  ")) {         
         query = query.replace("  ", " ");
      }
      query += "\n";
      System.out.println(query);
   }

   private void pintaQuery(String query, Object[] args) {
      String[] separa = query.split("\\?");
      String qryNvo = "";
      for (int idx = 0; idx < separa.length - 1; idx++) {
         qryNvo += separa[idx] + String.valueOf(args[idx]);
      }
      qryNvo += separa[separa.length - 1];
      pintaQuery(qryNvo);
   }

   protected PreparedStatement getPreparedStatement(String query, Object[] args) {
      PreparedStatement pstmt = null;
      try {
         if (null != conn) {
            pstmt = conn.prepareStatement(query);
            for (int idx = 0; idx < args.length; idx++) {
               int pos = idx + 1;
               if (args[idx] instanceof Integer) {
                  pstmt.setInt(pos, vrs(args[idx]));
               } else if (args[idx] instanceof Double) {
                  pstmt.setDouble(pos, (Double) args[idx]);
               } else if (args[idx] instanceof Number) {
                  pstmt.setInt(pos, vrs(args[idx]));
               } else if (args[idx] instanceof String) {
                  pstmt.setString(pos, (String) args[idx]);
               } else if (args[idx] instanceof Character) {
                  pstmt.setString(pos, (String) args[idx]);
               } else if (args[idx] instanceof Boolean) {
                  pstmt.setBoolean(pos, (Boolean) args[idx]);
               } else {
                  pstmt.setObject(pos, args[idx]);
               }
            }
            if (modoDepurar) {
               pintaQuery(query, args);
            }
         }
      } catch (SQLException e) {
         System.out.println("Error en ConexionOracle.getPrepareStatement()\n" + query + "\n" + e.getMessage());
         try {
            if (null != pstmt) {
               pstmt.close();
               pstmt = null;
            }
         } catch (SQLException ex) {
            System.out.println("Error en ConexionOracle.getPrepareStatement().catch()\n" + ex.getMessage());
         }
      }
      return pstmt;
   }

   private Integer vrs(Object obj) {
      Integer numero;
      if (obj instanceof Byte && !obj.equals(DatoDefault.dfltBYTE)) {
         numero = new Integer(obj.toString());
      } else if (obj instanceof Short && !obj.equals(DatoDefault.dfltSHORT)) {
         numero = new Integer(obj.toString());
      } else if (obj instanceof Integer && !obj.equals(DatoDefault.dfltINTEGER)) {
         numero = new Integer(obj.toString());
      } else if (obj instanceof Long && !obj.equals(DatoDefault.dfltLONG)) {
         numero = new Integer(obj.toString());
      } else if (obj instanceof Double && !obj.equals(DatoDefault.dfltDOUBLE)) {
         numero = new Integer(obj.toString());
      } else {
         numero = null;
      }
      return numero;
   }

   public CLOB creaTemporary() throws Exception {
      return creaTemporary(1)[0];
   }

   public CLOB[] creaTemporary(int cuantos) throws Exception {
      CallableStatement cs = null;
      CLOB[] clobs = null;
      try {
         if (null != conn) {
            clobs = new CLOB[cuantos];
            for (int i = 0; i < cuantos; i++) {
               cs = conn.prepareCall("{ call DBMS_LOB.CREATETEMPORARY(?, TRUE)}");
               cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CLOB);
               cs.execute();
               clobs[i] = (CLOB) cs.getObject(1);
            }
         } else {
            throw new Exception("error en ConexionOracle.creaTemporary(int), la conexion es null");
         }
      } catch (Exception e) {
         System.out.println("error en ConexionOracle.creaTemporary() " + e.toString());
      } finally {
         try {
            if (null != cs) {
               cs.close();
            }
         } catch (SQLException e) {
            System.out.println("error en ConexionOracle.creaTemporary() " + e.toString());
         }
      }
      return clobs;
   }
   
   public Connection getConn() {
      return conn;
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
      conn = null;
   }
}
