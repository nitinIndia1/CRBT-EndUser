package com.crbt.api.services.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.crbt.api.services.bean.ChangeAlbumStatusBean;
import com.crbt.api.services.bean.SongLoadResponse;
import com.crbt.api.services.bean.SongRequestBean;
import com.crbt.api.services.domain.ContentSongs;

public interface ContentSongsService {

	ContentSongs getSongsByid(@NotNull @Valid final Integer id);

	ContentSongs update(@Valid @NotNull final ContentSongs contentSongs);

	ContentSongs save(@Valid @NotNull final ContentSongs contentSongs);

	ContentSongs getSongsBySongId(@Valid @NotNull final Integer song_id);
	
	Page<ContentSongs> getAlbumByAlbumId(@Valid @NotNull final Integer album_id, Pageable page);

	Page<ContentSongs> getAllSongsByCategoryId( @Valid @NotNull final Integer categoryId, Pageable page );
	
	List<ContentSongs> getAllSongsByCategory( @Valid @NotNull final Integer categoryId);
	
	ContentSongs getChangeSong(Integer song_id);

	void saveUpdateSongStatus(ContentSongs contentSongs);

	void updateContentSongByAlbumId( @Valid @NotNull ChangeAlbumStatusBean bean );

	List<ContentSongs> getChangeAlbum(Integer album_id);

	//SongLoadResponse getContentSongsByUploadNewSongs(Integer song_id, String songs_name, String content_path_location, Integer integer, String string);

	SongLoadResponse getContentSongsByUploadNewSongs(Integer song_id, String songs_name, String uploader_type,
			Integer album_id, MultipartFile content_path_location);
	
	ContentSongs getSongIdAndSongLocationBase(@Valid @NotNull Integer song_id);

	List<ContentSongs> saveAllSongs(List<ContentSongs> contentSongs);

	List<ContentSongs> saveAllAlbumIdSongs( List<ContentSongs> contentSongs, MultipartFile file) ;

	List<ContentSongs> getSongs_By_Name(@Valid @NotNull final String songs_name);

	List<ContentSongs> listAllSongs();
	
	
	Integer getMaxSongId();
	
	ContentSongs getUploadTypeByAlbumId(@Valid @NotNull final Integer album_id , Integer uploader_type);

	String getSongList();
	
	
}
