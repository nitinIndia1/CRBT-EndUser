package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.bean.CategorySongsResponseBean;
import com.crbt.api.services.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	String nativeQuery = "SELECT * FROM rbtlibyana.category WHERE category_name LIKE %:category_name% OR category_name_ar LIKE %:category_name_ar% ";

	@Query(value = nativeQuery, nativeQuery = true)
	List<Category> findCategoryByName(@Param("category_name") String category_name,
			@Param("category_name_ar") String category_name_ar);

	// String nativeQuery1="SELECT * FROM rbtlibyana.category order by priority
	// asc";

	String nativeQuery1 = "SELECT * FROM rbtlibyana.category order by priority asc";

	@Query(value = nativeQuery1, nativeQuery = true)
	List<Category> findAllByPriorityOrder();

	// String nativeQuery3="SELECT cs.song_id as songid,
	// cs.content_path_location as songName, c.id as categoryid, c.priority as
	// priority , c.category_name as category_name FROM rbtlibyana.content_songs
	// cs JOIN rbtlibyana.category c ORDER BY priority asc";
	/*String nativeQuery3= "select cs.song_id, c.id as category_id, cs.content_path_location as song_name, c.category_name, c.priority as id_index from rbtlibyana.category c "
			+"join rbtlibyana.content_album ca on c.id = ca.album_category "
			+"join rbtlibyana.content_songs cs on cs.album_id = ca.id "
			+"order by c.priority asc "; */

	@Query( "select new com.crbt.api.services.bean.CategorySongsResponseBean( cs.songId , cs.contentAlbum.categoryId.id , cs.songsName ,  cs.contentPathLocation, cs.contentAlbum.categoryId.categoryName, cs.contentAlbum.categoryId.priority ) from ContentSongs cs ")
	List<CategorySongsResponseBean> findAllTop20Songs();
	
	@Query( "FROM Category c WHERE c.id NOT IN (11) AND c.isActive = 'Y'" )
	Page<Category> findAllCategory( Pageable page );

}
