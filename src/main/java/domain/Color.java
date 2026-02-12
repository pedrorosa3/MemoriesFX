package domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Color {
	@Column(name = "Color_name")
	private String nome;
	
	public Color() {
		
	}
	public Color(String nome) {
		this.nome=nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		return Objects.equals(nome, other.nome);
	}
	@Override
	public String toString() {
		return "Color [nome=" + nome + "]";
	}
	
	
}
