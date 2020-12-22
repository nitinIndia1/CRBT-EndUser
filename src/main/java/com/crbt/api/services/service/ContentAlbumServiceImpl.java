package com.crbt.api.services.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.domain.ContentAlbum;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.repository.ContentAlbumRepository;

@Service
@Validated
public class ContentAlbumServiceImpl implements ContentAlbumService{

	private static final Logger logger = LoggerFactory.getLogger( ContentAlbumServiceImpl.class );
	private final ContentAlbumRepository repository;
	
	@Inject
	public ContentAlbumServiceImpl(ContentAlbumRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public ContentAlbum getContentAlbumByid(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<ContentAlbum> listAllContentAlbum(Pageable page) {
		// TODO Auto-generated method stub
		return repository.findAll(page);
	}

	@Override
	public ContentAlbum update(ContentAlbum contentAlbum) {
		// TODO Auto-generated method stub
		logger.info( "Received request to create new entry for contentAlbum." );
		logger.info( "Update {}", contentAlbum );
		return repository.saveAndFlush(contentAlbum);
	}

	@Override
	public ContentAlbum save(ContentAlbum contentAlbum) {
		logger.info( "Received request to create new entry for contentAlbum." );
		logger.info( "Creating {}", contentAlbum );
		ContentAlbum saveUser=null;
		try{
			saveUser= repository.save(contentAlbum);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return saveUser;
	
		//return repository.save(contentAlbum);
	}

	@Override
	public void delteContentAlbumById(Integer id) {
		logger.info( "Received request to delete contentAlbum for id {}", id );
		try {
			repository.delete(id);
		}catch( Exception e ) {
			
		}	
	}

	
	@Override
	public ContentAlbum getContentById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public ContentAlbum getContentAlbumById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<ContentAlbum> getAlbumByAlbumCategoryId(Integer album_category , Pageable page) {
		// TODO Auto-generated method stub
		return repository.findByCategoryId(album_category, page);
	}

	@Override
	public List<ContentAlbum> getCategoryByName(String album_title, String album_title_ar) {
		// TODO Auto-generated method stub
		return repository.getAlbumByName(album_title, album_title_ar);
	}

	@Override
	public ContentAlbum getAlbumName(String album_title) {
		// TODO Auto-generated method stub
		return repository.getAlbumTitle(album_title);
	}

	
	
}
