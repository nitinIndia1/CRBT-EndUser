package com.crbt.api.services.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.domain.ContentSongs;

public interface ContentSongsRepository extends JpaRepository<ContentSongs, Integer>{


    String nativeQuery="select * from rbtlibyana.content_songs where song_id =:song_id";
	
	@Query(value = nativeQuery,nativeQuery=true)
	ContentSongs findBySongId(@Param("song_id")Integer song_id);

    String nativeQuery2="select * from rbtlibyana.content_songs where album_id = :album_id";
	
	@Query(value = nativeQuery2,nativeQuery=true)
	List<ContentSongs> findByAlbumID(@Param("album_id") Integer album_id);
	
	@Transactional
	@Modifying
	@Query( "Update ContentSongs SET songStatus = :status WHERE contentAlbum.id = :albumId" )
	void updateSongStatusByAlbumId( @Param("status") String status, @Param("albumId") Integer albumId );

	
	//@Query("select * from rbtlibyana.content_songs where id= :id and content_path_location=:content_path_location ")
	 String nativeQuery3="select * from rbtlibyana.content_songs where song_id= :song_id  ";
	 @Query(value = nativeQuery3,nativeQuery=true)
	 ContentSongs findSongIdAndSongLocationBase(@Param("song_id")Integer song_id);

	 @Query("select cs from ContentSongs cs where cs.contentAlbum.id =:album_id and cs.songStatus = 'Y'")
	 Page<ContentSongs> findByAlbumID(@Param("album_id")Integer album_id, Pageable page);
	
	 @Query("select cs from ContentSongs cs where cs.songsName LIKE %:songs_name%")
	// String nativeQuery4="select * from rbtlibyana.content_songs where songsName LIKE %:songs_name% ";
	 //@Query(value = nativeQuery4,nativeQuery=true)
	 List<ContentSongs> searchSongsByName(@Param("songs_name")String songs_name);

	
	 
	 String nativeQuery4="SELECT max(song_id) FROM rbtlibyana.content_songs ";
	 @Query(value = nativeQuery4,nativeQuery=true)
	 Integer findMaxIdBySongsId();

	 String nativeQuery5="SELECT * FROM rbtlibyana.content_songs where album_id=:album_id and uploader_type=:uploader_type and song_status='In-Active' order by created_date desc limit 1";
	 @Query(value = nativeQuery5,nativeQuery=true)
	// @Query("select cs from ContentSongs cs where cs.contentAlbum=:album_id and cs.uploadType=:uploader_type and songStatus='In-Active' order by created_date desc limit 1 ")
     ContentSongs findUploadTypeByAlbumId(@Param("album_id")Integer album_id, @Param("uploader_type")Integer uploader_type);

	 @Query("select  cs from ContentSongs cs where cs.contentAlbum.categoryId.id =:categoryId and cs.songStatus = 'Y' order by cs.songId asc")
	 Page<ContentSongs> getAllSongsByCategoryId(@Param("categoryId")Integer categoryId, Pageable page);	 
	 
	 @Query("select  cs from ContentSongs cs where cs.contentAlbum.categoryId.id =:categoryId and cs.songStatus = 'Y' order by cs.songId asc")
	 List<ContentSongs> getAllSongsByCategory(@Param("categoryId")Integer categoryId);	
	
	
}
