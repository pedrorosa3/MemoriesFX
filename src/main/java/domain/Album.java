package domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String descricao;
	
	@ManyToOne
	private Tag tag;
	
	@ManyToMany(mappedBy = "albuns")
	private List<Picture> pictures = new ArrayList<>();
	
	
	public Album() {
		
	}
	
	
	public Album(String name, String description) {
		this.nome=name;
		this.descricao=description;
	}
	public Album(Tag tag) {
		this.tag=tag;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Tag getTag () {
		return tag;
		
	}
	
	public void setTag(Tag tag) {
		this.tag=tag;
		
	}
	
	public List<Picture> getPictures(){
		return pictures;
	}
	
	public void setPictures(List<Picture> pictures) {
		this.pictures=pictures;
		for(Picture picture : pictures) {
			picture.addAlbum(this);
		}
	}
	
	
	public void addPicture(Picture pic) {
		if(!pictures.contains(pic)) {
			pictures.add(pic);
			pic.addAlbum(this);
		}
	}
	
	public void removePicture(Picture pic) {
		if(pictures.contains(pic)) {
			pictures.remove(pic);
			pic.removeAlbum(this);
			
		}
	}
	
	public void addTag(Tag tagnova) {
		if(tag==null) {
			tag=tagnova;
			
		}
	}
	
	public void removeTag() {
		
		tag=null;
	
	}
	
	public void moveUp(Picture p) {
		int i = pictures.indexOf(p);
		if(i != 0) {
			Picture pic = pictures.get(i-1);
			pictures.set(i-1, p);
			pictures.set(i, pic);
		}
		
	}
	
	
	public void moveDown(Picture p) {
		int i = pictures.indexOf(p);
		if(i != (pictures.size()-1)) {
			Picture pic = pictures.get(i+1);
			pictures.set(i+1, p);
			pictures.set(i, pic);
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
		Album other = (Album) obj;
		return Objects.equals(nome, other.nome);
	}


	@Override
	public String toString() {
		return "Album [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", tag=" + tag + ", pictures="
				+ pictures + "]";
	}
	
	
	
	
}
