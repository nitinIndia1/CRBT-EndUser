package com.crbt.api.services.bean;

public class SongsAttributeBean {

	private String songsText;
	private String msisdn;
	private Integer uploadType;
	private Integer albumId;

	public SongsAttributeBean() {
		// TODO Auto-generated constructor stub
	}

	public SongsAttributeBean(String songsText, String msisdn, Integer uploadType, Integer albumId) {
		super();
		this.songsText = songsText;
		this.msisdn = msisdn;
		this.uploadType = uploadType;
		this.albumId = albumId;
	}

	public String getSongsText() {
		return songsText;
	}

	public void setSongsText(String songsText) {
		this.songsText = songsText;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getUploadType() {
		return uploadType;
	}

	public void setUploadType(Integer uploadType) {
		this.uploadType = uploadType;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	@Override
	public String toString() {
		return "SongsAttributeBean [songsText=" + songsText + ", msisdn=" + msisdn + ", uploadType=" + uploadType
				+ ", albumId=" + albumId + "]";
	}

}
