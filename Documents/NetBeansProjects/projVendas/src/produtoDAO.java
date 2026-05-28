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
import java.util.Set;


public class produtoDAO {
    private Conexao conexao;
    private Connection conn;
    
    public produtoDAO(){
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public void inserir(Produto produto){
        String sql = "INSERT INTO produtos (nome, preco, qtdeEstoque) VALUES (?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtdeEstoque());
            stmt.execute();
        }
        catch(SQLException ex){
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }
    
    public Produto getProduto(int id){
        String sql = "SELECT * FROM produtos WHERE idProduto = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produto p = new Produto();
             if (rs.next()) {
            p.setIdProduto(rs.getInt("idProduto"));
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getDouble("preco"));
            p.setQtdeEstoque(rs.getInt("qtdeEstoque"));
        }
            return p;
        } catch (SQLException ex){
            System.out.println("Erro ao consultar produto: "+ex.getMessage());
            return null;
        }
    }
    
    public void editar(Produto produto){
        try{
            String sql = "UPDATE produtos set nome=?, preco=?, qtdeEstoque=? WHERE idProduto=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtdeEstoque());
            stmt.setInt(4, produto.getIdProduto());
            stmt.execute();
        } catch (SQLException ex){
            System.out.println("Erro ao editar Produto: "+ex.getMessage());
        }
    }
    
    public void excluir(int id){
        try{
            String sql = "delete from produtos where idProduto=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }catch (SQLException ex){
            System.out.println("Erro ao excluir Produto: "+ex.getMessage());
        }
    }
    public Produto buscarProduto(int id) {
    Produto p = new Produto();

    try {
        
        
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            p.setIdProduto(rs.getInt("idProduto"));
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getDouble("preco"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return p;
}
    public List<Produto> getProdutos(){
        String sql = "select * from produtos";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            List<Produto> listaProdutos = new ArrayList();
            while(rs.next()){
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQtdeEstoque(rs.getInt("qtdeEstoque"));
                listaProdutos.add(p);
            }
            return listaProdutos;
        } catch(SQLException ex){
            System.out.println("Erro ao consultar todos os produtos: "+ex.getMessage());
            return null;
        }
    }
    
}
