package fr.si5.cc.td1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.cloud.storage.*;


@WebServlet(name = "CreateServlet", value = "/create")
public class CreateServlet extends HttpServlet {

    private static final Object storage = ;

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        BookDao dao = (BookDao) this.getServletContext().getAttribute("dao");
        Book book = new Book.Builder()
                .author(req.getParameter("author"))   // form parameter
                .description(req.getParameter("description"))
                .publishedDate(req.getParameter("publishedDate"))
                .title(req.getParameter("title"))
                .imageUrl(null)
                .build();
        try {
            Long id = dao.createBook(book);
            resp.sendRedirect("/read?id=" + id.toString());   // read what we just wrote
        } catch (Exception e) {
            throw new ServletException("Error creating book", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setAttribute("action", "Add");          // Part of the Header in form.jsp
        req.setAttribute("destination", "/create");  // The urlPattern to invoke (this Servlet)
//        req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
        req.getRequestDispatcher("/uploadForm.jsp").forward(req, resp);
    }
}
