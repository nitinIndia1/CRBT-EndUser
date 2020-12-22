package com.crbt.api.services.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.Views;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.CategoryService;
import com.crbt.api.services.service.ContentAlbumService;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBean;
import com.crbt.api.services.validator.CategoryValidator;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class CategoryController {
	/*
	 * @Value("${locale}") private String locale;
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	private final CategoryService service;
	private final CategoryValidator validator;
	private final ContentSongsService contentSongsServiceservice;
	private final ContentAlbumService contentAlbumService;
	

	@Inject
	public CategoryController(CategoryService service, CategoryValidator validator,
			ContentSongsService contentSongsServiceservice, ContentAlbumService contentAlbumService) {
		super();
		this.service = service;
		this.validator = validator;
		this.contentSongsServiceservice = contentSongsServiceservice;
		this.contentAlbumService = contentAlbumService;
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String Home(Locale locale) {
		return locale.getDisplayCountry();
	}

	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody final Category category, Errors errors) {
		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: Category Update ##############");
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

		if (category.getId() != null)
			logger.info("Received request to update the contentSongs info with credentials {}", category);
		else
			logger.info("Received request to create the contentSongs info with credetails {}", category);
		Category categorySaved = service.update(category);
		if (categorySaved != null) {

			return new ResponseEntity<Category>(categorySaved, HttpStatus.OK);
		} else {
			
			ErrorMessage categoryError = new ErrorMessage();
			categoryError.setErrorCode(HttpStatus.CONFLICT + "");
			categoryError.setErrorField("categoryName");
			categoryError.setErrorDescription("User Category Name already exist!");
			return new ResponseEntity<ErrorMessage>(categoryError, HttpStatus.CONFLICT);
			
		}

		/*
		 * if (category.getId() == null) logger.
		 * info("Received request to update the contentSongs info with credentials {}"
		 * , category); else logger.
		 * info("Received request to create the contentSongs info with credetails {}"
		 * , category); return new
		 * ResponseEntity<Category>(service.update(category), HttpStatus.OK);
		 */
		// return new ResponseEntity<ContentSongs>(service.update(contentSongs),
		// HttpStatus.OK);
	}

	// Service performing save or update by providing valid user info id
	@RequestMapping(value = "/category/create", method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody final Category category, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: Category Controller ##############");
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

		if (category.getId() != null)
			logger.info("Received request to update the contentSongs info with credentials {}", category);
		else
			logger.info("Received request to create the contentSongs info with credetails {}", category);
		Category categorySaved = service.save(category);
		if (categorySaved != null) {

			return new ResponseEntity<Category>(categorySaved, HttpStatus.OK);
		} else {
			ErrorMessage categoryError = new ErrorMessage();
			categoryError.setErrorCode(HttpStatus.CONFLICT + "");
			categoryError.setErrorField("categoryName");
			categoryError.setErrorDescription("User Category Name already exist!");
			return new ResponseEntity<ErrorMessage>(categoryError, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "/category/id/{id}", method = RequestMethod.GET)
	@JsonView(Views.ExtendedPublic.class)

	public Category getCategoryByID(@Valid @PathVariable final Integer id,
			@RequestHeader(value = "Accept-Language") String acceptLanguage, Locale locale) {
		logger.info("Received request to list status master info details for category {}", id);
		String langEn = "en";
		String langAr = "ar-LY";

		if (acceptLanguage.equals(langAr)) {
			Category category = service.getCategoryIDByid(id);
			if (category == null) {
				logger.info("لم يتم العثور على سجلات بمعرف statusMaster" + id);
				throw new ResourceNotFoundException(0, "لم يتم العثور على سجلات بمعرف الفئة" + id);
			}
			category.setCategoryName(category.getCategoryNameAr());

			return category;
		} else if (acceptLanguage.equals(langEn)) {
			Category category = service.getCategoryIDByid(id);
			if (category == null) {
				logger.info("No records found with the statusMaster id " + id);
				throw new ResourceNotFoundException(0, "No records found with the category id " + id);
			}
			return category;
		} else {
			throw new ResourceNotFoundException(0, "Bad Request NO Language Found");
		}

	}

	/*
	 * service for get all category list
	 */
	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	@JsonView(Views.ExtendedPublic.class)
	public ResponseEntity<?> getAllCategoryDetails(Pageable page,
			@RequestHeader(value = "Accept-Language") String acceptLanguage, Locale locale) {
		String langEn = "en";
		String langAr = "ar-LY";

		Page<Category> categorylist = service.listAllCategory(page);
		logger.info("Result response: {}", categorylist);
		if (acceptLanguage.equals(langAr)) {
			//Page<Category> arList =
			List<Category> test = new ArrayList();
			for (Iterator<Category> iterator = categorylist.iterator(); iterator.hasNext();) {
				Category cat = (Category) iterator.next();
				test.add(new Category(cat.getId(), cat.getCategoryNameAr(),cat.getIsActive(), cat.getPriority(), cat.getCategoryNameAr()));
			}
			
			Page<Category> pageResponse = new PageImpl<>(test, page, test.size());
			return new ResponseEntity<Page<Category>>(pageResponse, HttpStatus.OK);
		} else if (acceptLanguage.equals(langEn)) {

			return new ResponseEntity<Page<Category>>(categorylist, HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(0, "Bad Request NO Language Found");

		}
	}

	
	
	/*
	 * service for get all category list based on priority
	 */
	@RequestMapping(value = "/categoryPriority/list", method = RequestMethod.GET)
	@JsonView(Views.ExtendedPublic.class)
	public ResponseEntity<?> getAllCategoryBasedOnPriority(
			@RequestHeader(value = "Accept-Language") String acceptLanguage, Locale locale) {
		String langEn = "en";
		String langAr = "ar-LY";

		List<Category> categorylist = service.listAllCategoryByPriority();
		logger.info("Result response: {}", categorylist);
		if (acceptLanguage.equals(langAr)) {
			//Page<Category> arList =
			List<Category> test = new ArrayList();
			for (Iterator<Category> iterator = categorylist.iterator(); iterator.hasNext();) {
				Category cat = (Category) iterator.next();
				test.add(new Category(cat.getId(), cat.getCategoryNameAr(),cat.getIsActive(), cat.getPriority(), cat.getCategoryNameAr()));
			}
			
			List<Category> pageResponse = new ArrayList<>();
			return new ResponseEntity<List<Category>>(test, HttpStatus.OK);
		} else if (acceptLanguage.equals(langEn)) {

			return new ResponseEntity<List<Category>>(categorylist, HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(0, "Bad Request NO Language Found");

		}
	}
	// Service for deleting radius group by id
	@RequestMapping(value = "/category/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CoreResponseHandler> deleteCategoryById(@Valid @PathVariable Integer id) {
		logger.info("Received request to delete category group for the id {}", id);
		Category category = service.getCategoryById(id);
		if (category == null) {
			throw new ResourceNotFoundException(0, "No category group exist for the id " + id);
			// return new ResponseEntity<String>("No category group exist for
			// the id " + id, HttpStatus.OK);
		} else {
			service.delteCategoryById(id);
			category = service.getCategoryById(id);
			if (category == null) {
				return new ResponseEntity(new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL,
						"category group has been deleted successfuly!"), HttpStatus.OK);
				// return new ResponseEntity<>("category group has been deleted
				// successfuly!", HttpStatus.OK);
			} else {
				return new ResponseEntity(new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL,
						"category to delete category group!"), HttpStatus.OK);

				// return new ResponseEntity<String>("category to delete
				// category group!", HttpStatus.OK);
			}
		}

	}

	/*
	 * Search category by name
	 */ @RequestMapping(value = "/categoryName", method = RequestMethod.POST)
	public List<Category> getCategoryByName(@Valid @RequestParam("category_name") String category_name,
			@RequestParam("category_name_ar") String category_name_ar) {
		logger.info("Received request to list  info details for CategoryName {}",
				category_name + "" + category_name_ar);
		String categoryName = null;
		if ((category_name.isEmpty())) {
			category_name = null;
		} else if ((category_name_ar.isEmpty())) {
			category_name_ar = null;
		} else {
			category_name = null;
			category_name_ar = null;
		}

		System.out.println(category_name);
		System.out.println(category_name_ar);
		List<Category> category = service.getCategoryByName(category_name, category_name_ar);

		if (category == null) {
			logger.info("No records found with the categoryName  " + category_name);
			throw new ResourceNotFoundException(0,
					"No records found with the categoryName " + category_name + "" + category_name_ar);
		}
		return category;

	}
	 
	 @RequestMapping(value = "/category/List", method = RequestMethod.GET)
	 public List<Category> getCategoryBaseSongList( ) {
		 logger.info("Received request to list all the category {}" );
		 return service.listAllCategoryByPriority();
	 }
	 
	 /*
       Service for request category priority

	  */
	 @RequestMapping(value = "/priorityBaseSongs/List", method = RequestMethod.GET)
		public  List<Object> getCategoryDetailsOrderByPriority( ) {

			logger.info("Received request to list all the user under group category {}" );
			
			return service.getCategoryOrderByPriority();

	 
	/* @RequestMapping(value = "/commonSearch", method = RequestMethod.POST)
//		@JsonView(Views.PageData.class)
		public ResponseEntity<?> getCommonValueByName(@RequestParam("identifier") String identifier,
				@RequestParam("contentValue") String contentValue, @RequestParam("contentValue_ar")  String contentValue_ar) {
	         logger.info("Data request parameter"+identifier+"++"+contentValue+"++"+contentValue_ar);
			//return identifier+contentValue+contentValue_ar;
	         
		    if (identifier != null && !identifier.isEmpty()) {
				if (identifier.equalsIgnoreCase("category")) {
					String category_name = contentValue;
					String category_name_ar = contentValue_ar;
					if ((category_name.isEmpty())) {
						category_name = null;
					} else if ((category_name_ar.isEmpty())) {
						category_name_ar = null;
					} else {
						category_name = null;
						category_name_ar = null;
					}
					List<Category> category = service.getCategoryByName(category_name, category_name_ar);

					if (category == null) {
						logger.info("No records found with the categoryName  " + category_name);
						throw new ResourceNotFoundException(0,
								"No records found with the categoryName " + category_name + "" + category_name_ar);
					}
					return new ResponseEntity<List<Category>>(category, HttpStatus.OK);

				}

				// return new ResponseEntity<Category>(
				// this.categoryService.getCategoryByName(category_name,
				// category_name_ar) )
		    } else if (identifier.equalsIgnoreCase("album")) {
				String album_title = contentValue;
				String album_title_ar = contentValue_ar;
				if ((album_title.isEmpty())) {
					album_title = null;
				} else if ((album_title_ar.isEmpty())) {
					album_title_ar = null;
				} else {
					album_title = null;
					album_title_ar = null;
				}
				logger.info("request Data"+contentValue+"++"+contentValue_ar);
				List<ContentAlbum> contentAlbums = contentAlbumService.getCategoryByName(album_title, album_title_ar);
				logger.info("Response Data"+contentAlbums);
				if (contentAlbums == null) {
					logger.info("No records found with the albumName  " + album_title + "" + album_title_ar);
					throw new ResourceNotFoundException(0,
							"No records found with the albumName " + album_title + "" + album_title_ar);
				}
				return new ResponseEntity<List<ContentAlbum>>(contentAlbums, HttpStatus.OK);

			} else if (identifier.equalsIgnoreCase("song")) {
				String songs_name = contentValue;
				if (contentValue != null && contentValue_ar.isEmpty()) {
					logger.info("Request SongsName" + songs_name);
					List<ContentSongs> contentSongs = contentSongsServiceservice.getSongsByName(songs_name);
					logger.info("Response SongsData" + contentSongs);
					// return new ResponseEntity<ContentSongs>(
					// this.contentSongsServiceservice.getSongsByName(songs_name));
					return new ResponseEntity<List<ContentSongs>>(contentSongs, HttpStatus.OK);

				} else {
					throw new ResourceNotFoundException(0, "No Record found of this SongsName");
				}
		}
			else {
				throw new ResourceNotFoundException(0, "No identifier mapping found!");
			}
			return new ResponseEntity<>("NO identifer", HttpStatus.OK);

		}
*/
	 }
}