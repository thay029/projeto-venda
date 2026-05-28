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

public class clienteDAO {
    private Conexao conexao;
    private Connection conn;
    
    public clienteDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Cliente cliente){
        String sql = "INSERT INTO clientes (nome, cpf, telefone) VALUES (?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.execute();
        }
        catch(SQLException ex){
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
        }
    }
    
    public Cliente getCliente(int id){
        String sql = "SELECT * FROM clientes WHERE idCliente = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Cliente c = new Cliente();
            rs.first();
            c.setIdCliente(id);
            c.setNome(rs.getString("nome"));
            c.setCpf(rs.getString("cpf"));
            c.setTelefone(rs.getString("telefone"));
            return c;
        } catch (SQLException ex){
            System.out.println("Erro ao consultar cliente: "+ex.getMessage());
            return null;
        }
    }
    
    public void editar(Cliente cliente){
        try{
            String sql = "UPDATE clientes set nome=?, cpf=?, telefone=? WHERE idCliente=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getIdCliente());
            stmt.execute();
        } catch (SQLException ex){
            System.out.println("Erro ao atualizar cliente: "+ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try{
            String sql = "delete from clientes where idCliente=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex){
            System.out.println("Erro ao excluir cliente: "+ex.getMessage());
        }
    }
    
    public List<Cliente> getClientes(){
        String sql = "select * from clientes";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            List<Cliente> listaClientes = new ArrayList();
            while(rs.next()){
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                listaClientes.add(c);
            }
            return listaClientes;
        } catch(SQLException ex){
            System.out.println("Erro ao consultar todos os clientes: "+ex.getMessage());
            return null;
        }
    }
    
}
