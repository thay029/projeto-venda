/**
 *
 * @author thay
 */
import java.sql.Connection;
import java.sql.DriverManager;


public class Conexao {

    static Connection conectar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Connection getConexao(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projVendas?useTimezone=true&serverTimezone=UTC","root","");
            System.out.println("Conexao realizada com sucesso!");
            return conn;
        }
        catch(Exception e){
            System.out.println("Erro ao conectar no BD"+ e.getMessage());
            return null;
        }
    }
}
