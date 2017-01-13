package com.sgaop.common.util;

import com.sgaop.entity.sys.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
public class MenuTree {

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
                List<Menu> childs = MenuTree.createTree(menus, id);
                c.setChildren(childs);
                childList.add(c);
            }
        }
        return childList;
    }

    /**
     * 排序ID,PID树
     *
     * @param oldMenu
     * @return
     */
    public static List<Menu> ShortTree(List<Menu> oldMenu) {
        List<Menu> shortMenu = new ArrayList<>();
        for (Menu c : oldMenu) {
            if(c.getChildren().size()>0){
                List<Menu> nenuChilds=  c.getChildren();
                Collections.sort(nenuChilds, new Menu());
                nenuChilds= ShortTree(nenuChilds);
                c.setChildren(nenuChilds);
            }
            shortMenu.add(c);
        }
        return shortMenu;
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
    public static List<HashMap> createHashMap(List<Menu> menus, int parentId) {
        menus = MenuTree.createTree(menus, parentId);
        List<HashMap> list = new ArrayList<>();
        for (Menu menu : menus) {
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
