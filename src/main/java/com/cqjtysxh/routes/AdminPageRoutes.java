package com.cqjtysxh.routes;

import com.cqjtysxh.controller.AdminController;
import com.jfinal.config.Routes;

public class AdminPageRoutes extends Routes{

	@Override
	public void config() {
		setBaseViewPath("/AdminPage");
		add("/jyxh-admin", AdminController.class, "/");
	}

}
