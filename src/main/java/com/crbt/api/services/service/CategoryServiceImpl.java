package com.crbt.api.services.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.bean.CategorySongsResponseBean;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.repository.CategoryRepository;

@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final CategoryRepository repository;

	@Inject
	public CategoryServiceImpl(CategoryRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		logger.info("Update {}", category);
		Category update = repository.getOne(category.getId());
		if (update != null) {
			update.setIsActive(category.getIsActive());
			// update.setEmail( subscriber.getEmail() );

			repository.save(update);
		}

		return update;

		/*
		 * Category saveUser=null; try{ if(saveUser!=null){
		 * 
		 * } saveUser= repository.saveAndFlush(category); }catch (Exception e) {
		 * // TODO: handle exception } return saveUser;
		 */}

	@Override
	public Category save(Category category) {
		logger.info("Received request to create new entry for category.");
		logger.info("Creating {}", category);
		Category saveUser = null;
		try {

			saveUser = repository.save(category);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saveUser;
	}

	@Override
	public void delteCategoryById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Received request to delete category for id {}", id);
		try {
			repository.delete(id);
		} catch (Exception e) {

		}

	}

	@Override
	public Category getCategoryIDByid(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Category getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<Category> listAllCategory(Pageable page) {
		// TODO Auto-generated method stub
		return repository.findAllCategory(page);
		//return repository.findAll(page);
		// return repository.findAllByPriorityOrder(page);
	}

	@Override
	public List<Category> getCategoryByName(String category_name, String category_name_ar) {
		return repository.findCategoryByName(category_name, category_name_ar);

	}

	@Override
	public List<Category> listAllCategoryByPriority() {
		// TODO Auto-generated method stub
		return repository.findAllByPriorityOrder();
	}

	@Override
	public List<Object> getCategoryOrderByPriority() {
		
		List<Category> categories = repository.findAllByPriorityOrder();
		  List<CategorySongsResponseBean > data = repository.findAllTop20Songs();
		  List<Object> response = new ArrayList<>();
		    
		  Map<Object,Object> responseData = new LinkedHashMap<Object,Object>();  
		  
		  for( Category cat: categories ) {
		   Map<String,List<CategorySongsResponseBean>> catMap = new LinkedHashMap<String,List<CategorySongsResponseBean>>();
		   List<CategorySongsResponseBean> temp = new ArrayList<>();
		   int internalIdx = 0;
		   for( CategorySongsResponseBean cs: data ) {    
		    if( cat.getCategoryName().equalsIgnoreCase(cs.getCategory_name()) ) {
		    internalIdx++;
		    CategorySongsResponseBean bean = cs;
		    //bean.setSong_name( cs.getSong_name().replace("/Libyana/songs/", "") );
		    bean.setSong_name( cs.getSong_name().substring(cs.getSong_name().lastIndexOf("/")+1, cs.getSong_name().length() ));
		    bean.setContentPathLocation( cs.getContentPathLocation() );
		    bean.setId_index(internalIdx); 
		    temp.add(bean);    
		    }    
		   }
		   catMap.put(cat.getCategoryName(), temp);
		   response.add(catMap);
		  } 
		  
		  return response;
		  
		 }
}
