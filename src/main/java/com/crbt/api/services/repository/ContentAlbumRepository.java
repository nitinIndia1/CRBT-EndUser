package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.domain.ContentAlbum;

public interface ContentAlbumRepository extends JpaRepository<ContentAlbum, Integer> {

	//String nativeQuery2 = "select * from rbtlibyana.content_album where album_category = :album_category";
   // @Query(value = nativeQuery2, nativeQuery = true)
	//"select um from UserMaster um where um.oauthClientDetails.clientId = :username"
	@Query("select ca from ContentAlbum  ca where ca.categoryId.id = :album_category")
    Page<ContentAlbum> findByCategoryId(@Param("album_category") Integer album_category, Pageable page);

	
	String nativeQuery="SELECT * FROM rbtlibyana.content_album WHERE album_title LIKE %:album_title% OR album_title_ar LIKE %:album_title_ar% ";
    @Query(value = nativeQuery , nativeQuery=true)
	
	List<ContentAlbum> getAlbumByName(@Param("album_title")String album_title, @Param("album_title_ar")String album_title_ar);
	
    @Query("select ca from ContentAlbum  ca where ca.albumTitle = :album_title")
    ContentAlbum getAlbumTitle(@Param("album_title") String album_title);

}
