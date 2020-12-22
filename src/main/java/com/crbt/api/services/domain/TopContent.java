package com.crbt.api.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "top_content", catalog = "rbtlibyana")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TopContent implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6672907557031092528L;
	private Integer id;
	private int songId;
	private String songName;
	private String category;
	private int categoryId;
	private String order;
	private int uploaderType;
	
	private int isActive;
	private String contentPathLocation;
	private String albumArt;
	private String categoryNameAr;
	private String artistName;
	private String artistNameAr;
	private String songNameAr;
	private int userId;
	private int approverId;
	private int albumId;
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "approved_date", nullable = false)
	private Date approvedDate;

	public TopContent() {
	}

	public TopContent(int songId, String songName, String category, int categoryId, String order) {
		this.songId = songId;
		this.songName = songName;
		this.category = category;
		this.categoryId = categoryId;
		this.order = order;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "song_id", nullable = false)
	public int getSongId() {
		return this.songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}


	@Column(name = "song_name", nullable = false, length = 65535)
	public String getSongName() {
		return this.songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	@Column(name = "category", nullable = false, length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "category_id", nullable = false)
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "song_order", nullable = false, length = 45)
	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	@Column(name = "is_active", nullable = false)
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Column(name = "content_path_location", nullable = false)
	public String getContentPathLocation() {
		return contentPathLocation;
	}

	public void setContentPathLocation(String contentPathLocation) {
		this.contentPathLocation = contentPathLocation;
	}

	@Column(name = "album_art", nullable = false)
	public String getAlbumArt() {
		return albumArt;
	}

	
	public void setAlbumArt(String albumArt) {
		this.albumArt = albumArt;
	}

	@Column(name = "category_name_ar", nullable = false, length = 135)
	public String getCategoryNameAr() {
		return categoryNameAr;
	}

	
	public void setCategoryNameAr(String categoryNameAr) {
		this.categoryNameAr = categoryNameAr;
	}

	@Column(name = "artist_name", nullable = false, length = 135)
	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}


	@Column(name = "songs_name_ar", nullable = false, length = 135)
	public String getSongNameAr() {
		return songNameAr;
	}

	public void setSongNameAr(String songNameAr) {
		this.songNameAr = songNameAr;
	}

	@Column(name = "user_id", nullable = true)
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "approver_id", nullable = true)
	public int getApproverId() {
		return approverId;
	}

	
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	@Column(name = "album_id", nullable = false)
	public int getAlbumId() {
		return albumId;
	}
	
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "artist_name_ar", nullable = false, length = 135)
	public String getArtistNameAr() {
		return artistNameAr;
	}

	public void setArtistNameAr(String artistNameAr) {
		this.artistNameAr = artistNameAr;
	}

	@Column(name = "uploader_type", nullable = false)
	public int getUploaderType() {
		return uploaderType;
	}

	public void setUploaderType(int uploaderType) {
		this.uploaderType = uploaderType;
	}

	

	

}
