package com.kalwador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kalwador
 */
public class FileWalker extends HttpServlet {

    MapDisc md;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startPath = "C:\\Program Files\\Internet Explorer";
        String normalizedStartPath = Utils.replaceOut(startPath);
        String path = request.getParameter("path");
        
        /**
         * Handling wrong url path and lack of url path
         * Incorrect url path way to start point
         */
        if (path == null || !path.startsWith(normalizedStartPath) || md == null) {
            md = new MapDisc();
            md.map(startPath);
            path = startPath;
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DISC MAPPER</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<link href=\"style/css/bootstrap.css\" type=\"text/css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<h1>Disc Mapper\tAuthor Kalwador</h1>");
            out.println("<hr>");
            out.println("<table class=\"table\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th></th>");
            out.println("<th>Name:</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            String normalizedPath = Utils.replaceOut(path);
            
            
            /**
             * Step Backward button
             */
            out.println("<tr>");
            out.println("<td><span class=\"glyphicon glyphicon-step-backward\"></span></td>");
            String backWay = normalizedPath.substring(0, normalizedPath.lastIndexOf("/"));
            out.println("<td><a href=\"?path=" + backWay + "\">BACK</a></td>");
            out.println("</tr>");

            /**
             * List of all directories included in actual path
             */
            String[] tab = normalizedPath.split("/");
            for (String string : md.getFiles().keySet()) {
                String[] tab2 = string.split("/");
                if (tab2.length >= tab.length) {
                    if ((tab[tab.length - 1].equals(tab2[tab.length - 1])) && (tab.length + 1 == tab2.length)) {
                        out.println("<tr>");
                        out.println("<td><span class=\"glyphicon glyphicon-folder-close\"></span></td>");
                        out.println("<td><a href=\"?path=" + string + "\">" + string.replaceAll("!=!", " ") + "</a></td>");
                        out.println("</tr>");
                    }
                }
            }
            /**
             * Handling '/' character at the end of url path
             */
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
                System.out.println("2. ucinamy:" + path);
            }

            /**
             * List of all files included in actual path
             */
            for (String file : md.getFiles().get(path)) {
                out.println("<tr>");
                out.println("<td><span class=\"glyphicon glyphicon-file\"></span></td>");
                out.println("<td><a href=DiscMapper/DownloadFile?path=" + path + "/" + file + ">" + file + "</a></td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
