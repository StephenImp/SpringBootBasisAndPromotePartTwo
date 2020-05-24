package com.cn.demoApp.tomcat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MOZi on 2019/1/24.
 *
 * 这里只是一个例子
 * 为什么不用加入web.xml
 * 因为，在pom中 加入了plugin
 */
@WebServlet(urlPatterns = "/demo")
public class DemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        String message = req.getParameter("message");

        resp.getWriter().print(message);

        resp.getWriter().print("<br/>");

        String contextPath = req.getServletContext().getContextPath();

        resp.getWriter().print("contextPath:"+contextPath);

    }
}
