package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Picture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String data;
	private Boolean NoExif;
	private String nome;
	private boolean selected;
	
	@ManyToMany
	@JoinTable(
			name = "picture_album",
			joinColumns=@JoinColumn(name="picture_id"),
			inverseJoinColumns= @JoinColumn (name="album_id")
			
	)
	private Set<Album> albuns = new HashSet<>();
	
	
	@ManyToMany
	@JoinTable(
			name = "picture_tag",
			joinColumns = @JoinColumn(name = "picture_id"),
			inverseJoinColumns = @JoinColumn (name = "tag_id")
	)
	private  Set<Tag> tags = new HashSet<>();
	
	
	public Picture() {
		this.selected=false;
	}
	
	public Picture(String nome) {
		this.selected=false;
		this.nome=nome;
		
	}
	
	public Picture(String nome, String data, Boolean NoExif) {
		this.selected=false;
		this.nome=nome;
		this.data=data;
		this.NoExif=NoExif;
				
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String date) {
		this.data = date;
	}

	public Boolean getNoExif() {
		return NoExif;
	}

	public void setNoExif(Boolean noExif) {
		NoExif = noExif;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Set<Tag> getTags(){
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags=tags;
		for (Tag tag: tags) {
			tag.getPictures().add(this);
		}
	}
	
	public Set<Album> getAlbum(){
		return albuns;
	}
	
	public void setAlbum(Set<Album> albuns) {
		this.albuns=albuns;
	}
	
	public void addTag (Tag tag) {
		if(!tags.contains(tag)) {
			tags.add(tag);
			//tag.addPicture(this);
			
			
		}
	}
	
	public boolean hasTag(Tag tag) {
		if(tags.contains(tag)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void removeTag(Tag tag) {
		if(tags.contains(tag)) {
			tags.remove(tag);
			
			
		}
	}
	
	public void addAlbum(Album album2) {
		if(!albuns.contains(album2)) {
			albuns.add(album2);
		}
	}
	
	
	public void removeAlbum(Album album3) {
		if(albuns.contains(album3)) {
			albuns.remove(album3);
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
		Picture other = (Picture) obj;
		return Objects.equals(nome, other.nome);
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void select() {
		this.selected=true;
	}
	
	public void unselect() {
		this.selected=false;
	}
	
	public String getAppPath() {
		
		String date = getData();
		String[] partes = date.split(" ")[0].split("/");
		String dia = partes[0];
		String mes = partes [1];
		String ano = partes [2];
		String nome = getNome();
		nome = nome.substring(nome.lastIndexOf("/")+1);
		return ano+"/"+mes+"/"+dia+"/"+nome;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", data=" + data + ", NoExif=" + NoExif + ", nome=" + nome + ", albuns=" + albuns
				+ ", tags=" + tags + "]";
	}

	

	
	
	
	


	
	
}
