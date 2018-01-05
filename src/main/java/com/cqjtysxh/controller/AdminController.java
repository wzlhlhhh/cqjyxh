package com.cqjtysxh.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.cqjtysxh.config.CONST;
import com.cqjtysxh.model.NewsEdit;
import com.cqjtysxh.model.Users;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.ndktools.javamd5.Mademd5;


public class AdminController extends Controller{
	
	public void index()
	{
		render("login.html");
	}
	
	public void welcome()
	{
		String username = getPara("username");
		String password = getPara("password");
		String pwd = DigestUtils.md5Hex(password);
		Users user = new Users();
		System.out.println(username+":"+password);
		boolean b = user.VerifyUser(username, pwd);
		System.out.println(b);
		if(b)
		{
			setSessionAttr("user", username);
			render( "index.html");
		}
		else
		{
			setAttr("msg", "账号密码出错！");
			render("login.html");
		}
	}
	//用户管理
	public void user()
	{
		render("user.html");
	}
	
	public void user_add()
	{
		render("user-add.html");
	}
	
	public void find_user()
	{
		//页码
		String queryString = getRequest().getQueryString();
		int s;
		if( queryString == null)
		{
			s = -1;
		}
		else
		{
			s = queryString.indexOf("page");
		}

		int page;
		if(s == -1)
		{
			page = 1;
		}
		else
		{
			page = getParaToInt("page");
		}
		
		String username = getPara("find_usera");
		Users user = new Users();
		List<Users> userall = user.findUser(username);
		List<Users> userlist = user.findUser(username ,(page-1)*6, 6);
		
		
		int page_all = (int) Math.ceil(userall.size()/6.0);
		setAttr("users", userlist);
		setAttr("onpage", page);
		setAttr("page_all", page_all);
		setAttr("findstatus", 1);
		setAttr("finduser", username);
		System.out.println(page+":"+page_all+":"+username);
		
		render("user-view.html");
	}
	
	
	public void add_user()
	{
		String username = getPara("username");
		String password = getPara("password");
		String real_name = getPara("real_name");
		System.out.println(username);
		//加密
		String pwd = DigestUtils.md5Hex(password);
        System.out.println(pwd);
        //查重
        Users user = new Users();
        if(!user.ChkUserHad(username))
        {
        	setAttr("msg", username+"已存在！");
        	render("user-add.html");
        }
        else
        {
        	//存储
    		
    		user.set("user_name", username);
    		user.set("pass_word", pwd);
    		user.set("real_name", real_name);
    		user.set("role", 1);
    		user.set("status", 1);
    		user.save();
    		
    		setAttr("msg", "添加新用户成功！");
    		user_view();
        }
		
	}

	public void user_view()
	{
		//页码
		String queryString = getRequest().getQueryString();
		int s;
		if( queryString == null)
		{
			s = -1;
		}
		else
		{
			s = queryString.indexOf("page");
		}

		int page;
		if(s == -1)
		{
			page = 1;
		}
		else
		{
			page = getParaToInt("page");
		}
		
		Users user = new Users();
		//取出用户
		List<Users> userlist = user.UserList((page-1)*6, 6);
		List<Users> userall = user.UserList();
		int page_all = (int) Math.ceil(userall.size()/6.0); 
		
		setAttr("users", userlist);
		setAttr("onpage", page);
		setAttr("page_all", page_all);
		
		render("user-view.html");
	}
	
	public void del_user()
	{
		int id = getParaToInt("id");
		Users user = new Users();
		user.deleteById(id);
		
		setAttr("msg", "删除成功！");
		user_view();
	}
	
	public void update_user()
	{
		int id = getParaToInt("id");
		String role = getPara("role");
		String real_name = getPara("real_name");
		Users user = new Users();
		user.set("id", id);
		user.set("role", role);
		user.set("real_name", real_name);
		user.update();
		
		setAttr("msg", "更新成功！");
		user_view();
	}
	
