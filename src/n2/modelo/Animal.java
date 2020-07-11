package n2.modelo;

public class Animal {
	private int id;
	private int raca;
	private String tutor;

	public Animal() { }

	public Animal(int id, String tutor, int raca) {
		this.id = id;
		this.tutor = tutor;
		this.raca = raca;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public int getRaca() {
		return raca;
	}

	public void setRaca(int raca) {
		this.raca = raca;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", tutor=" + tutor + ", raca=" + raca + "]";
	}
	
	
}