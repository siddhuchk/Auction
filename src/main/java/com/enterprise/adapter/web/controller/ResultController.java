package com.enterprise.adapter.web.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enterprise.adapter.domain.ProductBids;
import com.enterprise.adapter.domain.Products;
import com.enterprise.adapter.service.ProductBidTableService;
import com.enterprise.adapter.service.ProductTableService;
import com.enterprise.adapter.web.controller.contants.ControllerURL;
import com.enterprise.adapter.web.dto.response.AllResult;
import com.enterprise.adapter.web.dto.response.AllResultResponse;
import com.enterprise.adapter.web.dto.response.ResponseDTO;
import com.enterprise.adapter.web.dto.response.ResponseHeaderDto;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Controller
@RequestMapping(value = ControllerURL.RESULT)
public class ResultController {
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@Autowired
	private ProductBidTableService productBidTableService;
	@Autowired
	private ProductTableService productTableService;

	@RequestMapping(value = ControllerURL.GET_RESULT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllBidResult(HttpServletRequest servletRequest) throws Exception {
		logger.info("get all product request");
		ResponseDTO<AllResultResponse> response = new ResponseDTO<AllResultResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		AllResultResponse allResultResponse = new AllResultResponse();

		List<ProductBids> productBids = productBidTableService.findExpiredBides(LocalDateTime.now());

		List<AllResult> reqults = new ArrayList<AllResult>();
		for (ProductBids bids : productBids) {
			AllResult result = new AllResult();
			Products products = productTableService.findById(bids.getProductId());
			result.setProductName(products.getName());
			result.setStartTime(bids.getBidStartTime());
			result.setEndTime(bids.getBidStopTime());
			reqults.add(result);
		}
		allResultResponse.setExpiredBids(reqults);

		response.setHeaders(header);
		response.setBody(allResultResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
