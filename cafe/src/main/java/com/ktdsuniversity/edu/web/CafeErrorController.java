package com.ktdsuniversity.edu.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeErrorController implements ErrorController {
	
	/**
	 * Http Status 404(Page Not Found) Error Custom
	 * 없는 URL을 요청하면, 아래 메소드가 실행된다.
	 * @return
	 */
	@GetMapping("/error")
	public String viewErrorPage() {
		return "error/404";
	}
	
	

}
