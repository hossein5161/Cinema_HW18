package util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HtmlUtil {
    public static void writeHtml(HttpServletResponse response, String title, String body) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(body);
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
