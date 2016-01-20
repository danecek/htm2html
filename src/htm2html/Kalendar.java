/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htm2html;

import java.io.File;
import java.io.IOException;
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

    private static void printHeader(PrintWriter pw) {
        pw.println("<!DOCTYPE html>\n"
                + "<html><head>\n"
                + "        <meta http-equiv=\"KEYWORDS\" content=\"radka, vrstevnice, bbcc, vzp, akce, kalendář,\n"
                + "              plán, cyklostezky, cyklotrasy, kolo, kolovýlet, na kolo, na kole, cyklo,\n"
                + "              cyklovýlet, výlety, bike, cyklistika, diskuse, cykloakce, koloakce, bowling,\n"
                + "              galerie, turistika, history, pozvánka, trasa, start, čvut fel, škola,\n"
                + "              prezident, vláda, politika, krimi\">\n"
                + "        <meta http-equiv=\"AUTHOR\" content=\"radka\">\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "        <link href=\"kalendar.css\" rel=\"stylesheet\" type=\"text/css\">\n"
                + "        <title>akce 2016</title>\n"
                + "    <link rel=\"stylesheet\" href=\"http://www.w3schools.com/lib/w3.css\">"
                + "    </head>\n"
                + "\n");
    }
    static int mesic = 1;

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
        Path p = Paths.get("akce 2016.html");
        List<String> lines = Files.readAllLines(p);
        File f = new File("new 2016.html");
        PrintWriter pw = new PrintWriter(f);
        printHeader(pw);
        pw.println("<body class=\"w3-container\">");
        pw.println("<div class=\"w3-row\">");
        for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
            String line = iterator.next();
            if (line.contains("<tbody><tr class=\"dny\">")) {
                if (mesic == 3
                        || mesic == 5
                        || mesic == 7
                        || mesic == 9
                        || mesic == 11) {
                    pw.println("</div>"); // row
                    pw.println("<div class=\"w3-row\">");
                }
                pw.println("<div class=\"w3-col m6 w3-container\">");

                pw.println("<table>");
           ///     pw.println("<tbody>");
                printDays(pw);

                pw.println("<tr class=\"dny\">");
                line = iterator.next();
                while (!line.contains("</tbody>")) {
                    pw.println(line);
                    line = iterator.next();
                }
              //  pw.println("</tbody>");
                pw.println("</table>");
                pw.println("</div>"); // col

            }
        }
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }
}
