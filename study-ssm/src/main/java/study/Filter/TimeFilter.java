package study.Filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/*
* @WebFilter注解的urlPatterns属性配置了哪些请求可以进入该过滤器，/*表示所有请求。
*
* */

@Component
@WebFilter(urlPatterns = {"/*"})
public class TimeFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    /**
     * 对controller进行时间监控
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("开始执行过滤器");
        Long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("【过滤器】耗时 " + (new Date().getTime() - start));
        System.out.println("结束执行过滤器");
    }


    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}