package com.cqjtysxh.model;

import java.util.List;


import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.cqjtysxh.utils.Paginate;


public class Users extends Model<Users> {

	private static final long serialVersionUID = 1L;
	public static final Users dao = new Users().dao();
	
	//登录验证
	public boolean VerifyUser(String username, String password)
	{
//		System.out.println("select * from users where user_name=\""+username+"\" and pass_word=\""+password+"\"");
		List<Users> userU = dao.find("select * from users where user_name=\""+username+"\" and pass_word=\""+password+"\"");
//		System.out.println(user.size());
		if(0 != userU.size())
			return true;
		else
			return false;
	}
	//注册查重
	public boolean ChkUserHad(String username)
	{
//		System.out.println("select * from users where user_name=\""+username+"\"");
		List<Users> userU = dao.find("select * from users where user_name=\""+username+"\"");
		if( 0 != userU.size())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	//遍历用户
	public List<Users> UserList()
	{
		return dao.find("select * from users");
	}
	
	//遍历用户
	public List<Users> UserList(int page, int num)
	{
		return dao.find("select * from users limit "+page+", "+num);
	}
	
	//获取用户ID
	public List<Users>  getUser(String username)
	{
//		System.out.println("select * from users where user_name=\""+username+"\"");
//		List<Users> userU = dao.find("select * from users where user_name=\""+username+"\"");
//		System.out.println(user);
//		System.out.println(user.get(0));
//		System.out.println(user.get(0).get("id"));
		return dao.find("select * from users where user_name=\""+username+"\"");
	}
	
	//魔符查询
	public List<Users> findUser(String username, int page, int num)
	{
//		System.out.println("select * from users where user_name like \"%"+username+"%\" limit "+page+", "+num);
		return dao.find("select * from users where user_name like \"%"+username+"%\" limit "+page+", "+num);
	}
	
	//魔符查询
	public List<Users> findUser(String username)
	{
//		System.out.println("select * from users where user_name like \"%"+username+"%\"  ");
		return dao.find("select * from users where user_name like \"%"+username+"%\"  ");
	}
}