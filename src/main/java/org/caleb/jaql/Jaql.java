package org.caleb.jaql;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.*;

public class Jaql {
    private String nextLine = "";

    private Map<String, String> queriesByName = new HashMap<>();

    public Jaql(InputStream resourceAsStream) throws IOException {
        String inputString = IOUtils.toString(resourceAsStream, UTF_8);
        Iterable<String> lines = Splitter.on("\n").omitEmptyStrings().trimResults().split(inputString);
        Iterator<String> linesIterator = lines.iterator();

        while (linesIterator.hasNext()) {
            String line;
            if (StringUtils.isNotBlank(nextLine)) {
                line = nextLine;
                nextLine = "";
            } else {
                line = linesIterator.next();
            }

            String regex = "^\\s*--\\s*name:\\s*";
            Pattern namePattern = Pattern.compile(regex);
            Matcher nameMatcher = namePattern.matcher(line);
            if (nameMatcher.find()) {
                String name = line.replaceAll(regex, "");
                StringBuilder sb = new StringBuilder();
                boolean firstLine = true;
                while (linesIterator.hasNext()) {
                    String queryLine = linesIterator.next();
                    nameMatcher = namePattern.matcher(queryLine);
                    if (nameMatcher.find()) {
                        nextLine = queryLine;
                        break;
                    }

                    if (!firstLine) {
                        sb.append("\n");
                    }

                    sb.append(queryLine);
                    firstLine = false;
                }
                queriesByName.put(name, sb.toString());
            }
        }
    }

    public String getQueryByName(String name) {
        return queriesByName.get(name);
    }
}
