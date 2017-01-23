package com.sgaop.common.cons;

import com.sgaop.basis.annotation.Inject;
import com.sgaop.basis.annotation.IocBean;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/10/12 0012
 * To change this template use File | Settings | File Templates.
 * 已固定常量不允许修改，修改后会导致beetl错误
 */
@IocBean
public class Cons {

    public static final String AUTHORIZATION_INFO = "AUTHORIZATION_INFO";

    public static final String SESSION_USER = "me";

    public static final String SESSION_USER_NAME = "me_name";

    public static final String UTF8 = "utf-8";

    public static final String SESSION_MENUS = "sessionMenus";

    public static final String CAPTCHA_ATTR = "CAPTCHA_ATTR";

    @Inject("java:attach.extensions")
    public static String FILE_EXTENSIONS ;

    /***
     * UEDITOR 富文本编辑工具栏配置
     */
    public final static String UE_ALL_TOOL = "['undo', 'redo', 'bold', 'indent', 'italic', 'underline', 'strikethrough', 'subscript', 'fontborder', 'superscript', 'formatmatch', 'blockquote', 'pasteplain', 'selectall', 'horizontal', 'removeformat', 'unlink', 'cleardoc', 'fontfamily', 'fontsize', 'paragraph', 'edittable', 'edittd', 'link', 'emotion', 'spechars', 'searchreplace', 'map', 'justifyleft','justifyright', 'justifycenter', 'justifyjustify', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'fullscreen', 'directionalityltr', 'directionalityrtl', 'pagebreak',  'imagecenter', 'lineheight', 'edittip ', 'background', 'inserttable', 'print', 'preview', 'help']";


}
