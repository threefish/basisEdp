package com.sgaop.action;

import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.mvc.upload.TempFile;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.util.Base64Tool;
import com.sgaop.common.util.FileUtil;
import com.sgaop.common.util.RandomUtil;
import com.sgaop.entity.sys.FileAttach;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/5 0005
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/File")
public class BaseFileAction extends BaseAction {

    @Inject("java:attach.maxFileSize")
    private int maxFileSize;//单位M

    @Inject("dao")
    protected Dao dao;

    /**
     * 全局上传文件页面-通过open方式打开
     *
     * @param module
     */
    @Path("/page")
    @GET
    @OK("btl:tool.upload")
    public void attachPage(@Parameter("module") String module) {
        setAtrr("maxFileSize", maxFileSize * 1024 * 1024);
        setAtrr("maxFileSizeMsg", maxFileSize);
        setAtrr("module", module);
        setAtrr("url", "/File/FileUploadact");
        setAtrr("FILE_EXTENSIONS", Cons.FILE_EXTENSIONS);
    }


    /**
     * 单文件上传到指定URL-通过open方式打开
     */
    @Path("/singleUpload")
    @GET
    @OK("btl:tool.singleUpload")
    public void singleUpload(@Parameter("url") String url,
                             @Parameter("fileExtensions") String file_extensions,
                             @Parameter("module") String module,
                             @Parameter("fileType") String fileType
    ) {
        setAtrr("maxFileSize", maxFileSize * 1024 * 1024);
        setAtrr("maxFileSizeMsg", maxFileSize);
        setAtrr("url", url);
        setAtrr("module", module);
        setAtrr("FILE_EXTENSIONS", file_extensions == null ? Cons.FILE_EXTENSIONS : file_extensions);
        setAtrr("FILE_TYPE", (fileType == null && "file".equals(fileType)) ? "file" : "Images");
        setAtrr("MIME_TYPES", (fileType == null && "file".equals(fileType)) ? "*/*" : "image/*");
    }


    /**
     * 多 文件上传- 通过open方式打开
     *
     * @param url             上传地址
     * @param file_extensions 上传类型
     * @param fileType        上传类型 （是文件还是 图片）
     * @return
     */
    @Path("/multiUpload")
    @GET
    @OK("btl:tool.multiUpload")
    public void multiUpload(@Parameter("url") String url,
                            @Parameter("fileExtensions") String file_extensions,
                            @Parameter("module") String module,
                            @Parameter("fileType") String fileType) {
        setAtrr("maxFileSize", maxFileSize * 1024 * 1024);
        setAtrr("maxFileSizeMsg", maxFileSize);
        setAtrr("url", url);
        setAtrr("FILE_EXTENSIONS", file_extensions == null ? Cons.FILE_EXTENSIONS : file_extensions);
        setAtrr("FILE_TYPE", (fileType == null && "file".equals(fileType)) ? "file" : "Images");
        setAtrr("MIME_TYPES", (fileType == null && "file".equals(fileType)) ? "*/*" : "image/*");
    }


    /**
     * 全局上传封面图片文件页面-通过open方式打开
     *
     * @param module
     */
    @Path("/cutimg")
    @GET
    @OK("btl:tool.cutimg")
    public void cutimgindex(@Parameter("module") String module) {
        setAtrr("maxFileSize", maxFileSize * 1024 * 1024);
        setAtrr("maxFileSizeMsg", maxFileSize);
        setAtrr("module", module);
    }

    @POST
    @OK("json")
    @Path("/FileUploadact")
    public Result FileUploadact(@Parameter("file") TempFile tf, @Parameter("module") String module) {
        try {
            String parentPath = Mvcs.getServletContext().getRealPath("/");
            java.nio.file.Path newFile = FileUtil.getFilePath(tf.getName(), getUserAccount().getId());
            String filePath = newFile.toAbsolutePath().toString().replace(parentPath, "");
            InputStream in = tf.getInputStream();
            Files.copy(in, newFile, StandardCopyOption.REPLACE_EXISTING);


            String doc = filePath.substring(filePath.lastIndexOf("."));  // 得到 扩展名
            String newFilePath = filePath.substring(0, filePath.lastIndexOf("_"));  //新的文件名
            newFilePath += "_" + new java.util.Date().getTime() + doc;  // 加上扩展名

            //存数据库
            FileAttach fileAttach = new FileAttach();
            fileAttach.setAddtime(new Timestamp(new java.util.Date().getTime()));
            fileAttach.setAdduser(getUserAccount().getUserName());
            fileAttach.setAttachfilename(tf.getName());
            fileAttach.setSavedfilename(newFile.getFileName().toString());
            fileAttach.setAttachtype(module);
            fileAttach.setFilesize(tf.getSize());
            fileAttach.setSavedpath(newFilePath);
            in.close();
            // 复制文件
            FileUtil.readFile(parentPath + filePath, parentPath + newFilePath);
            int key = dao.insert(fileAttach);
            return Result.sucess(key);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    /**
     * 上传文件
     *
     * @param module
     * @param filedate
     */
    @Path(value = "/uploadBase64Act")
    @POST
    @OK("json")
    public Result uploadBase64File(@Parameter("module") String module, @Parameter("filedate") String filedate) {
        try {
            String oldName = RandomUtil.generateString(5) + ".jpg";
            java.nio.file.Path newFile = FileUtil.getFilePath(oldName, getUserAccount().getId());
            String filePath = newFile.toAbsolutePath().toString().replace(Mvcs.getServletContext().getRealPath("/"), "");
            String relPath = newFile.toAbsolutePath().toString();
            if (Base64Tool.GenerateImage(filedate, relPath)) {
                File f = new File(relPath);
                //存数据库
                FileAttach fileAttach = new FileAttach();
                fileAttach.setAddtime(new Timestamp(new java.util.Date().getTime()));
                fileAttach.setAdduser(getUserAccount().getUserName());
                fileAttach.setAttachfilename(oldName);
                fileAttach.setSavedfilename(newFile.getFileName().toString());
                fileAttach.setAttachtype(module);
                fileAttach.setFilesize(f.length());
                fileAttach.setSavedpath(filePath);
                int id = dao.insert(fileAttach);
                return Result.sucess(id);
            } else {
                return Result.error("图片转换错误");
            }
        } catch (Exception e) {
            return Result.error("文件格式错误");
        }
    }
}
