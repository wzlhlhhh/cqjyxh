package com.cqjtysxh.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cqjtysxh.config.CONST;
import com.cqjtysxh.model.NewsEdit;
import com.jfinal.core.Controller;

public class FrontController extends Controller{
	public void index()
	{
		//取出数据
		NewsEdit ne = new NewsEdit();
		
		for(int i=0; i< CONST.unit.length; i++)
		{
			System.out.println(CONST.unit_e[i][0]);
			List<NewsEdit> news = ne.getNews( CONST.unit[i][0], 5);
			System.out.println(news);
			setAttr(CONST.unit_e[i][0], news);
		}
		
		render("index.html");
	}
	
	//内容页
	public void content()
	{
		int id = getParaToInt("id");
		System.out.println(id);
		
		NewsEdit ne = new NewsEdit();
		List<NewsEdit> news = ne.getNewsById(id);
		
		//修改一级标题
		String type = news.get(0).getStr("type");
		//定位查找
		int i=0,j=0, k=0;
		for(i=0; i<CONST.unit.length; i++)
			for(j=0; j<CONST.unit[i].length; j++)
			{
				System.out.println(type);
				if(type.equals(CONST.unit[i][j]))
				{
					news.get(0).set("type", CONST.unit_e[i][j]);
					k=i;
					break;
				}
			}
		setAttr("news", news);
		setAttr("new_type", type);
		type = news.get(0).getStr("subtype");
		String type_new = "";
		if( !type.equals("wu") )
		{
			//定位查找
			for(i=0; i<CONST.unit.length; i++)
				for(j=0; j<CONST.unit[i].length; j++)
				{
					System.out.println(type);
					if(type.equals(CONST.unit[i][j]))
					{
						type_new = CONST.unit_e[i][j];
						k=i;
						break;
					}
				}
			
			//二级标题
			setAttr("subtitle", "><a href=\"menu?type="+type_new+"\">"+type+"</a>>");
		}
		else
		{
			setAttr("subtitle", ">");
		}
		
		render("content.html");
	}
	
	//二级页面 新闻
	public void menu()
	{
		//类型
		String type =  getPara("type");
		String tp = type;
		System.out.println(type);
		
		//页码
		String queryString = getRequest().getQueryString();
		int s = queryString.indexOf("page");
		int page;
		if(s == -1)
		{
			page = 1;
		}
		else
		{
			page = getParaToInt("page");
		}
		
		
		
		//定位查找
		int i=0,j=0, k=0;
		System.out.println(CONST.unit_e.length);
		for(i=0; i<CONST.unit_e.length; i++)
			for(j=0; j<CONST.unit_e[i].length; j++)
			{
				if(type.equals(CONST.unit_e[i][j]))
				{
					System.out.println("i:"+i+" j:"+j);
					type = CONST.unit[i][j];
					k=i;
					break;
				}
			}
		
		//取出新闻
		NewsEdit ne = new NewsEdit();
		List<NewsEdit> news = ne.getNews( type, (page-1)*8 , 8);
		System.out.println(news);
		
		//取出子标题
		List<String> subtype = new ArrayList<String>();
		System.out.println(CONST.unit[k].length);
		for(j=1; j<CONST.unit[k].length; j++)
		{
			subtype.add(CONST.unit[k][j]);
		}
		
		List<String> subtype_abc = new ArrayList<String>();
		for(j=1; j<CONST.unit_e[k].length; j++)
		{
			System.out.println(type);
			subtype_abc.add(CONST.unit_e[k][j]);
		}
		
		setAttr("news", news);
		setAttr("type", CONST.unit[k][0]);
		setAttr("type_e", tp);
		setAttr("ontype", type);
		setAttr("subtype", subtype);
		setAttr("subtype_abc", subtype_abc);
		setAttr("subtype_num", subtype.size());
		System.out.println(subtype);
		
		
		
		if(tp.equals("xhjj") || tp.equals("xhzc") || tp.equals("xhld") || tp.equals("zzjg")
				|| tp.equals("zywyh") || tp.equals("rhxz") || tp.equals("hydw"))
		{
			System.out.print("转至menu3...");
			render("menu3.html");
		}
		else
		{
			System.out.println("计算页码。。");
			List<NewsEdit> news_count = ne.getNewsCount( type );
			System.out.println(news_count.get(0).get("cnt"));
			int count = Integer.parseInt(news_count.get(0).get("cnt").toString());
			System.out.println(count);
			int page_all = (int) Math.ceil(count/8.0);
			System.out.println(count+":"+page_all);
			//传页码过去
			setAttr("onpage", page);
			setAttr("page_all", page_all);
			render("menu2.html");
		}
	}
}
