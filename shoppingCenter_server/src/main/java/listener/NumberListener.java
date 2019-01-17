package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class NumberListener implements HttpSessionListener, HttpSessionAttributeListener, ServletContextListener {
    private ServletContext context;
    private Integer visitorCount;

    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        context.setAttribute("OnlineCount", 0);
        context.setAttribute("VisitorCount", 0);
    }

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        // 获取当前人数
        visitorCount = (Integer) context.getAttribute("VisitorCount");

        visitorCount++;

        // 改变人数
        context.setAttribute("VisitorCount", visitorCount);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        visitorCount = (Integer) context.getAttribute("VisitorCount");
        visitorCount--;

        context.setAttribute("VisitorCount", visitorCount);
    }

    public void attributeAdded(HttpSessionBindingEvent se) {
        if(se.getName().equals("username")){
            Integer onlineCount = (Integer) context.getAttribute("OnlineCount");
            onlineCount++;
            visitorCount--;
            context.setAttribute("VisitorCount", visitorCount);
            context.setAttribute("OnlineCount", onlineCount);
        }

    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        if(se.getName().equals("username")) {
            Integer onlineCount = (Integer) context.getAttribute("OnlineCount");
            onlineCount--;
            visitorCount++;
            context.setAttribute("VisitorCount", visitorCount);
            context.setAttribute("OnlineCount", onlineCount);
        }
    }
}
