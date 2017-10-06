package org.caleb.jaql;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

public class Jaql {
    String nextLine = "";
    private static final String NAME_PREFIX = "-- name: ";

    private Map<String, String> queriesByName = new HashMap<>();

    public Jaql(InputStream resourceAsStream) throws IOException {
        String inputString = IOUtils.toString(resourceAsStream, UTF_8);
        Iterable<String> lines = Splitter.on("\n").omitEmptyStrings().trimResults().split(inputString);
        Iterator<String> linesIterator = lines.iterator();

        while(linesIterator.hasNext()) {
            String line = "";
            if(StringUtils.isNotBlank(nextLine)) {
                line = nextLine;
                nextLine = "";
            } else {
                line = linesIterator.next();
            }
            if(StringUtils.startsWith(line, NAME_PREFIX)) {
               String name = StringUtils.removeStart(line, NAME_PREFIX).trim();
               StringBuilder sb = new StringBuilder();
               boolean firstLine = true;
               while(linesIterator.hasNext()) {
                    String queryLine = linesIterator.next();
                    if(StringUtils.startsWith(queryLine, NAME_PREFIX)) {
                        nextLine = queryLine;
                        break;
                    }
                    if(firstLine == false) {
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
