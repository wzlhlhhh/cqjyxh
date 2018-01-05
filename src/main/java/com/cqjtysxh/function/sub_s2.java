package com.cqjtysxh.function;

import java.io.Writer;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.stat.Scope;

public class sub_s2 extends Directive
{
	public void exec(Env env, Scope scope, Writer writer) 
	{
		// TODO 自动生成的方法存根
		String value = (String) exprList.eval(scope);
		System.out.print(value);
		
		if(value.length() > 6)
		{
			write(writer, value.substring(0, 6) + "...");
		}
		else
		{
			write(writer, value);
		}
	}

}