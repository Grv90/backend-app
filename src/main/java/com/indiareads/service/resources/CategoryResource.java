
package com.indiareads.service.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indiareads.service.domain.CategoryEntity;
import com.indiareads.service.repo.CategoryRepository;

/*
 *  @version     1.0, 27-Jan-2017
 *  @author gaurav
*/
@RestController
@RequestMapping(value = "/v1/categories")
public class CategoryResource {
    private static final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Autowired
    private CategoryRepository  categoryRespository;

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return new ResponseEntity<>((List<CategoryEntity>) categoryRespository.findAll(), HttpStatus.OK);
    }
    

}
