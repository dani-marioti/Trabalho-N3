package n2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Livro;
import exemplo.modelo.Departamento;

public class LivroDAO implements IDao<Livro> {

	public LivroDAO() {
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTable() throws SQLException {
		final String sqlCreate = "IF NOT EXISTS (" 
				+ "SELECT * FROM sys.tables t JOIN sys.schemas s ON " 
				+ "(t.schema_id = s.schema_id) WHERE s.name = 'dbo'" 
				+ "AND t.name = 'Livro')"
				+ "CREATE TABLE Livro"
				+ " (id	int	IDENTITY,"
				+ "  titulo	VARCHAR(255),"
				+ "	 autor	VARCHAR(10),"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<LivroDAO> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<LivroDAO> Livros = new ArrayList<LivroDAO>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Livro";
	        rs = stmt.executeQuery(SQL);

	        while (rs.next()) {
	        	LivroDAO d = getLivroFromRs(rs);
	        	
	        	Livros.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllLivros] Erro ao selecionar todos os Livros", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Livros;		
	}
	
	public LivroDAO getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		LivroDAO Livro = null;
		
		try {
			String SQL = "SELECT * FROM Livro WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);

	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Livro = getLivroFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getLivroById] Erro ao selecionar o Livro por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Livro;		
	}
	
	public void insert(LivroDAO Livro) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "INSERT INTO Livro (titulo, autor) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, Livro.getTitulo());			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	Livro.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereLivro] Erro ao inserir o Livro", e);
		} finally {
			close(conn, stmt, rs);
		}
        			
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Livro WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteLivro] Erro ao remover o Livro por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(LivroDAO Livro) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Livro SET titulo = ?, autor = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, Livro.getTitulo());
	    	stmt.setString(2, Livro.getAutorr());
	    	stmt.setInt(3, Livro.getId());
	    	
	        stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("[updateLivro] Erro ao atualizar o Livro", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private LivroDAO getLivroFromRs(ResultSet rs) throws SQLException {
		LivroDAO d = new LivroDAO();
		d.setId(rs.getInt("id"));
		d.setTitulo(rs.getString("titulo"));
		d.setAutorr(rs.getString("autor"));
		
		return d;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
	}

}