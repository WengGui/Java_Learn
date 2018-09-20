package com.weng.concurrency.threadLocal;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("do filter:"+Thread.currentThread().getId()+"--"+request.getServletPath());
        //request.getSession().getAttribute("user");
        RequestHolder.add(Thread.currentThread().getId());
        // 不拦截请求，只是在请求处理前截取部分数据，再把请求扔回去处理
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
