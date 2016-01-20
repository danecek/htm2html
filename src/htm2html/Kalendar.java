/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htm2html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author danecek
 */
public class Kalendar {

    static int mesic = 1;

    private static void printHeader(PrintWriter pw) {
        pw.println("<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    <meta name=\"KEYWORDS\" content=\"radka, vrstevnice, bbcc, vzp, kolo, \n"
                + "          kolovýlet, cyklo, cyklovýlet, výlety, bike, cyklistika, diskuse, akce, \n"
                + "          cykloakce, koloakce, bowling, galerie, turistika, history, pozvánka, \n"
                + "          trasa, start, radoslava jandová, čvut, fel, stm, politika, válka\">\n"
                + "    <meta name=\"AUTHOR\" content=\"radka\">\n"
                + "    <title>template</title>\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "\n"
                + "    <link rel=\"stylesheet\" href=\"http://www.w3schools.com/lib/w3.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css\">\n"
                + "    <link rel=\"stylesheet\" href=\"../../css/kalendar.css\">\n"
                //                + "    <style>\n"
                //                + "        /* Set the height of the grid so that left and right col can be 100% (adjust as needed) */\n"
                //                + "        .w3-row {height: 200px}\n"
                //                + "\n"
                //                + "        /* Set a 100% height to left and right col */\n"
                //                + "        .w3-col.m2, w3-col.m2 {height: 100%;}\n"
                //                + "\n"
                //                + "        /* On small screens, set grid height to 'auto' */\n"
                //                + "        @media screen and (max-width: 601px) {\n"
                //                + "            .w3-row {height:auto;} \n"
                //                + "        }\n"
                //                + "    </style>\n"
                + "\n"
                + "    <body class=\"light w3-margin-24\">\n"
                + "        <div w3-include-HTML=\"./../../header.inc\">My Header include will go here.</div>");

    }

    private static void printDays(PrintWriter pw) {
        pw.println("<thead>");
        pw.println("<tr><th colspan=\"7\">" + mesic++ + "</th></tr>");
        pw.println(" <tr class=\"tyden\">\n"
                + "                    <th>Pondělí</th>\n"
                + "                    <th>Úterý</th>\n"
                + "                    <th>Středa</th>\n"
                + "                    <th>Čtvrtek</th>\n"
                + "                    <th>Pátek</th>\n"
                + "                    <th>Sobota</th>\n"
                + "                    <th>Neděle</th>\n"
                + "                </tr>");
        pw.println("</thead>");
    }

    public static void main(String[] args) throws IOException {
        String rok = "2016";
        Path originKal = Paths.get("/home/danecek/NetBeansProjects/vrstevnice/public_html/akce/"+ rok + "/akce" + rok +"origin.html");
        List<String> lines = Files.readAllLines(originKal);
        File f = new File("/home/danecek/NetBeansProjects/vrstevnice/public_html/akce/"+2016+"/akce"+2016+ ".html");
        FileOutputStream os = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
        printHeader(pw);
        pw.println("<div class=\"w3-container\">");
        pw.println("<div class=\"w3-row\">");
        pw.println("<div class=\"w3-col m1 \">levy sloupec</div>");
        for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
            String line = iterator.next();
            if (line.contains("<tbody><tr class=\"dny\">")) {
                if (mesic == 3
                        || mesic == 5
                        || mesic == 7
                        || mesic == 9
                        || mesic == 11) {
                    pw.println("<div class=\"w3-col m1 \">pravy sloupec</div>");
                    pw.println("</div>"); // row
                    pw.println("<div class=\"w3-row\">");
                    pw.println("<div class=\"w3-col m1 \">levy sloupec</div>");
                }
                pw.println("<div class=\"w3-col m4 w3-container\">");

                pw.println("<table class=\"kalendar \">");
                ///     pw.println("<tbody>");
                printDays(pw);
                pw.println("<tbody>");
                pw.println("<tr class=\"dny\">");
                line = iterator.next();
                while (!line.contains("</tbody>")) {
                    pw.println(line);
                    line = iterator.next();
                }
                pw.println("</tbody>");
                pw.println("</table>");
                pw.println("</div>"); // col

            }
        }
        pw.println("</div>");
        pw.println("</div>");
        pw.println("        <div w3-include-HTML=\"./../../footer.inc\">My Footer include will go here.</div>\n"
                + "        <script src=\"../../w3-include-HTML.js\"></script>\n"
                + "    </body>\n"
                + "</html> ");
        pw.close();
    }
}
