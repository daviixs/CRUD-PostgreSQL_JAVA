import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String username, String password){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);

            if(conn != null){
                System.out.println("Conectado com sucesso!");
            }
            else{
                System.out.println("Erro na conexão ");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
}
