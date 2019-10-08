
package conexion;

 
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.OracleResultSet;
import java.sql.SQLException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DriverConexion {

   private OracleConnection oracleConnection;
   private OracleStatement oracleStatement;
   private OracleResultSet oracleResultSet;
   private List<String> listBatchQuerys = new ArrayList<String>();
   private boolean boSupportsBatch;

   public DriverConexion(String resourceName) {
      creaOracleConexion(resourceName);
      setSupportsBatchStatement();
   }

   public OracleResultSet executeQuery(String stQuery) {
      try {
         oracleStatement = (OracleStatement) oracleConnection.createStatement();
         oracleResultSet = (OracleResultSet) oracleStatement.executeQuery(stQuery);
         return oracleResultSet;
      } catch (SQLException sqlExc) {
         close();
         System.out.println("error en executeQuery: " + stQuery);
         System.out.println("sqlExc = " + sqlExc.getMessage());
         return null;
      }
   }

   public OracleResultSet executeQueryRsetScrollable(String stQuery) {
      try {
         oracleStatement = (OracleStatement) oracleConnection.createStatement(OracleResultSet.TYPE_SCROLL_INSENSITIVE, OracleResultSet.CONCUR_UPDATABLE);
         oracleResultSet = (OracleResultSet) oracleStatement.executeQuery(stQuery);
         return oracleResultSet;
      } catch (SQLException sqlExc) {
         close();
         System.out.println("error en executeQueryRsetScrollable: " + stQuery);
         System.out.println("sqlExc = " + sqlExc.getMessage());
         return null;
      }
   }

   public boolean executeUpdate(String stQuery) {
      boolean exito = false;
      try {
         oracleStatement = (OracleStatement) oracleConnection.createStatement();
         oracleStatement.executeUpdate(stQuery);
         oracleConnection.commit();
         exito = true;
      } catch (SQLException sqlExc) {
         System.out.println("error en executeUpdate: " + stQuery);
         System.out.println("sqlExc = " + sqlExc.getMessage());
         exito = false;
      } finally {
         try {
            oracleConnection.rollback();
         } catch (SQLException sqlExc) {
         System.out.println("Error en transaccion al realizar - ROLLBACK de la sentencia: Causa " + sqlExc.getMessage());
         }
         close();
      }
      return exito;
   }

   public boolean executeUpdateWithoutCommit(String stQuery) {
      try {
         oracleStatement = (OracleStatement) oracleConnection.createStatement();
         oracleStatement.executeUpdate(stQuery);
         return true;
      } catch (Exception sqlExc) {
         System.out.println("error en executeUpdateWithoutCommit: " + stQuery);
         System.out.println("sqlExc = " + sqlExc.getMessage());
         return false;
      }
   }

  
   public void addBatch(String stQuery) {
      if (boSupportsBatch) {
         listBatchQuerys.add(stQuery);
      }
   }

   public boolean executeBatch() throws SQLException {
      final int tamanoCommit = 500;
      try {
         if (boSupportsBatch) {
            oracleStatement = (OracleStatement) oracleConnection.createStatement();
            int contador = 1;
            for (String stQuery : listBatchQuerys) {
               oracleStatement.addBatch(stQuery);
               if (contador < tamanoCommit) {
                  contador++;
               } else {
                  oracleStatement.executeBatch();
                  oracleConnection.commit();
                  oracleStatement.clearBatch();
                  contador = 0;
               }
            }
            oracleStatement.executeBatch();
            oracleConnection.commit();
            clearBatch();
            return true;
         } else {
            throw new SQLException("Driver does not support batch updates.");
         }
      } catch (BatchUpdateException buExc) {
         System.out.println("error en executeBatch");
         System.out.println("buExc = " + buExc.getMessage());
         clearBatch();
         rollback();
         return false;
      }
   }

   public void clearBatch() throws SQLException {
      if (boSupportsBatch) {
         listBatchQuerys.clear();
      } else {
         throw new SQLException("Driver does not support batch updates.");
      }
   }

   public boolean commit() {
      try {
         oracleConnection.commit();
         return true;
      } catch (SQLException sqlExc) {
         System.out.println("Error en transaccion al realizar - COMMIT: Causa " + sqlExc.getMessage());
         return false;
      }
   }

   public boolean rollback() {
      try {
         oracleConnection.rollback();
         return true;
      } catch (SQLException sqlExc) {
         System.out.println("Error en transaccion al realizar - ROLLBACK: Causa " + sqlExc.getMessage());
         return false;
      }
   }

   public boolean close() {
      try {
         if (oracleStatement != null) {
            oracleStatement.close();
         }
         if (oracleResultSet != null) {
            oracleResultSet.close();
         }
      } catch (Exception sqlEx) {
         oracleStatement = null;
         oracleResultSet = null;
         System.out.println("Exception (DBConnDriver - close(oracleStatement,oracleResultSet):" + sqlEx.getMessage());
      }

      try {
         if (oracleConnection != null) {
            if (!oracleConnection.isClosed()) {
               oracleConnection.close();
              
            }
         }
         return true;
      } catch (Exception sqlExc) {
         oracleConnection = null;
         System.err.println("-------------------problemas al cerrar la conexion BD-------------------. Por: ");
         System.err.println("Exception (DBConnDriver - close): " + sqlExc.getMessage());
         System.err.println("Stacktrace: \n");
         return false;
      }
   }

   public OracleConnection getOracleConnection() {
      try {
         return oracleConnection;
      } catch (Exception sqlExc) {
         System.out.println("Error al obtener <--DELEGATING CONNECTION--> EN: DBConnDriver - getOracleConnection\n" + sqlExc.getMessage());
         return null;
      }
   }

   private void setSupportsBatchStatement() {
      try {
         oracleStatement = (OracleStatement) oracleConnection.createStatement();
         oracleStatement.executeBatch();
         boSupportsBatch = true;
         oracleStatement.close();
      } catch (SQLException sqlExc) {
         System.out.println("Error en ejecucion de batch metodo setSupportsBatchStatement(). Causa " + sqlExc.getMessage());
         boSupportsBatch = false;
      }
   }

   private void creaOracleConexion(String resourceName) {
      try {
         Context initContext = new InitialContext();

         DataSource dataSource = null;
         try {
            dataSource = (DataSource) initContext.lookup(resourceName);
         } catch (Exception e) {
            dataSource = (DataSource) initContext.lookup("java:/comp/env/" + resourceName); //tomcat
         }

         oracleConnection = (OracleConnection) dataSource.getConnection();

      } catch (Exception sqlExc) {
         System.err.println("<---Error al conectar a la BD:...--->\n" + sqlExc.getMessage());
         oracleConnection = null;
      }
   }
}
