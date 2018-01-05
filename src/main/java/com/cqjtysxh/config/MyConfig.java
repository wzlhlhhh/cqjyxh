package com.cqjtysxh.config;

import java.util.Properties;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.cqjtysxh.model.*;
import com.cqjtysxh.routes.AdminPageRoutes;
import com.cqjtysxh.routes.FrontPageRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.cqjtysxh.config.*;
import com.cqjtysxh.function.sub_s;
import com.cqjtysxh.function.sub_s2;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class MyConfig extends JFinalConfig{
	
	@Override
	public void configConstant(Constants me) {
		// TODO 自动生成的方法存根
		me.setDevMode(true);
		me.setBaseUploadPath(PathKit.getWebRootPath()+"/upload");
//		me.setError500View(PathKit.getWebRootPath()+"/err500.html");
//		me.setError404View(PathKit.getWebRootPath()+"/other/err404.html");
		
	}

	@Override
	public void configRoute(Routes me) {
		// TODO 自动生成的方法存根
		me.add(new FrontPageRoutes());
		me.add(new AdminPageRoutes());
	}
	

	@Override
	public void configEngine(Engine me) {
		// TODO 自动生成的方法存根
		me.addSharedObject("b", new CONST());
		me.addDirective("subString", new sub_s());
		me.addDirective("sub_s", new sub_s2());
//		me.addDirective("subString", new SubString_s());
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO 自动生成的方法存根
			loadPropertyFile("db.properties");
			 /**配置druid数据连接池插�?**/
			 DruidPlugin dp=new DruidPlugin(getProperty("jdbcUrl")
					 ,getProperty("user"),getProperty("password").trim());
//			 System.out.println(getProperty("user"));
			 /**配置druid监控**/
			dp.addFilter(new StatFilter());
			WallFilter wall=new WallFilter();
			wall.setDbType("mysql");
			dp.addFilter(wall);
			me.add(dp);
			ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
			me.add(arp);
			arp.addMapping("users", Users.class);
			arp.addMapping("content", NewsEdit.class);

	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());//要用session,就得用这个拦截器
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO 自动生成的方法存根
		
	}

}
