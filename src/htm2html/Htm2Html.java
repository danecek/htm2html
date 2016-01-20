/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htm2html;

import static htm2html.Htm2Html.State.*;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author danecek
 */
public class Htm2Html {

    enum State {
        START, DOT, H, T, M,
    }

    private static final Logger LOG = Logger.getLogger(Htm2Html.class.getName());

    private static String htm2html(String s) {
        boolean rfl = false;
        StringBuilder sb = new StringBuilder();
        State state = START;
        for (char c : s.toCharArray()) {
            char lc = Character.toLowerCase(c);
            if (state == START && lc == '.') {
                state = DOT;
            } else if (state == DOT && lc == 'h') {
                state = H;
            } else if (state == H && lc == 't') {
                state = T;
            } else if (state == T && lc == 'm') {
                state = M;
            } else if (state == M) {
                if (lc != 'l') {
                    sb.append('l');
                    rfl = true;

                }
                state = START;
            }
            sb.append(lc);
        }
        if (rfl) {
            LOG.info(sb.toString());
            replaceFl = true;
        }
        return sb.toString();
    }

    static boolean replaceFl = false;

    public static void main(String[] args) throws IOException {        
        LOG.addHandler( new FileHandler("htm2html.log"));
        Path start = Paths.get("./src");
        Files.walkFileTree(start, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //    LOG.info(dir.toString());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                replaceFl = false;
                if (file.toString().toLowerCase().endsWith(".html")) {
                    List<String> lines = Files.readAllLines(file);
                    List<String> outLines = new ArrayList<>();
                    lines.stream().forEach((line) -> {
                        outLines.add(htm2html(line));
                    });
                    if (replaceFl) {
                        LOG.info("***************** Modified file:" + file.toString());
                        Files.write(file, outLines);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override

            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                //   LOG.log(Level.SEVERE, "visitFileFailed{0}", file.toString());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                //    LOG.info(dir.toString());
                return FileVisitResult.CONTINUE;
            }
        }
        );
    }

}
