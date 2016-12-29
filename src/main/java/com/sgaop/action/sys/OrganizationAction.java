package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.entity.sys.Organization;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysOrg")
@RequiresRoles("admin")
public class OrganizationAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.org.index")
    @GET
    @Path("/index")
    public void index() {
    }


    @OK("json:{ignoreNull:false,locked:'createTime|updateTime|createUserid|updateUserid'}")
    @POST
    @Path("/tree")
    public List<Organization> tree() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<Organization> organizations = dao.query(Organization.class, condition);
        Organization org = new Organization();
        org.setId(0);
        org.setPid(0);
        org.setName("根节点");
        org.setLocked(false);
        org.setDescription("");
        organizations.add(org);
        return organizations;
    }


    @OK("json")
    @POST
    @Path("/update")
    public Result update(@Parameter("data>>") Organization org) {
        if (org.getPid() != 0 && org.getId() == org.getPid()) {
            return Result.error("不能选择自己作为自己的上级单位");
        }
        if (StringsTool.isNullorEmpty(org.getName())) {
            return Result.error("组织机构名称不能为空！");
        }
        Condition condition = new Condition();
        condition.and("name", "=", org.getName());
        condition.and("id", "!=", org.getId());
        Organization organization1 = dao.fetch(Organization.class, condition);
        if (organization1 != null) {
            return Result.error("组织机构名称已经存在，不能重复！");
        }
        Organization organization = dao.fetch(Organization.class, org.getId());
        organization.setName(org.getName());
        organization.setLocked(org.isLocked());
        organization.setPid(org.getPid());
        organization.setShortName(org.getShortName());
        organization.setOrgIcon(org.getOrgIcon());
        organization.setDescription(org.getDescription());
        organization.setUpdateTime(new Timestamp(new Date().getTime()));
        try {
            dao.update(organization);
            return Result.sucess(org, "修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/move")
    public Result move(@Parameter("id") int id, @Parameter("type") String type) {
        if (!StringsTool.isNullorEmpty(type)) {
            Organization uOrganization = dao.fetch(Organization.class, id);
            //取出同级组织机构
            Condition cnd = new Condition();
            cnd.and("pid", "=", uOrganization.getPid());
            cnd.asc("short_no");
            List<Organization> menuList = dao.query(Organization.class, cnd);
            //重新整理顺序
            List<Organization> oldMenuList = new ArrayList<>();
            for (int i = 0; i < menuList.size(); i++) {
                Organization menu = menuList.get(i);
                menu.setShortNo(i);
                oldMenuList.add(menu);
            }
            //上移
            if ("up".equals(type)) {
                //升级后的组织机构
                List<Organization> upOrgList = new ArrayList<>();
                for (Organization org : oldMenuList) {
                    if (org.getId() == id) {
                        if (org.getShortNo() == 0) {
                            return Result.error("已经是置顶了！");
                        } else {
                            org.setShortNo(org.getShortNo() - 1);
                        }
                    }
                    upOrgList.add(org);
                }
                Collections.sort(upOrgList, new Organization());
                //重新整理顺序
                List<Organization> newMenuList = new ArrayList<>();
                for (int i = 0; i < upOrgList.size(); i++) {
                    Organization menu = upOrgList.get(i);
                    menu.setShortNo(i);
                    newMenuList.add(menu);
                }
                dao.update(newMenuList);
            } else {//下移
                //降级级后的组织机构
                List<Organization> upMenuList = new ArrayList<>();
                int last = 1;
                for (Organization org : oldMenuList) {
                    if (org.getId() == id) {
                        if (last == oldMenuList.size()) {
                            return Result.error("已经是置底了！");
                        } else {
                            org.setShortNo(org.getShortNo() + 1);
                        }
                    }
                    last++;
                    upMenuList.add(org);
                }
                Collections.sort(upMenuList, new Organization());
                //重新整理顺序
                List<Organization> newMenuList = new ArrayList<>();
                for (int i = 0; i < upMenuList.size(); i++) {
                    Organization org = upMenuList.get(i);
                    org.setShortNo(i);
                    newMenuList.add(org);
                }
                dao.update(newMenuList);
            }
        }
        return Result.sucess("修改成功");
    }

    @OK("json")
    @POST
    @Path("/add")
    public Result add(@Parameter("data>>") Organization org) {
        try {
            if (StringsTool.isNullorEmpty(org.getName())) {
                return Result.error("组织机构名称不能为空！");
            }
            Organization organization = dao.fetch(Organization.class, "name", org.getName());
            if (organization != null) {
                return Result.error("组织机构名称已经存在，不能重复！");
            }
            org.setCreateTime(new Timestamp(new Date().getTime()));
            org.setCreateUserid(getUserAccount().getId());
            int id = dao.insert(org);
            org.setId(id);
            if (id > 0) {
                return Result.sucess(org, "添加成功");
            } else {
                return Result.error("未知原因");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/del")
    public Result del(@Parameter("data>>") Organization org) {
        try {
            Organization organization = dao.fetch(Organization.class, org.getId());
            boolean flag = dao.delete(organization);
            return Result.sucess(organization, flag ? "删除成功" : "删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
