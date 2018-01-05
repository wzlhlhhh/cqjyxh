package com.cqjtysxh.model;

import java.util.List;
import com.jfinal.plugin.activerecord.Model;

public class NewsEdit extends Model<NewsEdit>{
	//取出各种类型新闻数据
	private static final long serialVersionUID = 775988305820064296L;
	public static final NewsEdit dao = new NewsEdit().dao();
	
	//分开获取
	public List<NewsEdit> getNews(String NewsType)
	{
		System.out.println("select * from content where type=\""+NewsType+"\" order by date desc" );
		return dao.find("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc" );
	}
	
	public List<NewsEdit> getNews(String NewsType, int num)
	{
//		System.out.println("select * from content where type=\""+NewsType+"\" or type=\""+NewsType+"\" order by date desc limit 1,"+num+"");
		return dao.find("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit 0,"+num+"" );
	}
	
	public List<NewsEdit> getNewsById(int id)
	{
		return dao.find("select * from content where id="+id );
	}
	
	public List<NewsEdit> getNews(String NewsType, int start, int end)
	{
//		System.out.println("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end);
		return dao.find("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end );
	}
	
	public List<NewsEdit>  getNewsCount(String NewsType)
	{
//		System.out.println("select COUNT(*) as cnt from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc");
		return dao.find("select COUNT(*) as cnt from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc");
	}
	
	//新闻概览
	public List<NewsEdit>  getNewsll()
	{
//		System.out.println("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end);
		return dao.find("select type,count(type) total from content  group by  type");
	}
	
	//取出所有新闻
	public List<NewsEdit> NewsList()
	{
//		System.out.println("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end);
		return dao.find("select * from content where subtype not in ('协会章程', '组织机构', '入会须知', '会员单位', '专业委员会', '协会简介', '协会领导') order by date desc");
	}
	
	//取出所有新闻
	public List<NewsEdit> NewsList(int start, int end)
	{
//			System.out.println("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end);
		return dao.find("select * from content where subtype not in ('协会章程', '组织机构', '入会须知', '会员单位', '专业委员会', '协会简介', '协会领导') order by date desc limit "+start+", "+end);
	}
	
	//查找
	public List<NewsEdit> findNews(String name, int start, int end)
	{
//			System.out.println("select * from content where type=\""+NewsType+"\" or subtype=\""+NewsType+"\" order by date desc limit "+start+", "+end);
		return dao.find("select * from content  where title like \"%"+name+"%\" or type like \"%"+name+"%\" or subtype like \"%"+name+"%\" limit "+start+", "+end);
	}
	
	public List<NewsEdit> findNews(String name)
	{
//			System.out.println("select * from content  where title likes \"%"+name+"%\" or type likes \"%"+name+"%\" or subtype \"%"+name+"%\"");
		return dao.find("select * from content  where title like \"%"+name+"%\" or type like \"%"+name+"%\" or subtype like \"%"+name+"%\"");
	}
	//列出1级类别
	public List<NewsEdit> getNewsType()
	{
//			System.out.println("select * from content  where title likes \"%"+name+"%\" or type likes \"%"+name+"%\" or subtype \"%"+name+"%\"");
		return dao.find("select DISTINCT type from content  ");
	}
	//列出2级类别
	public List<NewsEdit> getNewsSubType()
	{
//			System.out.println("select * from content  where title likes \"%"+name+"%\" or type likes \"%"+name+"%\" or subtype \"%"+name+"%\"");
		return dao.find("select DISTINCT subtype from content  ");
	}
	
	
}
