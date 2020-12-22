package com.crbt.api.services.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crbt.api.services.bean.ChangeAlbumStatusBean;
import com.crbt.api.services.bean.ContentSongsBean;
import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.PreviewSongsBean;
import com.crbt.api.services.bean.SongStatusBean;
import com.crbt.api.services.bean.SongsAttributeBean;
import com.crbt.api.services.bean.Views;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.domain.ContentAlbum;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.UploadType;
import com.crbt.api.services.domain.User;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.CategoryService;
import com.crbt.api.services.service.ContentAlbumService;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.UploadTypeService;
import com.crbt.api.services.service.UserService;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBean;
import com.crbt.api.services.validator.ContentSongsValidator;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ContentSongsController {

	private static final Logger logger = LoggerFactory.getLogger(ContentSongsController.class);
	@Value("${file.Window_SOURCE_FOLDER}")
	private String Window_SOURCE_FOLDER;
	private final ContentSongsService service;
	private final SubscriberService subscriberService;
	private final ContentAlbumService albumService;
	private final CategoryService categoryService;
	private final UserService userService;
	private final UploadTypeService uploadTypeService;
	private final ContentSongsValidator validator;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	
	@Inject
	public ContentSongsController(ContentSongsService service, 
			SubscriberService subscriberService,
			ContentAlbumService albumService,
			CategoryService categoryService,
			UserService userService,
			UploadTypeService uploadTypeService,
			ContentSongsValidator validator) {
		super();
		this.service = service;
		this.subscriberService = subscriberService;
		this.albumService = albumService;
		this.categoryService = categoryService;
		this.userService = userService;
		this.uploadTypeService = uploadTypeService;
		this.validator = validator;

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/songs/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody final ContentSongs contentSongs, Errors errors) {
		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: ContentSongs Update ##############");
			List<FieldError> fielderrors = errors.getFieldErrors();
			List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

			for (Iterator<FieldError> iterator = fielderrors.iterator(); iterator.hasNext();) {
				FieldError ferrors = (FieldError) iterator.next();
				errorList.add(new ErrorMessage(ferrors.getField(),
					HttpStatus.BAD_REQUEST.toString(), ferrors.getCode()));
			}

			logger.error(errorList.toString());

			return new ResponseEntity<List<ErrorMessage>>(errorList, HttpStatus.BAD_REQUEST);
		}

		if (contentSongs.getId() != null)
			logger.info("Received request to update the contentSongs info with credentials {}", contentSongs);
		else
			logger.info("Received request to create the contentSongs info with credetails {}", contentSongs);
		return new ResponseEntity<ContentSongs>(service.update(contentSongs), HttpStatus.OK);

	}

	// Service performing save or update by providing valid user info id
	@RequestMapping(value = "/songs/create", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> save(@RequestParam("contentSongs") String songs, @RequestPart(name = "file", required = false) MultipartFile file) {
	//	System.out.println("file"+file); //throws JsonParseException, JsonMappingException, IOException
		logger.info("128-------"+songs +"request -------"+file);
		if( songs != null && !songs.equals("")) {
			SongsAttributeBean bean = null;
			try{
				bean = new ObjectMapper().readValue(songs, SongsAttributeBean.class);
				if( bean.getAlbumId() != null && bean.getAlbumId() != 0  ){ //consider it as admin
					ContentAlbum existingAlbum = albumService.getContentAlbumByid(bean.getAlbumId());
							logger.info("135-------"+existingAlbum);				
						if( existingAlbum != null ){ //consider it as admin
							if( bean.getUploadType() == 2 ){ //for rbt
								ContentSongs checkSong = service.getUploadTypeByAlbumId(existingAlbum.getId(), 2);
								if( file != null ){
									file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
									String fileName = file.getOriginalFilename();
									checkSong.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
								}
								checkSong.setSongStatus("Active");
								User user= userService.getUserById(1);
								checkSong.setUserId(user);
								checkSong.setApproverId(user);
								logger.info("145-----"+checkSong);
								service.save(checkSong);
							}
							if( bean.getUploadType() == 1 ){ //for nrbt
								ContentSongs checkSong = service.getUploadTypeByAlbumId(existingAlbum.getId(), 1);
								file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
								String fileName = file.getOriginalFilename();	                            
								checkSong.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
								checkSong.setSongStatus("Active");
								//checkSong.setNrbtSongText(bean.getSongsText());
								service.save(checkSong);						    						   
							}
						}						
						
					
				}else{ //consider it as user
					Subscriber subscriber = subscriberService.getChangePassword( bean.getMsisdn() );
					logger.info("162----------Msisdn-"+subscriber);//check if subscriber exist
					ContentAlbum newAlbum = albumService.getAlbumName( subscriber.getMsisdn() );
					
					if(newAlbum == null){ logger.info("165------");
						newAlbum = new ContentAlbum();
						newAlbum.setAlbumTitle(subscriber.getMsisdn());
						newAlbum.setAlbumTitleAr(subscriber.getMsisdn());
						newAlbum.setAlbumDescription(subscriber.getMsisdn());
						newAlbum.setAlbumGenre(subscriber.getMsisdn());
						newAlbum.setAlbumArtist("Custom");
					    Category category = categoryService.getCategoryById(10);
						newAlbum.setCategoryId(category);
						newAlbum.setAlbumArt("default");
						newAlbum.setAlbumType("1");
						logger.info("175--Saved---"+ albumService.save(newAlbum));
					}
						//albumService.save(newAlbum);
						
						if( bean.getUploadType() == 2 ){ //for rbt
							file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
							String fileName = file.getOriginalFilename();
                            ContentSongs newSong = new ContentSongs();
                            newSong.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
							newSong.setSongsName("RRBT-"+fileName.substring(0, fileName.indexOf(".")));
							UploadType uploadId= uploadTypeService.getDetailsByUploadId(bean.getUploadType());
							newSong.setUploadType(uploadId);
							User userId = userService.getUserById(1);
							newSong.setUserId(userId);
							newSong.setApproverId(userId);
							newSong.setSongStatus("In-Active");
							newSong.setContentAlbum(newAlbum);
							newSong.setSongId( service.getMaxSongId()+1 );
							logger.info("183--------"+service.save(newSong));
							
							//service.save(newSong);
						}
						if( bean.getUploadType() == 1 ){ //for nrbt
							logger.info("187-------"+bean.getUploadType());
							ContentSongs newSong = new ContentSongs();
							newSong.setSongId( service.getMaxSongId()+1 );
							if( file == null ){
								newSong.setContentPathLocation("");
								
							}
							newSong.setSongsName("NRBT-"+sdf.format(new Date()));
							User user= userService.getUserById(1);
							newSong.setUserId(user);
							newSong.setApproverId(user);
							newSong.setSongStatus("In-Active");
							UploadType uploadId= uploadTypeService.getDetailsByUploadId(bean.getUploadType());
							newSong.setUploadType(uploadId);
							newSong.setContentAlbum(newAlbum);
							newSong.setNrbtSongText(bean.getSongsText());
							ContentSongs checkSong = service.getUploadTypeByAlbumId(newAlbum.getId(), 1);
							if( checkSong != null ){
								checkSong.setSongsName( newSong.getSongsName() );
								checkSong.setSongStatus( newSong.getSongStatus() );
								checkSong.setContentPathLocation( newSong.getContentPathLocation() );
								//checkSong.setNrbtSongText(bean.getSongsUrl());
								service.save(checkSong);
							}else{
								service.save(newSong);
							}						    						   
						}
					//}
				}
				
				return new ResponseEntity(
						new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
						HttpStatus.OK);

//				ErrorMessage error = new ErrorMessage();
//				error.setErrorCode( "200" );
//				error.setErrorField( "Song upload" );
//				error.setErrorDescription( "Song data uploaded successfully." );
//				return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );
				
			}catch( Exception e ){
				e.printStackTrace();
				ErrorMessage error = new ErrorMessage();
				error.setErrorCode( "400" );
				error.setErrorField( "Song Details" );
				error.setErrorDescription( "Faild to process song data." );
				return new ResponseEntity<ErrorMessage>( error, HttpStatus.BAD_REQUEST );
			}
			 
			
		}else{
			ErrorMessage error = new ErrorMessage();
			error.setErrorCode( "400" );
			error.setErrorField( "Song Details" );
			error.setErrorDescription( "Bad data." );
			return new ResponseEntity<ErrorMessage>( error, HttpStatus.BAD_REQUEST );
		}
		

		/*if ((file == null || file.isEmpty()) && (songs == null || songs.isEmpty())) {
			throw new ResourceNotFoundException(0, "All feild requried");
		} else if (!file.getOriginalFilename().endsWith(".wav")) {
			throw new ResourceNotFoundException(0, "Allow only Wav file");
		}
		ContentSongs bean = new ObjectMapper().readValue(songs, ContentSongs.class);

		
		if ((bean.getUploadType().getId() == 1) && file.equals(null)) {
			bean.setSongStatus("In-Active");
			bean.setSongsName("Name_RBT"+new Date());
			
			bean.setContentPathLocation(null);
			//file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
			//String fileName = file.getOriginalFilename();
			ContentSongs contentSongSaved = service.save(bean);
			if (contentSongSaved != null) {
				// try {
				logger.info("----------fileName------" + file.getName());

				return new ResponseEntity<ContentSongs>(service.save(bean), HttpStatus.OK);
			} // catch (Exception e) {
			else {
				ErrorMessage ContentSongError = new ErrorMessage();
				ContentSongError.setErrorCode(HttpStatus.CONFLICT + "");
				ContentSongError.setErrorField("song_id");
				ContentSongError.setErrorDescription("User songId already exist!");
				return new ResponseEntity<ErrorMessage>(ContentSongError, HttpStatus.CONFLICT);
			}


		} else if (bean.getUploadType().getId() == 2) {
			bean.setSongStatus("In-Active");
			bean.setSongsName("Recorded");//Recored RBT
			bean.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
			file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
			String fileName = file.getOriginalFilename();
			bean.setSongsName(fileName.substring(0, fileName.indexOf(".")));

		} else {
			bean.setSongStatus("Active");
			bean.setSongsName("Online");
			bean.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());
			file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
			String fileName = file.getOriginalFilename();
			bean.setSongsName(fileName.substring(0, fileName.indexOf(".")));
		}

		ContentSongs contentSongSaved = service.save(bean);
		System.out.println("33455"+contentSongSaved);
		if (contentSongSaved != null) {
			// try {
			logger.info("----------fileName------" + file.getName());

			return new ResponseEntity<ContentSongs>(service.save(bean), HttpStatus.OK);
		} // catch (Exception e) {
		else {
			ErrorMessage ContentSongError = new ErrorMessage();
			ContentSongError.setErrorCode(HttpStatus.CONFLICT + "");
			ContentSongError.setErrorField("song_id");
			ContentSongError.setErrorDescription("User songId already exist!");
			return new ResponseEntity<ErrorMessage>(ContentSongError, HttpStatus.CONFLICT);
		}*/
		}

	

	// Service performing search info by id

	@RequestMapping(value = "/songs/id/{id}", method = RequestMethod.GET)
	public ContentSongs getSongsByUser_id(@Valid @PathVariable final Integer id) {
		logger.info("Received request to contentSong info details for id {}", id);

		ContentSongs contentSongs = service.getSongsByid(id);

		if (contentSongs == null) {
			logger.info("No records found with the contentSong id " + id);
			throw new ResourceNotFoundException(0, "No records found with the contentSong id " + id);
		}

		return contentSongs;

	}
   //Service to get all songs list
	@RequestMapping(value = "/songs/list", method = RequestMethod.GET  )
	public List<ContentSongs> getAllSongsDetails() {
		logger.info("Received request to ContentSongs list all songs info details.");
		return service.listAllSongs();
	}
	// Service performing search info by songId
	@RequestMapping(value = "/songs_id/{song_id}", method = RequestMethod.GET)
	@JsonView( Views.WithMapping.class )
	public ContentSongs getSongsBySongID(@Valid @PathVariable final Integer song_id) {
		logger.info("Received request to list status master info details for song_id {}", song_id);

		ContentSongs contentSongs = service.getSongsBySongId(song_id);

		if (contentSongs == null) {
			logger.info("No records found with the contentSong SongId " + song_id);
			throw new ResourceNotFoundException(0, "No records found with the contentSong SongId " + song_id);
		}

		return contentSongs;

	}

	// Service for Change Song Status By Song Id
	@RequestMapping(value = "/change/songId/status", method = RequestMethod.POST)
	//@JsonView(Views.WithMapping.class)
	public ResponseEntity<CoreResponseHandler> changeSong(@RequestBody final SongStatusBean pcb) {
		logger.info("Received request to list Subscriber info details for songId {} ,songStatus {}", pcb.getSong_id(),
				pcb.getSong_status());

		if (pcb.getSong_id() != null && pcb.getSong_status() != null) {
			ContentSongs contentSongs = service.getChangeSong(pcb.getSong_id());
			if (contentSongs == null) {
				// errors.rejectValue("msisdn", HttpStatus.BAD_REQUEST.name(),
				// "msisdn " + pcb.getMsisdn() + " is invalid ");
				logger.info("No records found with the SongID " + pcb.getSong_id());
				logger.info("Song Id or status feild incorect");
				throw new ResourceNotFoundException(0,
						"No records found with the songId " + pcb.getSong_id() + "/" + pcb.getSong_status());

			} else {
				contentSongs.setSongStatus(pcb.getSong_status());
				service.saveUpdateSongStatus(contentSongs);
			}
		} else {
			throw new ResourceNotFoundException(0, "songId & status can't be empty");
		}

		return new ResponseEntity(
				new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
				HttpStatus.OK);

	}

	// service for change song status with album id
	@RequestMapping(value = "/change/AlbumId/status", method = RequestMethod.POST)
	public ResponseEntity<CoreResponseHandler> changeSong(@RequestBody final ChangeAlbumStatusBean pcb) {
		logger.info("Received request to list changeSong info details for album id {} ,songStatus {}",
				pcb.getAlbum_id(), pcb.getSong_status());

		if (pcb.getAlbum_id() != null && pcb.getSong_status() != null) {
			List<ContentSongs> contentSongs = service.getChangeAlbum(pcb.getAlbum_id());
			if (contentSongs != null && contentSongs.size() > 0) {
				service.updateContentSongByAlbumId(pcb);
			} else {
				logger.info("No records found with the AlbumID " + pcb.getAlbum_id());
				logger.info("Album Id or status feild incorect");
				throw new ResourceNotFoundException(0,
						"No records found with the albumId " + pcb.getAlbum_id() + "/" + pcb.getSong_status());
			}
		} else {
			throw new ResourceNotFoundException(0, "albumId & status can't be empty");
		}

		return new ResponseEntity(
				new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
				HttpStatus.OK);

	}

	/*
	 * Service For View Song File
	 * 
	 */
	@RequestMapping(value = "/viewSong", method = RequestMethod.POST)
	public Object ViewSongs(@RequestBody PreviewSongsBean previewSongsBean, BindingResult result) {
		if (result.hasErrors()) {
			return "Data not exit";
		} else {
			ContentSongs contentSongs = service.getSongIdAndSongLocationBase(previewSongsBean.getSong_id());
			if (contentSongs == null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("404", "Data Not Found ");
				return map;
				// return "Data Not Found";
			} else {
				String viewSong = contentSongs.getContentPathLocation();

				return viewSong;
			}
		}

	}

	/*
	 * 
	 * Update Songs By id
	 * 
	 */

	@RequestMapping(value = "/updateSongs", method = RequestMethod.POST, consumes = { "multipart/form-data " })
	public ResponseEntity<?> UpdateSongsByID(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException {

		ContentSongs contentSongs = service.getSongsBySongId(id);

		if (contentSongs == null) {
			throw new ResourceNotFoundException(0, "No Resource Found of this Id" + id);
		} else {
			logger.info("Received request to update the contentSongs info with credetails {}", contentSongs);
			// ContentSongs bean = new ObjectMapper().readValue(contentSongs,
			// ContentSongs.class);
			contentSongs.setContentPathLocation("/Libyana/songs" + "/" + file.getOriginalFilename());

			file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
			try {
				ContentSongs contentSongSaved = service.save(contentSongs);
				return new ResponseEntity<ContentSongs>(service.save(contentSongs), HttpStatus.OK);

			} catch (Exception e) {
				// TODO: handle exception
				throw new ResourceNotFoundException(0, "Exception In Sending file");
			}

		}

	}

	/*
	 * 
	 * Update Songs By AlbumID
	 * 
	 */

	@RequestMapping(value = "/updateAllSongs", method = RequestMethod.POST, consumes = { "multipart/form-data " })
	public ResponseEntity<?> UpdateSongsByAlbumId(@RequestParam("album_id") Integer album_id,
			@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {

		List<ContentSongs> contentSongs = service.getChangeAlbum(album_id);

		if (contentSongs == null) {
			throw new ResourceNotFoundException(0, "No Resource Found of this Id" + album_id);
		} else {
			logger.info("Received request to update the contentSongs info with credetails {}", contentSongs);
			// List<ContentSongs> bean= new ArrayList<ContentSongs>();
			// ContentSongs mitem = new ContentSongs();
			// mitem.setContentPathLocation(Window_SOURCE_FOLDER + "/" +
			// file.getOriginalFilename());
			try {

				file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));

				return new ResponseEntity<List<ContentSongs>>(service.saveAllAlbumIdSongs(contentSongs, file),
						HttpStatus.OK);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new ResourceNotFoundException(0, "Exception In Sending file");
			}

		}

	}

	/*
	 * Service for get record by Album Id
	 */
	@RequestMapping(value = "/song/albumId/{album_id}/{language}", method = RequestMethod.GET)
	@JsonView( Views.PageData.class)
	public ResponseEntity<?> getUserDetailsByUsername(@PathVariable @Valid final Integer album_id , @PathVariable @Valid final String language, Pageable page) {

		logger.info("Received request to get all songs under album id {} for language {}", album_id, language);
		Page<ContentSongs> pagedSong = service.getAlbumByAlbumId(album_id ,page);
		List<ContentSongsBean> contentSongs = new ArrayList<>();
		for( ContentSongs cs : pagedSong ) {
			ContentSongsBean song = new ContentSongsBean();
			song.setId( cs.getId() );
			song.setSongId( cs.getSongId() );
			if( language.equalsIgnoreCase("en") ) {
				song.setSongsName( cs.getSongsName() );
				song.setArtistName( cs.getArtistName() );
			}else {
				song.setSongsName( cs.getSongsNameAr() );
				song.setArtistName( cs.getArtistNameAr() );
			}
			song.setContentAlbum( cs.getContentAlbum() );
			song.setUploadType( cs.getUploadType() );
			song.setUserId( cs.getUserId() );
			song.setSongStatus( cs.getSongStatus() );
			song.setApproverId( cs.getApproverId() );
			song.setContentPathLocation( cs.getContentPathLocation() );
			song.setContentPreview( cs.getContentPreview() );
			song.setNrbtSongText( cs.getNrbtSongText() );
			song.setCreatedDate( cs.getCreatedDate() );
			song.setApprovedate( cs.getApprovedate() );
			song.setUpdatedOn( cs.getUpdatedOn() );
			contentSongs.add(song);
			
		}
		Page<ContentSongsBean> pcs = new PageImpl<>(contentSongs);
		//Page<ContentSongs> contentSongs = service.getAlbumByAlbumId(album_id ,page);
		if (pagedSong == null) {
			logger.info("NOT FOUND: " + "No user information found under the user group " + album_id);
			throw new ResourceNotFoundException(0, "No user information found under the user group " + album_id);

		}
		return new ResponseEntity<Page<ContentSongsBean>>(pcs, HttpStatus.OK);

	}
	/*
	*Search Songs by name
	*/	
	@RequestMapping(value = "/songs/{songs_name}", method = RequestMethod.GET)
		public ResponseEntity<?> getSongs_By_Name(@Valid @PathVariable final String songs_name) {
			logger.info("Received request to list user master info details for ContentSongs {}", songs_name );
            if(songs_name!=null){
            	//String songs_name = searchSongBean.getSongs_name();
			List<ContentSongs> contentSongs = service.getSongs_By_Name(songs_name);

			if (contentSongs == null) {
				logger.info("No records found with the ContentSongs  " +songs_name);
				throw new ResourceNotFoundException(0, "No records found with the SongsName " + songs_name);
			}
			return new ResponseEntity<List<ContentSongs>>(contentSongs, HttpStatus.OK);			

		}else{
			throw new ResourceNotFoundException(0, "Please Enter Songs Name ,NO record found ");
		}
          
	}
	
	/*
	 * Get all songs by category id
	 */
	@RequestMapping( value = "/songs/categoryId/{catId}", method = RequestMethod.GET )
	public ResponseEntity<?> getAllSongsByCategoryId( @Valid @PathVariable final Integer catId, Pageable page ){
		logger.info( "Received request to get all songs under the category id: {}", catId );
		return new ResponseEntity<Page<ContentSongs>>( service.getAllSongsByCategoryId(catId, page), HttpStatus.OK );
	}
	
	@RequestMapping( value = "/songs/category/{catId}", method = RequestMethod.GET )
	public ResponseEntity<?> getAllSongsByCategory( @Valid @PathVariable final Integer catId ){
		logger.info( "Received request to get all songs under the category id: {}", catId );
		return new ResponseEntity<List<ContentSongs>>( service.getAllSongsByCategory(catId), HttpStatus.OK );
	}
	
	@RequestMapping(value = "/ivr/listSongs", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody JSONArray listSongs(HttpServletRequest request) {
		
		JSONArray object = new JSONArray();
		
		JSONParser parser = new JSONParser();
		try {
			String str = service.getSongList();
		object = (JSONArray)parser.parse(str);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return object;
	}
	
}
