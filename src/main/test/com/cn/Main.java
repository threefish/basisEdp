package com.cn;

import com.google.gson.Gson;
import com.sgaop.entity.sys.Menu;
import com.sgaop.entity.sys.UserAccount;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/9 0009
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void TestJDK8Base64() {
        String str = "是打发士大夫";
        String enStr = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后：" + enStr);
        String unStr = new String(Base64.getDecoder().decode(enStr), StandardCharsets.UTF_8);
        System.out.println("解密后：" + unStr);
    }

    public static void TestPassword() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName("admin");
//        String salt=UUID.randomUUID().toString().replaceAll("-","");
        String salt = "334ee3a373164b3f872ee16694bf1c77";
        userAccount.setSalt(salt);
        userAccount.setUserPass("123456");
        Sha256Hash sha = new Sha256Hash(userAccount.getUserPass(), userAccount.getSalt());
        System.out.printf("盐:%s  加密前:%s  加密后:%s", salt, userAccount.getUserPass(), sha.toHex());

    }

    public static void main(String[] args) {
        List<Menu> list = new ArrayList<>();
        int[] ints = {1, 5, 2, 2, 3};
        for (int i = 0; i < ints.length; i++) {
            Menu menu = new Menu();
            menu.setMenuName(i+"");
            menu.setId(i);
            menu.setShortNo(ints[i]);
            list.add(menu);
        }
        list.get(3).setShortNo(1);
        list.get(3).setMenuName("升级了");
        Collections.sort(list,new Menu());
        System.out.println(new Gson().toJson(list));
    }
}
