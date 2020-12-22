package com.crbt.api.services.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.ContentAlbum;


public interface ContentAlbumService {

	ContentAlbum getContentAlbumByid(@NotNull @Valid final Integer id);

	Page<ContentAlbum> listAllContentAlbum(Pageable page);

	ContentAlbum update(@NotNull @Valid final ContentAlbum contentAlbum);

	ContentAlbum save(@NotNull @Valid final ContentAlbum contentAlbum);

	void delteContentAlbumById(@NotNull @Valid final Integer id);

	ContentAlbum getContentAlbumById(@NotNull @Valid final Integer id);

	ContentAlbum getContentById(Integer id);

	Page<ContentAlbum> getAlbumByAlbumCategoryId(@NotNull @Valid Integer album_category, Pageable page);

	List<ContentAlbum> getCategoryByName( String album_title, String album_title_ar);

	ContentAlbum getAlbumName(String album_title);
	
  }
