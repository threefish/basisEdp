package com.sgaop.basis.plugin.beetl.format;

import com.google.gson.Gson;
import com.sgaop.basis.util.StringsTool;
import org.beetl.core.Format;

public class HtmlEscapeFormat implements Format {

    public Object format(Object data, String pattern) {
        String dataHtml = String.valueOf(data);
        if (pattern != null) {
            HtmlFormat jsonFormat = new Gson().fromJson("{" + pattern + "}", HtmlFormat.class);
            switch (jsonFormat.getType()) {
                case "delHtmlTag":
                    dataHtml = StringsTool.delHTMLTag(dataHtml);
                    if (jsonFormat.getLength() != 0 && dataHtml.length() > jsonFormat.getLength()) {
                        dataHtml = dataHtml.substring(0, jsonFormat.getLength()) + "......";
                    }
                    return dataHtml;
            }

        }
        return StringsTool.escapeHtml(dataHtml);
    }

}