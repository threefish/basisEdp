package com.sgaop.common.util;

import com.sgaop.entity.sys.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
public class Tree {

    /**
     * 迭代ID,PID树
     *
     * @param menus
     * @param parentId
     * @return
     */
    public static List<Menu> createTree(List<Menu> menus, int parentId) {
        List<Menu> childList = new ArrayList<>();
        for (Menu c : menus) {
            int id = c.getId();
            int pid = c.getPid();
            if (parentId == pid) {
                List<Menu> childs = Tree.createTree(menus, id);
                c.setChilds(childs);
                childList.add(c);
            }
        }
        return childList;
    }


    /**
     * 取得子菜单
     *
     * @param menus
     * @param id
     * @return
     */
    public static Menu getChilds(List<Menu> menus, int id) {
        Menu menu = null;
        sw:
        for (Menu p : menus) {
            if (p.getId() == id) {
                menu = p;
                break sw;
            } else if (p.getChilds().size() > 0) {
                menu = getChilds(p.getChilds(), id);
                if (menu != null) {
                    break sw;
                }
            }
        }
        return menu;
    }

}
