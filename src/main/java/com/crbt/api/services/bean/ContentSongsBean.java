package com.crbt.api.services.bean;

import java.io.Serializable;
import java.util.Date;

import com.crbt.api.services.domain.ContentAlbum;
import com.crbt.api.services.domain.UploadType;
import com.crbt.api.services.domain.User;

public class ContentSongsBean implements Serializable{

	private static final long serialVersionUID = 1658137734260666099L;
	
	private Integer id;
	private Integer songId;
	private String songsName;
	private String artistName;
	private ContentAlbum contentAlbum;
	private UploadType uploadType;
	private User userId;
	private String songStatus;
	private User approverId;
	private String contentPathLocation;
	private String contentPreview;
	private String nrbtSongText;
	private Date createdDate;
	private Date approvedate;
	private Date updatedOn;
	
	public ContentSongsBean() {}

	public ContentSongsBean(Integer id, Integer songId, String songsName, String artistName, ContentAlbum contentAlbum,
			UploadType uploadType, User userId, String songStatus, User approverId, String contentPathLocation,
			String contentPreview, String nrbtSongText, Date createdDate, Date approvedate, Date updatedOn) {
		super();
		this.id = id;
		this.songId = songId;
		this.songsName = songsName;
		this.artistName = artistName;
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

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
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

	@Override
	public String toString() {
		return "ContentSongsBean [id=" + id + ", songId=" + songId + ", songsName=" + songsName + ", artistName="
				+ artistName + ", contentAlbum=" + contentAlbum + ", uploadType=" + uploadType + ", userId=" + userId
				+ ", songStatus=" + songStatus + ", approverId=" + approverId + ", contentPathLocation="
				+ contentPathLocation + ", contentPreview=" + contentPreview + ", nrbtSongText=" + nrbtSongText
				+ ", createdDate=" + createdDate + ", approvedate=" + approvedate + ", updatedOn=" + updatedOn + "]";
	}	

}
