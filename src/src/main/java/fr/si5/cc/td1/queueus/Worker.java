package fr.si5.cc.td1.queueus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TaskWorker",
        urlPatterns = "/testqueue/worker"
)
public class Worker extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("key");
        try {
            Thread.sleep(500);
//            response.getWriter().print(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
