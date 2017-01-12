package com.sgaop.common.util;

import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.cons.Cons;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chuan on 2015-11-09.
 */
public class FileUtil {
    /**
     * 获取安全路径
     *
     * @param filePath
     * @return
     */
    public static String getSafePath(String filePath) {
        filePath = filePath.replace("../", "");
        return filePath;
    }


    /**
     * 根据文件名创建唯一文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Path getFilePath(boolean isPublic,String fileName, int userid) throws IOException {
        Date date = new Date();
        Path newFile = Paths.get(Mvcs.getServletContext().getRealPath("/"),
                (isPublic?"publiceupload": "privateupload"),
                "web",
                String.valueOf(userid),
                DateUtil.getFullYear(date),
                DateUtil.getMonth(date) + DateUtil.getDay(date),
                DateUtil.getHour(date) + DateUtil.getMinute(date),
                RandomUtil.generateString(10) + "_" + fileName);
        //判断文件夹是否存在
        if (!newFile.getParent().toFile().exists()) {
            Files.createDirectories(newFile.getParent());
        }
        do {
            //随机一个新文件名字
            newFile = Paths.get(newFile.getParent().toString() + File.separator + RandomUtil.generateString(10) + "_" + fileName);
        } while (newFile.toFile().exists());//如果文件存在
        Files.createFile(newFile);
        return newFile;
    }


    /**
     * 创建APK文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Path createAppFilePath(String fileName) throws IOException {
        Path newFile = Paths.get(Mvcs.getServletContext().getRealPath("/"),
                "upload",
                "apk",
                RandomUtil.generateString(10) + "_" + fileName);
        //判断文件夹是否存在
        if (!newFile.getParent().toFile().exists()) {
            Files.createDirectories(newFile.getParent());
        }
        do {
            //随机一个新文件名字
            newFile = Paths.get(newFile.getParent().toString() + File.separator + RandomUtil.generateString(10) + "_" + fileName);
        } while (newFile.toFile().exists());//如果文件存在
        Files.createFile(newFile);
        return newFile;
    }


    /**
     * i/o进行读取文件
     *
     * @return fileContent读出的内容
     */
    public static String readFile(String filePath) {
        String fileContent = "";
        try {
            File f = new File(filePath);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(f), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line + "\n";
                }
                reader.close();
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * 读取文件夹
     *
     * @param folderPath 　文件夹路径
     * @param list       <Map> 返回包括文件名称与文件路径 Map返回结构 name:文件名称 path;文件路径
     * @return
     */
    public static List readFolder(List<Map<String, String>> list,
                                  String folderPath) {
        if (list == null) {
            return null;
        }
        File file = new File(folderPath);
        File[] tempList = file.listFiles();
        if (tempList != null && tempList.length > 0) {
            for (File tmpFile : tempList) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", file.getName());
                map.put("path", file.getPath());
                list.add(map);
                if (tmpFile.isDirectory()) {
                    readFolder(list, tmpFile.getPath()); // 递归删除
                }
            }
        }
        return list;
    }

    /**
     * i/o写入文件
     *
     * @param content   写入文件内容
     * @param writePath 要写入的文件名路径
     */
    public static void writeFile(String content, String writePath,
                                 String charCoder) {
        try {
            File file = new File(writePath);
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file), charCoder);
            BufferedWriter reader = new BufferedWriter(osw);
            reader.write(content);
            osw.flush();
            reader.close();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建文件夹
     *
     * @param path 　路径
     */
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除指定文件夹 如果文件夹里面存在文件夹就进行递归，删除规则是从里面开始先删除
     *
     * @param folderPath 文件夹路径
     */
    public static void delFolders(String folderPath) {
        // 删除完里面所有内容
        File file = new File(folderPath);
        // 如果路径本身就是一个文件就直接删除
        if (!file.isDirectory()) {
            file.delete();
            return;
        }
        // 检查文件夹里面是否存在文件夹
        File[] tempList = file.listFiles();
        if (tempList != null && tempList.length > 0) {
            for (File tmpFile : tempList) {
                if (tmpFile.isDirectory()) {
                    delFolders(tmpFile.getPath()); // 递归删除
                } else {
                    tmpFile.delete();
                }
            }
        } else {
            file.delete();
        }
        delFolders(file.getPath());
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return
     */
    public static void delFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 以当前程序目录为根目录写临时文件
     *
     * @param fileName
     * @param content
     */
    public static void writeTmpFile(String fileName, String content) {
        // 获取程序当前路径
        String strDir = System.getProperty("user.dir");
        // 将路径分隔符更换
        String folderpath = strDir;// .replace("/", File.separatorChar);
        String filepath = folderpath + File.separatorChar + fileName + ".tmp";
        FileUtil.writeFile(content, filepath, Cons.UTF8);
    }

    /**
     * 以当前程序目录为根目录读取临时文件
     *
     * @param fileName
     * @return
     */
    public static String readTmpFile(String fileName) {
        // 获取程序当前路径
        String strDir = System.getProperty("user.dir");
        // 将路径分隔符更换
        String folderpath = strDir;// .replace("/", File.separatorChar);
        String filepath = folderpath + File.separatorChar + fileName + ".tmp";
        return FileUtil.readFile(filepath);
    }

    /**
     * 从输入流中读取内容
     *
     * @param is InputStream 输入流对象
     * @return String
     * @throws Exception
     */
    public String readFromIS(InputStream is) throws Exception {
        try {
            String strRtn = "";
            int length = is.available();
            byte[] buf = new byte[length];
            while ((is.read(buf, 0, length)) != -1) {
                strRtn = strRtn + new String(buf, 0, length, Cons.UTF8);
            }
            return strRtn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            is.close();
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFilePrefix(String fileName) {
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return prefix;
    }

    /**
     * 读文件到字节数组中
     *
     * @param file
     * @throws Exception
     */
    public static byte[] fileToByte(File file) {
        FileInputStream is = null;
        try {
            byte[] dist = null;
            if (file.exists()) {
                is = new FileInputStream(file);
                dist = new byte[is.available()];
                is.read(dist);
            }
            return dist;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 子节数组 写入文件
     *
     * @param
     * @throws Exception
     */
    public static boolean writeFileToByte(String path, byte[] bytes) {
        FileOutputStream out = null;
        File file = new File(path);
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件到字节数组中
     *
     * @param filePath
     * @throws Exception
     */
    public static byte[] fileToByte1(String filePath) {
        FileInputStream is = null;
        try {
            File file = new File(filePath);
            byte[] dist = null;
            if (file.exists()) {
                is = new FileInputStream(file);
                dist = new byte[is.available()];
                is.read(dist);
            }
            return dist;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 压缩文件
     *
     * @param srcfile File[] 需要压缩的文件列表
     * @param zipfile File 压缩后的文件
     * @author
     */
    public static boolean zipFiles(List<File> srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            out.setEncoding(Cons.UTF8);
            for (int i = 0; i < srcfile.size(); i++) {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制 文件
     * 把原来 的 中文 名称的 文件 删除掉
     *
     * @param pathFile
     * @param newFilePath
     */
    public static void readFile(String pathFile, String newFilePath) {
        File file = new File(pathFile);
        if (file.exists()) {
            byte[] by = FileUtil.fileToByte(file);
            if (by != null && by.length > 0) {
                FileUtil.delFile(pathFile);
                FileUtil.writeFileToByte(newFilePath, by);
            }

        }
    }

    /**
     * 关闭流
     */
    public static void closeStream(InputStream is, OutputStream os) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
