package com.sgaop.entity.news;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
@Table("tb_news_menu")
public class NewsMenu implements Serializable, Comparator<NewsMenu> {

    private static final long serialVersionUID = 1L;
    @ID
    @Colum
    private int id;

    @Colum
    private int pid;

    @Colum("menu_name")
    private String menuName;

    @Colum("menu_target")
    private String menuTarget;

    //权限标识
    @Colum("permission")
    private String permission;

    @Colum("menu_icon")
    private String menuIcon;

    //权限类型 0 菜单  1数据
    @Colum("menu_type")
    private int menuType;

    @Colum("locked")
    private boolean locked;

    @Colum("can_delect")
    private boolean canDelect;

    @Colum("description")
    private String description;

    @Colum("short_no")
    private int shortNo;

    @Colum("ct")
    private Timestamp createTime;

    @Colum("ut")
    private Timestamp updateTime;

    private List<NewsMenu> children;

    /**
     * ztree支持
     **/
    private String iconSkin;
    public boolean chkDisabled = false;

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
        this.iconSkin = "fa " + menuIcon + " ";
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    /**
     * ztree支持
     **/

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }

    public int getMenuType() {
        return menuType;
    }

    public boolean isCanDelect() {
        return canDelect;
    }

    public void setCanDelect(boolean canDelect) {
        this.canDelect = canDelect;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<NewsMenu> getChildren() {
        return children;
    }

    public int getShortNo() {
        return shortNo;
    }

    public void setShortNo(int shortNo) {
        this.shortNo = shortNo;
    }

    public String getMenuIcon() {
        return menuIcon;
    }


    public void setChildren(List<NewsMenu> children) {
        this.children = children;
    }


    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param m0 the first object to be compared.
     * @param m1 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(NewsMenu m0, NewsMenu m1) {
        if (m0.getShortNo() > m1.getShortNo()) {
            return 0;
        } else if (m0.getShortNo() == m1.getShortNo()) {
            return -1;
        } else {
            return -1;
        }
    }
}
