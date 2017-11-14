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

    MapDisc mapDiscUtil;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startPath = "D:\\test2";
        String normalizedStartPath = Utils.replaceOut(startPath);
        String requestPath = request.getParameter("path");

        /**
         * Handling wrong url path and lack of url path Incorrect url path way
         * to start point
         */
        if (requestPath == null || !requestPath.startsWith(normalizedStartPath) || mapDiscUtil == null) {
            mapDiscUtil = new MapDisc();
            mapDiscUtil.map(startPath);
            requestPath = startPath;
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

            String normalizedRequestPath = Utils.replaceOut(requestPath);

            /**
             * Step Backward button
             */
            out.println("<tr>");
            out.println("<td><span class=\"glyphicon glyphicon-step-backward\"></span></td>");
            String backButtonPath = normalizedRequestPath.substring(0, normalizedRequestPath.lastIndexOf("/"));
            out.println("<td><a href=\"?path=" + backButtonPath + "\">BACK</a></td>");
            out.println("</tr>");

            /**
             * List of all directories included in actual path
             */
            for (String filePath : mapDiscUtil.getFiles().keySet()) {
                if (filterDirectories(normalizedRequestPath, filePath)) {
                        out.println("<tr>");
                        out.println("<td><span class=\"glyphicon glyphicon-folder-close\"></span></td>");
                        out.println("<td><a href=\"?path=" + filePath + "\">" + filePath.replaceAll("!=!", " ") + "</a></td>");
                        out.println("</tr>");
                    }

                }
                /**
                 * Handling '/' character at the end of url path
                 */
                if (requestPath.endsWith("/")) {
                    requestPath = requestPath.substring(0, requestPath.length() - 1);
                }

                /**
                 * List of all files included in actual path
                 */
                for (String file : mapDiscUtil.getFiles().get(requestPath)) {
                    out.println("<tr>");
                    out.println("<td><span class=\"glyphicon glyphicon-file\"></span></td>");
                    out.println("<td><a href=DiscMapper/DownloadFile?path=" + requestPath + "/" + file + ">" + file + "</a></td>");
                    out.println("</tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }

    

    private boolean filterDirectories(String normalizedRequestPath, String filePath) {
        String[] requestPathTable = normalizedRequestPath.split("/");
        String[] filePathTable = filePath.split("/");

        //condition dropping previous directories
        if (filePathTable.length < requestPathTable.length) {
            return false;
        }

        //condition dropping directories further than one
        if (requestPathTable.length + 1 != filePathTable.length) {
            return false;
        }

        if (!requestPathTable[requestPathTable.length - 1].equals(filePathTable[requestPathTable.length - 1])) {
            return false;
        }
        //condition dropping directories with diffrent actual names
        if (!normalizedRequestPath.equals(filePath.substring(0, normalizedRequestPath.length()))) {
            return false;
        }
        return true;
    }
}
