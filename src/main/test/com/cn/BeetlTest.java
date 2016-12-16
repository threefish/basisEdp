package com.cn;

import com.google.gson.Gson;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/14 0014
 * To change this template use File | Settings | File Templates.
 */
public class BeetlTest {

    private static void run(HashMap data) throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        HashMap i18n=new HashMap();
        i18n.put("sys_user","系统");
        gt.setSharedVars(i18n);
        Template t = gt.getTemplate("hello,${sys_user}");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }


    private static HashMap createMap(int index, String[] all, String str, HashMap data) {
        boolean dorun = true;
        HashMap temp = (HashMap) data.get(all[0]);
        w:
        do {
            if (temp == null) {
                temp = new HashMap();
                dorun = false;
            } else {
                temp = (HashMap) temp.get(all);
                if (temp == null) {
                    temp = new HashMap();
                    dorun = false;
                }
            }
        } while (dorun);


//        HashMap tM = (HashMap) data.get(str);
//        if (tM == null) {
//            tM = new HashMap();
//            data.put(str, tM);
//        }
        return temp;
    }

    private static HashMap getMap(int index, String[] all, String str, HashMap data) {
        boolean dorun = true;
        HashMap temp = (HashMap) data.get(all[0]);
        w:
        do {
            if (temp == null) {
                temp = new HashMap();
                dorun = false;
            } else {
                temp = (HashMap) temp.get(all);
                if (temp == null) {
                    temp = new HashMap();
                    dorun = false;
                }
            }
        } while (dorun);
        return temp;
    }

    private static void test(){
        HashMap data = new HashMap();
        String[] temp = "user.info.xxx".split("\\.");
        int len = temp.length;
        int index = 1;
        for (int i = 0; i < len; i++) {
            String t = temp[i];
            data = getMap(i, temp, t, data);
            //不存在
            if (!data.containsKey(t)) {
                data.put(t, new HashMap<>());
            } else {

            }
        }

        for (String t : temp) {
            if (!data.containsKey(t)) {
                data.put(t, new HashMap<>());
            } else {
                data.put(t, createMap(index, temp, t, data));
            }
            index++;
        }
        System.out.println(new Gson().toJson(data));
//        run(data);
    }


    public static void main(String[] args) throws IOException {
         run(new HashMap());
    }
}
