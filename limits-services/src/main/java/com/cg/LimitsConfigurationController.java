package com.cg;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "LimitConfiguration demo using logger and swagger")
public class LimitsConfigurationController {
	
	private List<String> items = new ArrayList<String>();

	private static final Logger logger = LoggerFactory.getLogger(LimitsConfigurationController.class);

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	@ApiOperation(value = "retrieveLimitsFromConfigurations", nickname = "retrieveLimitsFromConfigurations")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = LimitConfiguration.class),
			@ApiResponse(code = 500, message = "Failure", response = LimitConfiguration.class) })
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		// return new LimitConfiguration(5, 3);
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}

	@GetMapping(path = "/limits-fault-tolerant")
	@HystrixCommand(fallbackMethod = "alternateMethod")
	@ApiOperation(value = "retrieveConfigurations", nickname = "retrieveConfigurations")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = LimitConfiguration.class),
			@ApiResponse(code = 500, message = "Failure", response = LimitConfiguration.class) })
	// Method signature of alternateMethod() should be exactly same as this method
	public LimitConfiguration retrieveConfigurations() {
		throw new RuntimeException("Not Available");
	}

	public LimitConfiguration alternateMethod() {
		System.out.println("inside alternateMethod");
		logger.info("Due to Exception, the fallbackmethod is called by Hystrix");
		return new LimitConfiguration(510, 0);
	}

	@GetMapping(path = "/limits/{max}/{min}")
	@ApiOperation(value = "retrieveLimitsFromParameters", nickname = "retrieveLimitsFromParameters")
	public LimitConfiguration retrieveLimitsFromParameters(@PathVariable("max") int max, @PathVariable("min") int min) {
		return new LimitConfiguration(max, min);
	}

	@GetMapping(path = "/limits-oauth2")
	public String restricted() {
		return "this is the restricted string but you are authorized to get it";
	}

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal prince) {
		return prince;
	}
	
	@RequestMapping(value = "/limits-session",method = RequestMethod.GET)
    public List<String> doSessionManagement(@RequestParam String item, HttpServletRequest req) {
        System.out.println("inside doSessionManagement and going to add item in the session");
        List<String> items = null;
        HttpSession session = req.getSession(); //enable the session.
        //HttpSession session = null;
        if(session != null ) {
            Object obj = session.getAttribute("items");
            if(obj != null && obj instanceof List<?>) {
                items = (List<String>)session.getAttribute("items");
                System.out.println("The items added in the session " + items);
                items.add(item);
                session.setAttribute("items", items);
            }else {
                System.out.println("The session is created for the first time");
                items = new ArrayList<String>();
                items.add(item);   
                session.setAttribute("items", items);
            }
           
        }else {
            System.out.println("session is null");
            this.items.add(item);
            items = this.items;
        }
        System.out.println("returning the shopping cart: " + items);
        return items;
    }
}
