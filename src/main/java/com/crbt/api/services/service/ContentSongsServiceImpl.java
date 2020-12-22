package com.crbt.api.services.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Sort;
import com.crbt.api.services.bean.ChangeAlbumStatusBean;
import com.crbt.api.services.bean.SongLoadResponse;
import com.crbt.api.services.bean.SongRequestBean;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.domain.ContentAlbum;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.TopContent;
import com.crbt.api.services.repository.CategoryRepository;
import com.crbt.api.services.repository.ContentSongsRepository;
import com.crbt.api.services.repository.TopContentRepository;

@Service
@Validated
public class ContentSongsServiceImpl implements ContentSongsService{

	private static final Logger logger = LoggerFactory.getLogger( ContentSongsServiceImpl.class );
	@Value("${file.Window_SOURCE_FOLDER}")
	private String Window_SOURCE_FOLDER;
	//private static final String Window_SOURCE_FOLDER = "C:/temp";
	//private final static String Window_SOURCE_FOLDER = "/opt/Libyana/songs";
	//private final static String Window_SOURCE_FOLDER = "/opt/apache-tomcat-7.0.63/webapps/ROOT/Libyana/songs";
	//private final static String Window_SOURCE_FOLDER = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/ROOT/Libyana/songs";

	private final ContentSongsRepository repository;

	@Autowired
	CategoryRepository categoryRepository;
	
	
	
