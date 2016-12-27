package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.entity.sys.Department;

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
@Action("/sysDept")
public class DepartmentAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.dept.index")
    @GET
    @Path("/index")
    public void index() {
    }


    @OK("json:{ignoreNull:false,locked:'createTime|updateTime|createUserid|updateUserid'}")
    @POST
    @Path("/tree")
    public List<Department> tree() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<Department> organizations = dao.query(Department.class, condition);
        Department org = new Department();
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
    public Result update(@Parameter("data>>") Department dept) {
        if (dept.getPid() != 0 && dept.getId() == dept.getPid()) {
            return Result.error("不能选择自己作为自己的上级单位");
        }
        if (StringsTool.isNullorEmpty(dept.getName())) {
            return Result.error("部门名称不能为空！");
        }
        Condition condition=new Condition();
        condition.and("name","=", dept.getName());
        condition.and("id","!=", dept.getId());
        Department department = dao.fetch(Department.class, condition);
        if (department != null) {
            return Result.error("部门名称已经存在，不能重复！");
        }
        Department organization = dao.fetch(Department.class, dept.getId());
        organization.setName(dept.getName());
        organization.setLocked(dept.isLocked());
        organization.setPid(dept.getPid());
        organization.setShortName(dept.getShortName());
        organization.setDeptIcon(dept.getDeptIcon());
        organization.setDescription(dept.getDescription());
        organization.setUpdateTime(new Timestamp(new Date().getTime()));
        try {
            dao.update(organization);
            return Result.sucess(dept, "修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/move")
    public Result move(@Parameter("id") int id, @Parameter("type") String type) {
        if (!StringsTool.isNullorEmpty(type)) {
            Department uDepartment = dao.fetch(Department.class, id);
            //取出同级组织机构
            Condition cnd = new Condition();
            cnd.and("pid", "=", uDepartment.getPid());
            cnd.asc("short_no");
            List<Department> menuList = dao.query(Department.class, cnd);
            //重新整理顺序
            List<Department> oldMenuList = new ArrayList<>();
            for (int i = 0; i < menuList.size(); i++) {
                Department menu = menuList.get(i);
                menu.setShortNo(i);
                oldMenuList.add(menu);
            }
            //上移
            if ("up".equals(type)) {
                //升级后的组织机构
                List<Department> upOrgList = new ArrayList<>();
                for (Department org : oldMenuList) {
                    if (org.getId() == id) {
                        if (org.getShortNo() == 0) {
                            return Result.error("已经是置顶了！");
                        } else {
                            org.setShortNo(org.getShortNo() - 1);
                        }
                    }
                    upOrgList.add(org);
                }
                Collections.sort(upOrgList, new Department());
                //重新整理顺序
                List<Department> newMenuList = new ArrayList<>();
                for (int i = 0; i < upOrgList.size(); i++) {
                    Department menu = upOrgList.get(i);
                    menu.setShortNo(i);
                    newMenuList.add(menu);
                }
                dao.update(newMenuList);
            } else {//下移
                //降级级后的组织机构
                List<Department> upMenuList = new ArrayList<>();
                int last = 1;
                for (Department org : oldMenuList) {
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
                Collections.sort(upMenuList, new Department());
                //重新整理顺序
                List<Department> newMenuList = new ArrayList<>();
                for (int i = 0; i < upMenuList.size(); i++) {
                    Department org = upMenuList.get(i);
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
    public Result add(@Parameter("data>>") Department dept) {
        try {
            if (StringsTool.isNullorEmpty(dept.getName())) {
                return Result.error("部门名称不能为空！");
            }
            Department department = dao.fetch(Department.class, "name", dept.getName());
            if (department != null) {
                return Result.error("部门名称已经存在，不能重复！");
            }
            dept.setCreateTime(new Timestamp(new Date().getTime()));
            dept.setCreateUserid(getUserAccount().getId());
            int id = dao.insert(dept);
            dept.setId(id);
            if (id > 0) {
                return Result.sucess(dept, "添加成功");
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
    public Result del(@Parameter("data>>") Department org) {
        try {
            Department organization = dao.fetch(Department.class, org.getId());
            boolean flag = dao.delete(organization);
            return Result.sucess(organization, flag ? "删除成功" : "删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
