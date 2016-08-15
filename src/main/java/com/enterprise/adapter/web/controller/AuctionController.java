package com.enterprise.adapter.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enterprise.adapter.domain.Products;
import com.enterprise.adapter.domain.Users;
import com.enterprise.adapter.service.ProductTableService;
import com.enterprise.adapter.service.UserTableService;
import com.enterprise.adapter.utilities.RequestValidator;
import com.enterprise.adapter.web.controller.contants.ControllerURL;
import com.enterprise.adapter.web.dto.request.CreateProductBidRequest;
import com.enterprise.adapter.web.dto.request.CreateProductRequest;
import com.enterprise.adapter.web.dto.request.GetProductBidsByProductRequest;
import com.enterprise.adapter.web.dto.response.CreateProductBidResponse;
import com.enterprise.adapter.web.dto.response.CreateProductResponse;
import com.enterprise.adapter.web.dto.response.GetAllProductsResponse;
import com.enterprise.adapter.web.dto.response.GetProductBidsByProductResponse;
import com.enterprise.adapter.web.dto.response.ResponseDTO;
import com.enterprise.adapter.web.dto.response.ResponseHeaderDto;
import com.enterprise.adapter.webservices.utilities.ApplicationResponseCodes;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Controller
@RequestMapping(value = ControllerURL.DEFAULT_AUCTION_URL)
public class AuctionController {
	private static final Logger logger = LoggerFactory
			.getLogger(AuctionController.class);

	@Autowired
	private UserTableService userTableService;
	@Autowired
	private RequestValidator requestValidator;
	@Autowired
	private ProductTableService productTableService;

	@RequestMapping(value = ControllerURL.CREATE_PRODUCT_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(
			@RequestBody CreateProductRequest request,
			@RequestHeader String email, HttpServletRequest servletRequest)
			throws Exception {
		logger.info("Request Object:\n" + request);
		ResponseDTO<CreateProductResponse> response = new ResponseDTO<CreateProductResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		CreateProductResponse createProductResponse = new CreateProductResponse();

		Users users = requestValidator.validateUsers(email, servletRequest);
		long id = users.getId();

		Products products = new Products();
		products.setName(request.getName());
		products.setOwnerId(id);
		products.setImgURL("");
		products.setSold(false);
		Products p = productTableService.addNewRow(products);
		createProductResponse.setProduct(p);
		logger.info("new product added: " + p);

		header.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
		response.setHeaders(header);
		response.setBody(createProductResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.GET_ALL_PRODUCT_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllProduct(HttpServletRequest servletRequest)
			throws Exception {
		logger.info("get all product request");
		ResponseDTO<GetAllProductsResponse> response = new ResponseDTO<GetAllProductsResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		GetAllProductsResponse allProductsResponse = new GetAllProductsResponse();

		response.setHeaders(header);
		response.setBody(allProductsResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.CREATE_BID_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createBid(
			@RequestBody CreateProductBidRequest request,
			HttpServletRequest servletRequest) throws Exception {
		logger.info("Request Object:\n" + request);
		ResponseDTO<CreateProductBidResponse> response = new ResponseDTO<CreateProductBidResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		CreateProductBidResponse productBidResponse = new CreateProductBidResponse();

		response.setHeaders(header);
		response.setBody(productBidResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.GET_BID_BY_PRODUCT_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBidByProduct(
			@RequestBody GetProductBidsByProductRequest request,
			HttpServletRequest servletRequest) throws Exception {
		logger.info("Request Object:\n" + request);
		ResponseDTO<GetProductBidsByProductResponse> response = new ResponseDTO<GetProductBidsByProductResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		GetProductBidsByProductResponse productBidResponse = new GetProductBidsByProductResponse();

		response.setHeaders(header);
		response.setBody(productBidResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.GET_WINNER_BY_PRODUCT_BID_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBidWinnerByProduct(
			@RequestBody GetProductBidsByProductRequest request,
			HttpServletRequest servletRequest) throws Exception {
		logger.info("Request Object:\n" + request);
		ResponseDTO<GetProductBidsByProductResponse> response = new ResponseDTO<GetProductBidsByProductResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		GetProductBidsByProductResponse productBidResponse = new GetProductBidsByProductResponse();

		response.setHeaders(header);
		response.setBody(productBidResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
