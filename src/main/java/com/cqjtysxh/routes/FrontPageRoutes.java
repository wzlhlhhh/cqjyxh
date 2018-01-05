package com.cqjtysxh.routes;

import com.cqjtysxh.controller.FrontController;
import com.jfinal.config.Routes;

public class FrontPageRoutes extends Routes{

	@Override
	public void config() {
		setBaseViewPath("/FrontPage");
		add("/", FrontController.class, "/");
	}

}
