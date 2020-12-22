package com.crbt.api.services.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "content_album", schema = "rbtlibyana", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "album_title" })})
@DynamicUpdate
public class ContentAlbum implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = 8491464415308323871L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	@JsonView( {Views.Public.class, Views.ExtendedPublic.class} )
	private Integer id;

	@Column(name = "album_title", nullable = false)
	@JsonView( {Views.Public.class,Views.PageData.class, Views.ExtendedPublic.class} )
	private String albumTitle;

	@Column(name = "album_description", nullable = false)
	@JsonView(Views.ExtendedPublic.class)
	private String albumDescription;

	
	@Column(name = "album_genre", nullable = false)
	@JsonView(Views.ExtendedPublic.class)
	private String albumGenre;

	@Column(name = "album_artist")
    @JsonView(Views.ExtendedPublic.class)
	private String albumArtist;

	/*
	 * @Column(name = "album_category") private String albumCategory;
	 */
	@ManyToOne
	@JoinColumn(name = "album_category", referencedColumnName = "id")
	@JsonView(Views.PageData.class)
	private Category categoryId;

	@Column(name = "album_art")
	@JsonView(Views.ExtendedPublic.class)
	private String albumArt;
	
	@JsonView(Views.ExtendedPublic.class)
	@Column(name = "album_type")
	private String albumType;

	
	@Column(name = "album_title_ar")
	@JsonView(Views.WithMapping.class)
	private String albumTitleAr;

	@Column(name = "album_description_ar")
	@JsonView(Views.WithMapping.class)
	private String albumDescriptionAr;

	@Column(name = "album_genre_ar")
	@JsonView(Views.WithMapping.class)
	private String albumGenreAr;

	@Column(name = "album_artist_ar")
	@JsonView(Views.WithMapping.class)
	private String albumArtistAr;

	@Column(name = "album_category_ar")
	@JsonView(Views.WithMapping.class)
	private String albumCategoryAr;

	@Column(name = "album_art_ar")
	@JsonView(Views.WithMapping.class)
	private String albumArtAr;

	@Column(name = "album_type_ar")
	@JsonView(Views.WithMapping.class)
	private String albumTypeAr;

	public ContentAlbum() {
		// TODO Auto-generated constructor stub
	}

	public ContentAlbum(Integer id, String albumTitle, String albumDescription, String albumGenre, String albumArtist,
			Category categoryId, String albumArt, String albumType, String albumTitleAr, String albumDescriptionAr,
			String albumGenreAr, String albumArtistAr, String albumCategoryAr, String albumArtAr, String albumTypeAr) {
		super();
		this.id = id;
		this.albumTitle = albumTitle;
		this.albumDescription = albumDescription;
		this.albumGenre = albumGenre;
		this.albumArtist = albumArtist;
		this.categoryId = categoryId;
		this.albumArt = albumArt;
		this.albumType = albumType;
		this.albumTitleAr = albumTitleAr;
		this.albumDescriptionAr = albumDescriptionAr;
		this.albumGenreAr = albumGenreAr;
		this.albumArtistAr = albumArtistAr;
		this.albumCategoryAr = albumCategoryAr;
		this.albumArtAr = albumArtAr;
		this.albumTypeAr = albumTypeAr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getAlbumDescription() {
		return albumDescription;
	}

	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}

	public String getAlbumGenre() {
		return albumGenre;
	}

	public void setAlbumGenre(String albumGenre) {
		this.albumGenre = albumGenre;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	public String getAlbumArt() {
		return albumArt;
	}

	public void setAlbumArt(String albumArt) {
		this.albumArt = albumArt;
	}

	public String getAlbumType() {
		return albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	public String getAlbumTitleAr() {
		return albumTitleAr;
	}

	public void setAlbumTitleAr(String albumTitleAr) {
		this.albumTitleAr = albumTitleAr;
	}

	public String getAlbumDescriptionAr() {
		return albumDescriptionAr;
	}

	public void setAlbumDescriptionAr(String albumDescriptionAr) {
		this.albumDescriptionAr = albumDescriptionAr;
	}

	public String getAlbumGenreAr() {
		return albumGenreAr;
	}

	public void setAlbumGenreAr(String albumGenreAr) {
		this.albumGenreAr = albumGenreAr;
	}

	public String getAlbumArtistAr() {
		return albumArtistAr;
	}

	public void setAlbumArtistAr(String albumArtistAr) {
		this.albumArtistAr = albumArtistAr;
	}

	public String getAlbumCategoryAr() {
		return albumCategoryAr;
	}

	public void setAlbumCategoryAr(String albumCategoryAr) {
		this.albumCategoryAr = albumCategoryAr;
	}

	public String getAlbumArtAr() {
		return albumArtAr;
	}

	public void setAlbumArtAr(String albumArtAr) {
		this.albumArtAr = albumArtAr;
	}

	public String getAlbumTypeAr() {
		return albumTypeAr;
	}

	public void setAlbumTypeAr(String albumTypeAr) {
		this.albumTypeAr = albumTypeAr;
	}

	@Override
	public String toString() {
		return "ContentAlbum [id=" + id + ", albumTitle=" + albumTitle + ", albumDescription=" + albumDescription
				+ ", albumGenre=" + albumGenre + ", albumArtist=" + albumArtist + ", categoryId=" + categoryId
				+ ", albumArt=" + albumArt + ", albumType=" + albumType + ", albumTitleAr=" + albumTitleAr
				+ ", albumDescriptionAr=" + albumDescriptionAr + ", albumGenreAr=" + albumGenreAr + ", albumArtistAr="
				+ albumArtistAr + ", albumCategoryAr=" + albumCategoryAr + ", albumArtAr=" + albumArtAr
				+ ", albumTypeAr=" + albumTypeAr + "]";
	}

}
