package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String nome;
	
	@Embedded
	private Color cor;
	
	
	@ManyToMany(mappedBy = "tags")
	private Set<Picture> imgInseridas = new HashSet<>();
	
	
	public Tag() {
		
	}
	
	public Tag(String nome, Color cor) {
		this.nome=nome;
		this.cor=cor;
	}
	
	public Tag(String nome) {
		this.nome=nome;
	}
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Color getCor() {
		return cor;
	}
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public Set<Picture> getPictures(){
		return imgInseridas;
	}
	
	public void setPictures(Set<Picture> pictures) {
		this.imgInseridas=pictures;
	}

	public void addPicture(Picture pic) {
		if(!imgInseridas.contains(pic)) {
			imgInseridas.add(pic);
		}
		
		
	}
	
	public void removePicture(Picture pic) {
		if(imgInseridas.contains(pic)) {
			imgInseridas.remove(pic);
		}
		
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
		Tag other = (Tag) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", nome=" + nome + ", cor=" + cor + ", imgInseridas=" + imgInseridas + "]";
	}
	
	
	

	
	
}
