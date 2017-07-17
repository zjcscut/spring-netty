package org.throwable.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.throwable.client.support.CustomNettySender;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 23:25
 */
@RestController
public class NettyController {

	@Autowired
	private CustomNettySender customNettySender;

	@GetMapping("/send")
	public String send(){
		customNettySender.sendRequestMessage();
		return "success";
	}
}
