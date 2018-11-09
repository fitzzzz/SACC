package fr.si5.cc.td1;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "Article", value = "/articles")
public class ArticlesServlet extends HttpServlet {
    private List<Article> articles = new ArrayList<>();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Properties properties = System.getProperties();

        response.setContentType("text/plain");
        response.getWriter().print(new Gson().toJson(articles));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Article a = new Gson().fromJson(req.getReader(), Article.class);
        articles.add(a);

        resp.getWriter().print("ok");
    }
}
