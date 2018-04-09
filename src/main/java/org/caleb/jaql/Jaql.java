package org.caleb.jaql;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.RegexValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

public class Jaql {
    String nextLine = "";
    private static final RegexValidator NAME_REGEX = new RegexValidator("-- *name *: *([a-zA-Z0-9 _-]+)");
    private static final RegexValidator SQL_COMMENT = new RegexValidator("--.*");
    private static final String MULTILINE_COMMENT = "(?s)\\/\\*.*?\\*\\/";
    private static final String INLINE_COMMENT = "\\s*--.*";

    private Map<String, String> queriesByName = new HashMap<>();

    public Jaql(InputStream resourceAsStream) throws IOException {
        String inputString = IOUtils.toString(resourceAsStream, UTF_8);
        inputString = inputString.replaceAll(MULTILINE_COMMENT, " ");

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
            String[] matches = NAME_REGEX.match(line);
            if(matches != null && matches.length == 1) {
               String name = StringUtils.strip(matches[0]);
               StringBuilder sb = new StringBuilder();
               boolean firstLine = true;
               while(linesIterator.hasNext()) {
                    String queryLine = linesIterator.next();
                    if(NAME_REGEX.isValid(queryLine)) {
                        nextLine = queryLine;
                        break;
                    }
                    if(SQL_COMMENT.isValid(queryLine) == false) {
                        if(firstLine == false) {
                            sb.append("\n");
                        }
                        queryLine = queryLine.replaceAll(INLINE_COMMENT, "");
                        sb.append(queryLine);
                        firstLine = false;
                    }
               }
               queriesByName.put(name, sb.toString());
            }
        }
    }

    public String getQueryByName(String name) {
        return queriesByName.get(name);
    }

    public List<String> getQueryNames() {
        List<String> queryNames = new ArrayList<>(queriesByName.keySet());
        Collections.sort(queryNames);
        return queryNames;
    }
}