	public void user_pwdch()
	{
		setAttr("user", getSessionAttr("user"));
		render("user-pwdch.html");
	}
	
	public void pwd_change()
	{
		String username = getPara("username");
		String pwd_old = getPara("pwd_old");
		String pwd_new = getPara("pwd_new");
		//验证旧密码
		String pwd = DigestUtils.md5Hex(pwd_old);

		Users user = new Users();
		boolean b = user.VerifyUser(username, pwd);
		System.out.println(b);
		if(b)
		{
			System.out.println(DigestUtils.md5Hex(pwd_new));
			String pwd_new_MD5 = DigestUtils.md5Hex(pwd_new);
			
			List<Users> u = user.getUser(username);
			user.set("id", u.get(0).get("id"));
			user.set("pass_word", pwd_new_MD5);
			user.update();
			setAttr("msg", "更新成功！");
			user_pwdch();
		}
		else
		{
			setAttr("msg", "旧密码输入错误！");
			user_pwdch();
		}
	}
	//新闻管理
	public void news()
	{
		render("news.html");
	}
	
	public void news_ll()
	{
		NewsEdit news = new NewsEdit();
		setAttr("news", news.getNewsll());
		render("news-ll.html");
	}
	
	public void news_add()
	{
		NewsEdit news = new NewsEdit();
		setAttr("types", news.getNewsType());
		setAttr("subtypes", news.getNewsSubType());
		render("news-add.html");
	}
	
	public void news_view()
	{
		//页码
		String queryString = getRequest().getQueryString();
		int s;
		if( queryString == null)
		{
			s = -1;
		}
		else
		{
			s = queryString.indexOf("page");
		}
		
		int page;
		if(s == -1)
		{
			page = 1;
		}
		else
		{
			page = getParaToInt("page");
		}
		
		NewsEdit news = new NewsEdit();
		//取出新闻
		
		List<NewsEdit> newslist = news.NewsList((page-1)*6, 6);
		List<NewsEdit> newsall = news.NewsList();
		int page_all = (int) Math.ceil(newsall.size()/6.0); 
		System.out.println(page_all);
		
		if( 0 == newslist.size() )
		{
			page = 0;
		}
		
		setAttr("news", newslist);
		setAttr("onpage", page);
		setAttr("page_all", page_all);
		
		render("news-view.html");
	}
	
	public void find_news()
	{
		//页码
		String queryString = getRequest().getQueryString();
		int s;
		if( queryString == null)
		{
			s = -1;
		}
		else
		{
			s = queryString.indexOf("page");
		}

		int page;
		if(s == -1)
		{
			page = 1;
		}
		else
		{
			page = getParaToInt("page");
		}
		System.out.println(page);
		String newstitle = getPara("find_newsa");
		
		NewsEdit news = new NewsEdit();
		List<NewsEdit> newsall = news.findNews(newstitle);
		List<NewsEdit> newslist = news.findNews(newstitle ,(page-1)*6, 6);
		

		int page_all = (int) Math.ceil(newsall.size()/6.0);
		
		if( 0 == newslist.size() )
		{
			page = 0;
		}
		
		setAttr("news", newslist);
		setAttr("onpage", page);
		setAttr("page_all", page_all);
		setAttr("findstatus", 1);
		setAttr("findnews", newstitle);
		System.out.println(page+":"+page_all+":"+newstitle);
				
		render("news-view.html");
	}
	
	public void del_news()
	{
		int id = getParaToInt("id");
		NewsEdit news = new NewsEdit();
		news.deleteById(id);
		
		setAttr("msg", "删除成功！");
		news_view();
	}

