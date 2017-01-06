package com.sgaop.common.util;

import com.sgaop.entity.news.NewsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
public class NewsMenuTree {

    /**
     * 迭代ID,PID树
     *
     * @param menus
     * @param parentId
     * @return
     */
    public static List<NewsMenu> createTree(List<NewsMenu> menus, int parentId) {
        List<NewsMenu> childList = new ArrayList<>();
        for (NewsMenu c : menus) {
            int id = c.getId();
            int pid = c.getPid();
            if (parentId == pid) {
                List<NewsMenu> childs = NewsMenuTree.createTree(menus, id);
                c.setChildren(childs);
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
    public static NewsMenu getChilds(List<NewsMenu> menus, int id) {
        NewsMenu menu = null;
        sw:
        for (NewsMenu p : menus) {
            if (p.getId() == id) {
                menu = p;
                break sw;
            } else if (p.getChildren().size() > 0) {
                menu = getChilds(p.getChildren(), id);
                if (menu != null) {
                    break sw;
                }
            }
        }
        return menu;
    }


    /**
     * 迭代ID,PID树
     *
     * @param menus
     * @param parentId
     * @return
     */
    public static List<HashMap> createHashMap(List<NewsMenu> menus, int parentId) {
        menus = NewsMenuTree.createTree(menus, parentId);
        List<HashMap> list = new ArrayList<>();
        for (NewsMenu menu : menus) {
            HashMap map = new HashMap();
            map.put("id", menu.getId());
            map.put("pid", menu.getPid());
            map.put("menuName", menu.getMenuName());
            map.put("menuTarget", menu.getMenuTarget());
            map.put("menuType", menu.getMenuType());
            map.put("menuIcon", menu.getMenuIcon());
            map.put("permission", menu.getPermission());
            map.put("locked", menu.isLocked());
            map.put("hasChild", menu.getChildren().size() > 0);
            list.add(map);
        }
        return list;
    }

}