	@Inject
	public ContentSongsServiceImpl(ContentSongsRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public ContentSongs getSongsByid(Integer id) {
		logger.info( "Received request to songs by id {}", id );
		return repository.findOne(id);
	}

	@Override
	public ContentSongs update(ContentSongs contentSongs) {
		logger.info("update Contentsongs record info"+contentSongs);
		contentSongs.setCreatedDate(contentSongs.getCreatedDate());
		contentSongs.setUpdatedOn(new Date());
		contentSongs.setApprovedate(new Date());
		return repository.saveAndFlush(contentSongs);
	}

	@Override
	public ContentSongs save(ContentSongs contentSongs) {
		logger.info( "Received request to create new entry for Contentsongs." );
		logger.info( "Creating {}", contentSongs );
		//contentSongs.setUploaderType("online");
		//int random = (int )(Math.random() * 50 + 1);
		//contentSongs.setSongId((int) Math.random() * 50 + 1);
		//return repository.save(contentSongs);
		
		ContentSongs saveUser=null;
		try{
			
			contentSongs.setApprovedate(new Date());
			contentSongs.setCreatedDate(new Date());
			contentSongs.setUpdatedOn(new Date());
			
			saveUser= repository.save(contentSongs);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return saveUser;
	}

	@Override
	public ContentSongs getSongsBySongId(Integer song_id) {
		// TODO Auto-generated method stub
		return repository.findBySongId(song_id);
	}

	@Override
	public ContentSongs getChangeSong(Integer song_id) {
		// TODO Auto-generated method stub
		return repository.findBySongId(song_id);
	}

	@Override
	public void saveUpdateSongStatus(ContentSongs contentSongs) {
		// TODO Auto-generated method stub
		contentSongs.setApprovedate(new Date());
		contentSongs.setUpdatedOn(new Date());
		repository.saveAndFlush(contentSongs);
	}

	@Override
	public Page<ContentSongs> getAlbumByAlbumId(Integer album_id, Pageable page) {
		// TODO Auto-generated method stub
		return repository.findByAlbumID(album_id , page);
	}

	@Override
	public List<ContentSongs> getChangeAlbum(Integer album_id) {
		// TODO Auto-generated method stub
		return repository.findByAlbumID(album_id);
	}

	@Override
	public void updateContentSongByAlbumId(ChangeAlbumStatusBean bean) {
		repository.updateSongStatusByAlbumId( bean.getSong_status(), bean.getAlbum_id());
		
	}



	@Override
	public SongLoadResponse getContentSongsByUploadNewSongs(Integer song_id, String songs_name, String uploader_type,
			Integer album_id, MultipartFile content_path_location) {
		    SongLoadResponse response = new SongLoadResponse();
		return response;
	
	}

	@Override
	public ContentSongs getSongIdAndSongLocationBase(Integer song_id) {
		// TODO Auto-generated method stub
		return repository.findSongIdAndSongLocationBase(song_id );
	}

	@Override
	public List<ContentSongs> saveAllSongs(List<ContentSongs> contentSongs) {
		List<ContentSongs> resp = new ArrayList<ContentSongs>();
		for( ContentSongs song: contentSongs ){
			song.setUpdatedOn(new Date());
			//song.setContentPathLocation()
			repository.save(song);
			resp.add(song);
			
		}
		return resp;
	}

	@Override
	public List<ContentSongs> saveAllAlbumIdSongs( List<ContentSongs> contentSongs, MultipartFile file)  {
		// TODO Auto-generated method stub
		List<ContentSongs> bean= new ArrayList<ContentSongs>();
		for( ContentSongs song: contentSongs ){
			song.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
			song.setSongsName(file.getOriginalFilename());
			song.setUpdatedOn( new Date() );
			bean.add( song );
			repository.save( song );
		}
		
		return bean;
	}

	@Override
	public List<ContentSongs> getSongs_By_Name(String songs_name) {
		// TODO Auto-generated method stub
		return repository.searchSongsByName(songs_name);
	}

	@Override
	public List<ContentSongs> listAllSongs() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Integer getMaxSongId() {
		// TODO Auto-generated method stub
		return repository.findMaxIdBySongsId();
	}

	@Override
	public ContentSongs getUploadTypeByAlbumId(Integer album_id, Integer uploader_type) {
		// TODO Auto-generated method stub
		return repository.findUploadTypeByAlbumId(album_id , uploader_type);
	}

	@Override
	public Page<ContentSongs> getAllSongsByCategoryId(Integer categoryId, Pageable page) {
		logger.info( "Received request to get all songs under category id: {}", categoryId );
		return repository.getAllSongsByCategoryId( categoryId, page );
	}
	
	@Override
	public List<ContentSongs> getAllSongsByCategory(Integer categoryId) {
		logger.info( "Received request to get all songs under category id: {}", categoryId );
		return repository.getAllSongsByCategory( categoryId );
	}
	@Autowired
	TopContentRepository topContentRepository;
	public String getSongList() {
		JSONArray jsonMain = new JSONArray();
		try {
		logger.info("GetSong List Request from IVR");
		Sort sort = new Sort("order");
		List<Category> categoryList= categoryRepository.findAll();
		
		if(categoryList.size()>0){
			int index=1;
			for(int i=0;i < categoryList.size();i++){
				Category cat=categoryList.get(i);
				JSONObject jsonIndex=new JSONObject();  
				Integer categoryId=cat.getId();
				ArrayList<JSONObject> array = new ArrayList<>();
				//List<TopContent> topContentList =
				//session.createCriteria(TopContent.class).add(Restrictions.eq("categoryId", categoryId)).addOrder(Order.asc("order")).list();
				try {
					List<Object[]> topContentList = topContentRepository.findAllTopContents();
					//for(int j=0; j<topContentList.size(); j++){
					//	if(topContentList.get(j).getCategoryId() == cat.getId()){
					int autoID = 1;
					if(topContentList.size()>0){
						for(Object[] topcontent:topContentList){
							
							int catId = Integer.parseInt(topcontent[4].toString()+"");
							String songName =topcontent[2]+"";
							Integer songID = Integer.parseInt(topcontent[1].toString()+"");
							if (categoryId == catId)
							{
								JSONObject jsonObject2 = new JSONObject();
								jsonObject2.put("id_index", autoID);
								jsonObject2.put("song_name", songName);
								jsonObject2.put("song_id", songID);
								jsonObject2.put("category_id", catId);
								array.add(jsonObject2);
								autoID++;
							}
						}

					}
					jsonIndex.put(cat.getCategoryName(),array);
					jsonMain.put(jsonIndex);
					index++;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
		System.out.println("json : "+jsonMain.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return jsonMain.toString();
	}

	
}

	
	


