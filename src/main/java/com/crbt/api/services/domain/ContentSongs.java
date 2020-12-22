package com.crbt.api.services.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import ch.qos.logback.classic.ViewStatusMessagesServlet;

@Entity
@Table(name = "content_songs", schema = "rbtlibyana")
public class ContentSongs implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = -3566803688003037545L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private Integer id;

	@Column(name = "song_id", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private Integer songId;

	@Column(name = "songs_name", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String songsName;
	
	@Column(name = "songs_name_ar", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String songsNameAr;
	
	@Column(name = "artist_name", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String artistName;
	
	@Column(name = "artist_name_ar", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String artistNameAr;

	@ManyToOne
	@JoinColumn(name = "album_id", referencedColumnName = "id")
	@JsonView(Views.WithMapping.class)
	private ContentAlbum contentAlbum;

	@ManyToOne
	@JsonView(Views.PageData.class)
	@JoinColumn(name = "uploader_type", referencedColumnName = "id")
	private UploadType uploadType;
	/*
	 * @Column(name = "uploader_type", nullable = false)
	 * 
	 * @JsonView( Views.Public.class ) private String uploaderType;
	 */
	@JsonView({ Views.WithMapping.class, Views.PageData.class })
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User userId;
	// @Column(name = "user_id")
	// private Integer userId;

	/*
	 * @Column(name = "approver_id") private Integer approverId;
	 */
	@JsonView({ Views.Public.class, Views.PageData.class })
	@Column(name = "song_status", nullable = false)
	private String songStatus;

	@JsonView({ Views.Public.class, Views.PageData.class })
	@ManyToOne
	@JoinColumn(name = "approver_id", referencedColumnName = "id")
	private User approverId;

	@Column(name = "content_path_location", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String contentPathLocation;

	@Column(name = "content_preview", nullable = false)
	@JsonView({ Views.Public.class, Views.PageData.class })
	private String contentPreview;

	@Column(name = "song_text")
	private String nrbtSongText;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private Date createdDate;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "approved_date", nullable = false)
	@JsonIgnore
	private Date approvedate;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "updated_on", nullable = false)
	@JsonIgnore
	private Date updatedOn;

	public ContentSongs() {
		// TODO Auto-generated constructor stub
		super();
	}

	

	public ContentSongs(Integer id, Integer songId, String songsName, String songsNameAr, String artistName,
			String artistNameAr, ContentAlbum contentAlbum, UploadType uploadType, User userId, String songStatus,
			User approverId, String contentPathLocation, String contentPreview, String nrbtSongText, Date createdDate,
			Date approvedate, Date updatedOn) {
		super();
		this.id = id;
		this.songId = songId;
		this.songsName = songsName;
		this.songsNameAr = songsNameAr;
		this.artistName = artistName;
		this.artistNameAr = artistNameAr;
		this.contentAlbum = contentAlbum;
		this.uploadType = uploadType;
		this.userId = userId;
		this.songStatus = songStatus;
		this.approverId = approverId;
		this.contentPathLocation = contentPathLocation;
		this.contentPreview = contentPreview;
		this.nrbtSongText = nrbtSongText;
		this.createdDate = createdDate;
		this.approvedate = approvedate;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSongId() {
		return songId;
	}

	public void setSongId(Integer songId) {
		this.songId = songId;
	}

	public String getSongsName() {
		return songsName;
	}

	public void setSongsName(String songsName) {
		this.songsName = songsName;
	}
	
	public String getSongsNameAr() {
		return songsNameAr;
	}

	public void setSongsNameAr(String songsNameAr) {
		this.songsNameAr = songsNameAr;
	}

	public ContentAlbum getContentAlbum() {
		return contentAlbum;
	}

	public void setContentAlbum(ContentAlbum contentAlbum) {
		this.contentAlbum = contentAlbum;
	}

	public UploadType getUploadType() {
		return uploadType;
	}

	public void setUploadType(UploadType uploadType) {
		this.uploadType = uploadType;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getSongStatus() {
		return songStatus;
	}

	public void setSongStatus(String songStatus) {
		this.songStatus = songStatus;
	}

	public User getApproverId() {
		return approverId;
	}

	public void setApproverId(User approverId) {
		this.approverId = approverId;
	}

	public String getContentPathLocation() {
		return contentPathLocation;
	}

	public void setContentPathLocation(String contentPathLocation) {
		this.contentPathLocation = contentPathLocation;
	}

	public String getContentPreview() {
		return contentPreview;
	}

	public void setContentPreview(String contentPreview) {
		this.contentPreview = contentPreview;
	}

	public String getNrbtSongText() {
		return nrbtSongText;
	}

	public void setNrbtSongText(String nrbtSongText) {
		this.nrbtSongText = nrbtSongText;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public String getArtistName() {
		return artistName;
	}



	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}



	public String getArtistNameAr() {
		return artistNameAr;
	}



	public void setArtistNameAr(String artistNameAr) {
		this.artistNameAr = artistNameAr;
	}



	@Override
	public String toString() {
		return "ContentSongs [id=" + id + ", songId=" + songId + ", songsName=" + songsName + ", songsNameAr="
				+ songsNameAr + ", artistName=" + artistName + ", artistNameAr=" + artistNameAr + ", contentAlbum="
				+ contentAlbum + ", uploadType=" + uploadType + ", userId=" + userId + ", songStatus=" + songStatus
				+ ", approverId=" + approverId + ", contentPathLocation=" + contentPathLocation + ", contentPreview="
				+ contentPreview + ", nrbtSongText=" + nrbtSongText + ", createdDate=" + createdDate + ", approvedate="
				+ approvedate + ", updatedOn=" + updatedOn + "]";
	}
	
}
