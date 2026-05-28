/**
 *
 * @author thay
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class notaDAO {
    private Conexao conexao;
    private Connection conn;
    private String nomeCliente;
    
    public notaDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(NotaFiscal nota){
        String sql = "INSERT INTO notaFiscal (data, total, idCliente) VALUES (?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, nota.getData());
            stmt.setDouble(2, nota.getTotal());
            stmt.setInt(3, nota.getIdCliente());
            stmt.execute();
        }
        catch(SQLException ex){
            System.out.println("Erro ao criar nota fiscal: " + ex.getMessage());
        }
    }
    
    public NotaFiscal getNotaFiscal(int id){
        String sql = "SELECT * FROM notaFiscal WHERE idNota = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            NotaFiscal n = new NotaFiscal();
            rs.first();
            n.setIdNota(id);
            n.setData(rs.getString("data"));
            n.setTotal(rs.getDouble("total"));
            n.setIdCliente(rs.getInt("idCliente"));
            return n;
        } catch (SQLException ex){
            System.out.println("Erro ao consultar Nota Fiscal: "+ex.getMessage());
            return null;
        }
    }
    
    public void editar(NotaFiscal nota){
        try{
            String sql = "UPDATE notaFiscal set data=?, total=?, idCliente=? WHERE idNota=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nota.getData());
            stmt.setDouble(2, nota.getTotal());
            stmt.setInt(3, nota.getIdCliente());
            stmt.setInt(4, nota.getIdNota());
            stmt.execute();
        } catch (SQLException ex){
            System.out.println("Erro ao editar Nota Fiscal: "+ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try{
            String sql = "delete from notaFiscal where idNota=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch (SQLException ex){
            System.out.println("Erro ao excluir Nota Fiscal: "+ex.getMessage());
        }
    }
    
    public List<NotaFiscal> getNotas(){

    String sql = "SELECT n.*, c.nome AS nomeCliente " +
                 "FROM notaFiscal n " +
                 "INNER JOIN clientes c ON n.idCliente = c.idCliente";

    try{

        PreparedStatement stmt = conn.prepareStatement(
            sql,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        );

        ResultSet rs = stmt.executeQuery();

        List<NotaFiscal> listaNotas = new ArrayList<>();

        while(rs.next()){

            NotaFiscal n = new NotaFiscal();

            n.setIdNota(rs.getInt("idNota"));
            n.setData(rs.getString("data"));
            n.setTotal(rs.getDouble("total"));
            n.setIdCliente(rs.getInt("idCliente"));

            
            n.setNomeCliente(rs.getString("nomeCliente"));

            listaNotas.add(n);
        }

        return listaNotas;

    } catch(SQLException ex){

        System.out.println("Erro ao consultar todas as Notas Fiscais: "+ex.getMessage());

        return new ArrayList<>();
    }
}
    
}
