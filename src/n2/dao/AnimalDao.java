package n2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Animal;
import exemplo.modelo.Departamento;

public class AnimalDAO implements IDao<Animal> {

	public AnimalDAO() {
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
				+ "AND t.name = 'Animal')"
				+ "CREATE TABLE Animal"
				+ " (id	int	IDENTITY,"
				+ "  tutor	VARCHAR(255),"
				+ "	 raca	int,"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<AnimalDAO> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		List<AnimalDAO> Animais = new ArrayList<AnimalDAO>();

		try {
			stmt = conn.createStatement();

			String SQL = "SELECT * FROM Animal";
	        rs = stmt.executeQuery(SQL);

	        while (rs.next()) {
	        	AnimalDAO d = getAnimalFromRs(rs);
	        	
	        	Animais.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllAnimais] Erro ao selecionar os Animais", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Animais;		
	}
	
	public AnimalDAO getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		AnimalDAO Animal = null;
		
		try {
			String SQL = "SELECT * FROM Animal WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);

	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Animal = getAnimalFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAnimalById] Erro ao selecionar Animal por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Animal;		
	}
	
	public void insert(AnimalDAO Animal) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "INSERT INTO Animal (tutor, raca) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, Animal.getTutor());
	    	stmt.setInt(2, Animal.getRaca());
	    	
			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	Animal.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereAnimal] Erro ao inserir um Animal");
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Animal WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteAnimal] Erro ao remover Animal por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(AnimalDAO Animal) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Animal SET tutor = ?, raca = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, Animal.getTutor());
	    	stmt.setInt(2, Animal.getRaca());
	    	stmt.setInt(3, Animal.getId());
	    	
	        stmt.executeUpdate(); // executa o UPDATE			
		} catch (SQLException e) {
			throw new RuntimeException("[updateAnimal] Erro ao atualizar o Animal", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private AnimalDAO getAnimalFromRs(ResultSet rs) throws SQLException {
		AnimalDAO d = new AnimalDAO();
		d.setId(rs.getInt("id"));
		d.settutor(rs.getString("tutor"));
		d.setraca(rs.getString("raca"));
		
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