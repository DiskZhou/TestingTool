package com.appcenter.testingtool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by diskzhou on 13-6-13.
 */
public class FileUtils {

    public static String  MakePath(String dictory,String fileName){

        return (dictory.endsWith(File.separator) ?dictory  : dictory + File.separator)+fileName;

    }

    public static String CreateFolder(String folderPath){

            File folderFile = new File(folderPath);

            if (!folderFile.exists()) {
                folderFile.mkdirs();
            }
        return folderPath;
    }

    /**
     *
     * @param srcPath
     * @param destPath
     * @param fileType
     * @param countNumber
     */
    public static void doSelfCopy(String srcPath, String destPath, String fileType ,int countNumber){

        if (countNumber<=0) return;

        for (int i=0;i<countNumber;i++){
            CopyFiles(srcPath,destPath,CreateRandomFilesName(fileType));
        }

    }

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFilePath
     *            源文件File
     * @param destDirPath
     *            目标目录File
     * @param newFileName
     *            新文件名
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    public static long CopyFiles(String srcFilePath, String destDirPath, String newFileName) {
        long copySizes = 0;

        File srcFile = new File(srcFilePath);
        File destDir = new File(destDirPath);
        if (!srcFile.exists()) {
            System.out.println("源文件不存在");
            copySizes = -1;
        } else if (!destDir.exists()) {
            System.out.println("目标目录不存在");
            copySizes = -1;
        } else if (newFileName == null) {
            System.out.println("文件名为null");
            copySizes = -1;
        } else {
            try {
                FileChannel fcin = new FileInputStream(srcFile).getChannel();
                FileChannel fcout = new FileOutputStream(new File(destDir,
                        newFileName)).getChannel();
                long size = fcin.size();
                fcin.transferTo(0, fcin.size(), fcout);
                fcin.close();
                fcout.close();
                copySizes = size;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return copySizes;
    }

    /**
     *
     * @param in
     * @param destPath
     */
    public static void  CopyFiles(InputStream in, String destPath) {
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            FileOutputStream out = new FileOutputStream(destPath,true);
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static String CreateRandomFilesName(String fileType) {

        Random rd = new Random(System.currentTimeMillis());
        Date date = new Date();
        int number = rd.nextInt();
        String dateString = new SimpleDateFormat("yyyyMMddkkmmss").format(date);
        return String.format("%s_%s_%s.%s", ConstString.FILE_NAME, dateString, String.valueOf(number),fileType);
    }

    public static void RemoveAllFiles(String... folderPath){
        String path;
        for (String filePath:folderPath ){
            path = filePath.endsWith(File.separator) ? filePath : filePath + File.separator;
            File file = new File(path);

            if (!file.isDirectory()&&(!file.exists())) return;

            for (File files:file.listFiles()) {
                try{
                    files.delete();
                }catch (Exception e){

                }
            }

        }


    }

    public static File InputstreamToFile(InputStream ins,String filePath){
        OutputStream os = null;
        File file = new File(filePath);
        int bytesRead;
        try {
            os = new FileOutputStream(file);

            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ins!=null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        return file;
    }

    /***
     *
     * @param filePath
     * @param length
     * @param fileType
     */

    public static void  createDustFiles(String filePath,long length,String fileType) {

        if (length<=SpaceManager.getSDCardFreeSpaceSize()){
            RandomAccessFile randomAccessFile = null;
            File file = new  File(filePath,FileUtils.CreateRandomFilesName(fileType));

            try{
                randomAccessFile =  new RandomAccessFile(file, "rw");
               randomAccessFile.setLength(length*1024*1024);

               for(int i=1;i<=length;i++){
                  byte[] buf = new byte[1024*1024];
                   randomAccessFile.write(buf);
               }

            }catch (FileNotFoundException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (randomAccessFile!=null){
                        randomAccessFile.close();
                    }

                }catch (IOException io){
                    io.printStackTrace();
                }


            }


        }

    }
}
