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


public class itemDAO {
    private Conexao conexao;
    private Connection conn;
    
    public itemDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Item item){
        String sql = "INSERT INTO itens (idNota, idProduto, quantidade, subtotal) VALUES (?,?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, item.getIdNota());
            stmt.setInt(2, item.getIdProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getSubtotal());
            stmt.execute();
        }
        catch(SQLException ex){
            System.out.println("Erro ao criar itens nota fiscal: " + ex.getMessage());
        }
    }
    
    public Item getItem(int id){
           String sql = "SELECT * FROM itens WHERE idItem = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Item i = new Item();
            i.setIdItem(rs.getInt("idItem"));
            i.setIdNota(rs.getInt("idNota"));
            i.setIdProduto(rs.getInt("idProduto"));
            i.setQuantidade(rs.getInt("quantidade"));
            i.setSubtotal(rs.getDouble("subtotal"));
            return i;
        }

        return null; 

    } catch (SQLException ex){
        System.out.println("Erro ao consultar item: " + ex.getMessage());
        return null;
    }

    }
    
    public void editar(Item item){
        try{
            String sql = "UPDATE itens set idNota=?, idProduto=?, quantidade=?, subtotal=? WHERE idItem=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, item.getIdNota());
            stmt.setInt(2, item.getIdProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getSubtotal());
            stmt.setInt(5, item.getIdItem());
            stmt.execute();
        } catch (SQLException ex){
            System.out.println("Erro ao editar Item Nota Fiscal: "+ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try{
            String sql = "delete from itens where idItem=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch (SQLException ex){
            System.out.println("Erro ao excluir Item nota fiscal: "+ex.getMessage());
        }
    }
    
    public List<Item> getItens(){
             String sql = "SELECT i.*, " +
             "c.nome AS nomeCliente, " +
             "p.nome AS nomeProduto " +
             "FROM itens i " +
             "INNER JOIN notafiscal n ON i.idNota = n.idNota " +
             "INNER JOIN clientes c ON n.idCliente = c.idCliente " +
             "INNER JOIN produtos p ON i.idProduto = p.idProduto";
    try{
        PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery();
        List<Item> listaItens = new ArrayList();
        while(rs.next()){
            Item i = new Item();
            i.setIdItem(rs.getInt("idItem"));
            i.setIdNota(rs.getInt("idNota"));
            i.setIdProduto(rs.getInt("idProduto"));
            i.setQuantidade(rs.getInt("quantidade"));
            i.setSubtotal(rs.getDouble("subtotal"));
            i.setNomeCliente(rs.getString("nomeCliente"));
            i.setNomeProduto(rs.getString("nomeProduto"));
            listaItens.add(i);
        }
        return listaItens;
    } catch(SQLException ex){
        System.out.println("Erro ao consultar todos os itens: "+ex.getMessage());
        return null;
    }
}
    
}