    public void upload(){
    	
    	UploadFile upf = getFile("uploadPic");
    	upf.getUploadPath();
    	
    	System.out.println(upf.getUploadPath());
    	System.out.println();
    	System.out.println(upf.getFileName());
    	//获取IP
    	String ip = "";
    	try {
    		ip = InetAddress.getLocalHost().getHostAddress();
    	} catch (UnknownHostException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
//    	String data = "{errno:0 ,data:['122.14.214.242:/"+CONST.PJname+"/upload/"+upf.getFileName()+"']}";
    	String data = "{errno:0 ,data:['122.14.214.242/upload/"+upf.getFileName()+"']}";
    	setAttr("data", data);
    	System.out.print("{\"errno\":0, \"data\":[\"http://cqslkj.com.cn/upload/"+upf.getFileName()+"\"]}");
//    	System.out.println("{errno: 0, data:['"+upf.getUploadPath()+"/"+upf.getFileName()+"']}");
//    	renderText("{errno:0, data:['http://localhost:8080/"+CONST.PJname+"/upload/"+upf.getFileName()+"']}");
    	renderJson("{\"errno\":0, \"data\":[\"http://cqslkj.com.cn/upload/"+upf.getFileName()+"\"]}");
//    	renderJson("{errno:0, data:['http://localhost:8080/"+CONST.PJname+"/upload/"+upf.getFileName()+"']}");
    }
    
    public void add_news()
    {
    	System.out.println("准备生成新闻");
    	String title = getPara("title");
    	String author = getPara("author");
    	String content = getPara("content");
    	String type = getPara("type");
    	String subtype = getPara("subtype");
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	
    	NewsEdit ne = new NewsEdit();
    	ne.set("title", title);
    	ne.set("author", author);
    	ne.set("content", content);
    	ne.set("date", df.format(new Date()));
    	ne.set("type", type);
    	ne.set("subtype", subtype);
    	ne.save();
    	
    	setAttr("msg", "新闻已生成！");
    	news_view();
    }
    
    public void news_update()
    {
    	int id = getParaToInt("id");
    	NewsEdit ne = new NewsEdit();
    	List<NewsEdit> newslist = ne.getNewsById(id);
    	setAttr("news", newslist);
    	System.out.println(newslist);
    	render("news-update.html");
    }
    
    public void update_news()
    {
    	String title = getPara("title");
    	String type = getPara("type");
    	String subtype = getPara("subtype");
    	String author = getPara("author");
    	int id = getParaToInt("id");
    	String content = getPara("content");
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	NewsEdit ne = new NewsEdit();
    	ne.set("id", id);
    	ne.set("title", title);
    	ne.set("author", author);
    	ne.set("content", content);
    	ne.set("date", df.format(new Date()));
    	ne.set("type", type);
    	ne.set("subtype", subtype);
    	ne.update();
    	
    	setAttr("msg", "更新成功！");
    	news_update();
    }
    
    //协会网站管理
    public void systemxh()
    {
    	render("systemxh.html");
    }
    
    public void xhwzgl()
    {
    	String type = getPara("type");

    	NewsEdit ne = new NewsEdit();
    	List<NewsEdit> yn = ne.getNews(type);
    	
    	//判断是否存在
    	if(yn.size() == 0)
    	{
    		ne.set("subtype", type);
    		ne.set("author", "admin");
    		ne.set("title", "~");
    		ne.set("type", "");
    		ne.set("content", "请输入新内容");
    		ne.set("date", "2017-12-26 12:40:01");
    		ne.save();
    		System.out.println("haha");
    	}
    	System.out.print(ne.getNews(type).get(0).get("id"));
    	setAttr("content", ne.getNews(type).get(0).get("content"));
    	setAttr("id", ne.getNews(type).get(0).get("id"));
    	setAttr("type", type);
    	render("systemxh-two.html");
    }
    
    public void xhwzgl_change()
    {
    	int id = getParaToInt("id");
    	String type = getPara("type");
    	String content = getPara("content");
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	
    	NewsEdit ne = new NewsEdit();
    	
    	System.out.println("bb");
    	ne.set("id", id);
    	ne.set("content", content);
    	ne.set("date", df.format(new Date()));
    	ne.update();
    	
    	System.out.println(id);
    	
    	setAttr("msg", "更新成功！");
    	xhwzgl();
    	   	
    }
    
}
