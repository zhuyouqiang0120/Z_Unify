package com.zens.unify.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class SessionInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		String loginUser = controller.getSessionAttr("loginUser");
		
		if( loginUser == "" || loginUser == "" ){
			controller.redirect("/");
		}else{
			ai.invoke();
		}
	}

}
