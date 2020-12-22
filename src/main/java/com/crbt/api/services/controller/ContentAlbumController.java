package com.crbt.api.services.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.Views;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.domain.ContentAlbum;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.ContentAlbumService;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBean;
import com.crbt.api.services.validator.ContentAlbumValidator;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ContentAlbumController {

	private static final Logger logger = LoggerFactory.getLogger(ContentAlbumController.class);
	@Value("${file.Window_SOURCE_FOLDER}")
	private String Window_SOURCE_FOLDER;
	private final ContentAlbumService service;
	private final ContentAlbumValidator validator;
	

	
	@Inject
	public ContentAlbumController(ContentAlbumService service, ContentAlbumValidator validator) {
		super();
		this.service = service;
		this.validator = validator;

	}

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/album/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody final ContentAlbum contentAlbum, Errors errors) {
		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: ContentAlbum Update ##############");
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

		if (contentAlbum.getId() == null)
			logger.info("Received request to update the contentAlbum info with credentials {}", contentAlbum);
		else
			logger.info("Received request to create the contentAlbum info with credetails {}", contentAlbum);
		return new ResponseEntity<ContentAlbum>(service.update(contentAlbum), HttpStatus.OK);

		// return new ResponseEntity<ContentSongs>(service.update(contentSongs),
		// HttpStatus.OK);
	}

	// Service performing save or update by providing valid user info id
	@RequestMapping(value = "/album/create", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> save(@RequestParam("contentAlbum") String album, @RequestParam("file") MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException {

		if ((file != null || file.isEmpty()) && (album == null || album.isEmpty())) {
			throw new ResourceNotFoundException(0, "All feild requried");
		} /*else if (!file.getOriginalFilename().endsWith(".png")) {
			throw new ResourceNotFoundException(0, "Allow only png or jpg file");
		}*/
		else {
			logger.info("Received request to create the albumSong info with credetails {}", album);
			ContentAlbum bean = new ObjectMapper().readValue(album, ContentAlbum.class);
			bean.setAlbumArt("/Libyana/image" + "/" + file.getOriginalFilename());
           file.transferTo(new File(Window_SOURCE_FOLDER + "/" + file.getOriginalFilename()));
			ContentAlbum afterSave = service.save(bean);
			if(afterSave != null ){
				//ContentAlbum contentAlbumSaved = service.save(bean);
				return new ResponseEntity<ContentAlbum>(afterSave, HttpStatus.OK);
			}
			else{

				ErrorMessage  contentAlbumError = new ErrorMessage();
				contentAlbumError.setErrorCode(HttpStatus.CONFLICT + "");
				contentAlbumError.setErrorField("album_title");
				contentAlbumError.setErrorDescription("User album Title already exist!");
				return new ResponseEntity<ErrorMessage>(contentAlbumError, HttpStatus.CONFLICT);
			}

		}

	}

	
	@RequestMapping(value = "/album/id/{id}", method = RequestMethod.GET)
	//@JsonView( Views.Internal.class )
	public ContentAlbum getcontentAlbumByID(@Valid @PathVariable final Integer id) {
		logger.info("Received request to list status master info details for category {}", id);

		ContentAlbum contentAlbum = service.getContentAlbumByid(id);

		if (contentAlbum == null) {
			logger.info("No records found with the contentAlbum id " + id);
			throw new ResourceNotFoundException(0, "No records found with the contentAlbum id " + id);
		}

		return contentAlbum;

	}

	/*
	 * service for get all content album list
	 */
	@RequestMapping(value = "/album/list", method = RequestMethod.GET )
	@JsonView(Views.ExtendedPublic.class)
	public ResponseEntity<?> getAllContentAlbumDetails(Pageable page,  @RequestHeader(value = "Accept-Language") String acceptLanguage, Locale locale) {
		Page<ContentAlbum> contentAlbumlist = service.listAllContentAlbum(page);
		logger.info("Result response: {}", contentAlbumlist);
		String langEn = "en";
		String langAr = "ar-LY";

		if (acceptLanguage.equals(langAr)) {
			List<ContentAlbum> test = new ArrayList();
			for (Iterator<ContentAlbum> iterator = contentAlbumlist.iterator(); iterator.hasNext();) {
				ContentAlbum cat = (ContentAlbum) iterator.next();
				test.add( new ContentAlbum(cat.getId(), 
							cat.getAlbumArtAr(),
							cat.getAlbumDescriptionAr(),
							cat.getAlbumGenreAr(),
							cat.getAlbumArtistAr(),
							cat.getCategoryId(),
							cat.getAlbumArt(),
							cat.getAlbumTypeAr(),
							cat.getAlbumArtAr(),
	                        cat.getAlbumDescriptionAr(),
							cat.getAlbumGenreAr(),
							cat.getAlbumArtistAr(),
							cat.getAlbumCategoryAr(),
							cat.getAlbumArtAr(),
							cat.getAlbumTypeAr()
							));
			}
		
			Page<ContentAlbum> pageResponse = new PageImpl<>(test, page, test.size());
		
			return new ResponseEntity<Page<ContentAlbum>>(pageResponse, HttpStatus.OK);

		//return new ResponseEntity<Page<ContentAlbum>>(contentAlbumlist, HttpStatus.OK);

		}else if(acceptLanguage.equals(langEn)){
			return new ResponseEntity<Page<ContentAlbum>>(contentAlbumlist, HttpStatus.OK);
	
		}else{
			throw new ResourceNotFoundException(0, "Bad Request NO Language Found");
			
		}
	}
	
	
	// Service Not Requried
	@RequestMapping(value = "/album/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteContentAlbumById(@Valid @PathVariable Integer id) {
		logger.info("Received request to delete category group for the id {}", id);
		ContentAlbum contentAlbum = service.getContentAlbumById(id);
		if (contentAlbum == null) {
			throw new ResourceNotFoundException(0, "No content album group exist for the id "+id);
			
			
			//return new ResponseEntity<String>("No category group exist for the id " + id, HttpStatus.OK);
		} else {
			service.delteContentAlbumById(id);
			contentAlbum = service.getContentAlbumById(id);
			if (contentAlbum == null) {
				return new ResponseEntity(
						new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "content album id has been deleted successfuly!"),
						HttpStatus.OK);
			
				//return new ResponseEntity<String>("category group has been deleted successfuly!", HttpStatus.OK);
			} else {
				return new ResponseEntity(
						new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "content album to delete album group!"),
						HttpStatus.OK);
			
				//return new ResponseEntity<String>("category to delete album group!", HttpStatus.OK);
			}
		}

	}
	
	/*
	 * service for get all album by category Id
	*/
	@RequestMapping(value = "/album/albumCategoryId/{album_category}", method = RequestMethod.GET)
	@JsonView(Views.ExtendedPublic.class)
	public ResponseEntity<?> getAllSongByUsername(@PathVariable @Valid final Integer album_category , Pageable page, @RequestHeader(value = "Accept-Language") String acceptLanguage, Locale locale) {

		logger.info("Received request to list all the user under group albumCategory {}", album_category);
		String langEn = "en";
		String langAr = "ar-LY";

		if (acceptLanguage.equals(langAr)) {
		
		Page<ContentAlbum> contentAlbum = service.getAlbumByAlbumCategoryId(album_category, page);
		/*if (contentAlbum == null) {
			logger.info("غير معثور عليه: " + "لم يتم العثور على معلوماتالمستخدم أسفل مجموعةالمستخدمين " + album_category);
			throw new ResourceNotFoundException(0, "لم يتم العثور على معلومات المستخدم أسفل مجموعة المستخدمين" + album_category);

		}*/
		/*Integer id, String albumTitle, String albumDescription, String albumGenre, String albumArtist,
		Category categoryId, String albumArt, String albumType, String albumTitleAr, String albumDescriptionAr,
		String albumGenreAr, String albumArtistAr, String albumCategoryAr, String albumArtAr, String albumTypeAr*/
		List<ContentAlbum> test = new ArrayList();
		for (Iterator<ContentAlbum> iterator = contentAlbum.iterator(); iterator.hasNext();) {
			ContentAlbum cat = (ContentAlbum) iterator.next();
			test.add( new ContentAlbum(cat.getId(), 
						cat.getAlbumArtAr(),
						cat.getAlbumDescriptionAr(),
						cat.getAlbumGenreAr(),
						cat.getAlbumArtistAr(),
						cat.getCategoryId(),
						cat.getAlbumArt(),
						cat.getAlbumTypeAr(),
						cat.getAlbumArtAr(),
                        cat.getAlbumDescriptionAr(),
						cat.getAlbumGenreAr(),
						cat.getAlbumArtistAr(),
						cat.getAlbumCategoryAr(),
						cat.getAlbumArtAr(),
						cat.getAlbumTypeAr()
						));
		}
			
		Page<ContentAlbum> pageResponse = new PageImpl<>(test, page, test.size());
	
		return new ResponseEntity<Page<ContentAlbum>>(pageResponse, HttpStatus.OK);

	}else if(acceptLanguage.equals(langEn)){
		Page<ContentAlbum> contentAlbum = service.getAlbumByAlbumCategoryId(album_category, page);
		/*if (contentAlbum == null) {
			logger.info("NOT FOUND: " + "No user information found under the user group " + album_category);
			throw new ResourceNotFoundException(0, "No user information found under the user group " + album_category);

		}*/
		return new ResponseEntity<Page<ContentAlbum>>(contentAlbum, HttpStatus.OK);

	}else{
		throw new ResourceNotFoundException(0, "Bad Request NO Language Found");
	}
		
}
	
	
	/*
	 * Search album by name
	 */ @RequestMapping(value = "/albumName", method = RequestMethod.POST)
	public List<ContentAlbum> getAlbumByName(@Valid @RequestParam("album_title") String album_title ,@RequestParam("album_title_ar") String album_title_ar) {
		logger.info("Received request to list  info details for CategoryName {}", album_title_ar +""+album_title_ar);
        String albumName=null; 
		if((album_title.isEmpty())){
			album_title = null;
         }else if((album_title_ar.isEmpty())){
        	 album_title_ar = null;
         }else{
        	 album_title=null;
             album_title_ar=null;
         }
		   List<ContentAlbum> contentAlbums = service.getCategoryByName(album_title, album_title_ar);

 		if (contentAlbums == null) {
 			logger.info("No records found with the albumName  " + album_title +""+album_title_ar);
 			throw new ResourceNotFoundException(0, "No records found with the albumName " + album_title+""+album_title_ar);
 		}
		return contentAlbums;

	}
}
