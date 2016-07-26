package com.dx168.efsmobile.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 判断文件格式是否为视频/音频
 *
 * @author chenwansong
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    private static final int BUFFEREDSIZE = 1024;

    // bt字节参考量
    public static final long SIZE_BT = 1024L;
    // KB字节参考量
    public static final long SIZE_KB = SIZE_BT * 1024L;
    // MB字节参考量
    public static final long SIZE_MB = SIZE_KB * 1024L;
    // GB字节参考量
    public static final long SIZE_GB = SIZE_MB * 1024L;
    // TB字节参考量
    public static final long SIZE_TB = SIZE_GB * 1024L;
    public static final int SACLE = 2;

    // the media format support built into the Android platform.
    // http://developer.android.com/guide/appendix/media-formats.html

    /**
     * 获取文件后缀
     */
    public static String getFileExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    /**
     * 获取文件名后缀（带.）
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if (!TextUtils.isEmpty(fileName) && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 获取带单位的文件大小
     *
     * @param file
     * @return
     */
    public static String getFileSizeWithUnit(File file) {
        long size = getFileSize(file);
        return getFileSizeWithUnit(size);
    }

    /**
     * 获取带单位的文件大小
     *
     * @param size
     * @return
     */
    public static String getFileSizeWithUnit(long size) {
        if (size >= 0 && size < SIZE_BT) {
            return size + "B";
        } else if (size >= SIZE_BT && size < SIZE_KB) {
            BigDecimal decimal = new BigDecimal(size / Double.valueOf(SIZE_BT)).setScale(SACLE,
                    BigDecimal.ROUND_HALF_UP);
            return decimal + "KB";
        } else if (size >= SIZE_KB && size < SIZE_MB) {
            BigDecimal decimal = new BigDecimal(size / Double.valueOf(SIZE_KB)).setScale(SACLE,
                    BigDecimal.ROUND_HALF_UP);
            return decimal + "MB";
        } else if (size >= SIZE_MB && size < SIZE_GB) {
            BigDecimal longs = new BigDecimal(Double.valueOf(size).toString());
            BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_MB).toString());
            String result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString();
            return result + "GB";
        } else {
            BigDecimal longs = new BigDecimal(Double.valueOf(size).toString());
            BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_GB).toString());
            String result = longs.divide(sizeMB, SACLE, BigDecimal.ROUND_HALF_UP).toString();
            return result + "TB";
        }
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = -1;
        if (file != null) {
            // 如果文件存在而且是文件，直接返回文件大小
            if (file.exists() && file.isFile()) {
                size += file.length();
                // 文件存在而且是目录，递归遍历文件目录计算文件大小
            } else if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                for (File temp : files) {
                    size += getFileSize(temp); // 如果遇到目录则通过递归调用继续统计
                }
            }
        }
        return size;
    }

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported
     *                    {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static String readFile(String filePath, String charsetName) {
        Log.i(TAG, "File   readFile     " + filePath);
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append   is append, if true, write to the end of file, else clear
     *                 content of file and write into it
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        Log.d(TAG, "File   writeFile     " + filePath);

        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            /*
             * File file = new File(filePath); File to = new
			 * File(file.getAbsolutePath()); file.renameTo(to); to.delete();
			 */
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param contentList
     * @param append      is append, if true, write to the end of file, else clear
     *                    content of file and write into it
     * @return return false if contentList is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
        // if (ListUtils.isEmpty(contentList)) {
        // return false;
        // }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
//			throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
//					throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * write file, the string list will be written to the begin of the file
     *
     * @param filePath
     * @param contentList
     * @return
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if <code>true</code>, then bytes will be written to the end of
     *                 the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(File, InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of
     *               the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//			throw new RuntimeException("FileNotFoundException occurred. ", e);
            return false;
        } catch (IOException e) {
//			throw new RuntimeException("IOException occurred. ", e);
            e.printStackTrace();
            return false;
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
//					throw new RuntimeException("IOException occurred. ", e);
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    /**
     * copy file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported
     *                    {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * get file name from path, not include suffix
     * <p>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     * <p>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     * <p>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     * <p>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file,
     * including the complete directory path required to create this directory.
     * <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target
     * directory already exists, false one of the directories can not be
     * created.
     * <ul>
     * <li>if {@link FileUtils#getFolderName(String)} return null,
     * return false</li>
     * <li>if target directory already exists, return true</li>
     * <li>return {@link }</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file
     * system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        Log.i(TAG, "**********  deleteFile   " + path);
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);

        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return deleteFileRN(file);
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                // f.delete();
                deleteFileRN(f);
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public static boolean deleteFileWithoutDir(String path) {
        Log.d(TAG, "**********  deleteFileWithoutDir   " + path);
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);

        if (!file.exists()) {
            return true;
        }
        if (file.isFile() && !file.isDirectory()) {
            return deleteFileRN(file);
        }

        for (File f : file.listFiles()) {
            if (f.isFile() && !f.isDirectory()) {
                deleteFileRN(f);
            }
        }
        if (!file.isDirectory()) {
            return deleteFileRN(file);
        } else {
            return false;
        }

    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file
     * does not exist.
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }

        File file = new File(path);
        return getFileSize(file);
    }

    /**
     * 创建指定大小的文件
     *
     * @param file       文件
     * @param fileLength 文件大小
     * @return
     */
    public static boolean createFixLengthFile(File file, long fileLength) {
        if (null != file && file.exists()) {
            return true;
        }
        long start = System.currentTimeMillis();
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {

            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            channel = fos.getChannel();
            channel.write(ByteBuffer.allocateDirect(1), fileLength - 1);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "" + (System.currentTimeMillis() - start));
        }
        return false;
    }

    public static void unzip(Context context, String zipFilename, String outputDirectory) throws IOException {
        File outFile = new File(outputDirectory);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        File zipfile = new File(zipFilename);
        ZipFile zipFile = new ZipFile(zipFilename);
        @SuppressWarnings("rawtypes")
        Enumeration en = zipFile.entries();
        ZipEntry zipEntry = null;
        while (en.hasMoreElements()) {
            zipEntry = (ZipEntry) en.nextElement();
            if (zipEntry.isDirectory()) {
                String dirName = zipEntry.getName();
                dirName = dirName.substring(0, dirName.length() - 1);
                File f = new File(outFile.getPath() + File.separator + dirName);
                f.mkdirs();
            } else {
                String strFilePath = outFile.getPath() + File.separator + zipEntry.getName();
                File f = new File(strFilePath);
                if (!f.exists()) {
                    String[] arrFolderName = zipEntry.getName().split("/");
                    String strRealFolder = "";
                    for (int i = 0; i < (arrFolderName.length - 1); i++) {
                        strRealFolder += arrFolderName[i] + File.separator;
                    }
                    strRealFolder = outFile.getPath() + File.separator + strRealFolder;
                    File tempDir = new File(strRealFolder);
                    tempDir.mkdirs();
                }

                InputStream in = zipFile.getInputStream(zipEntry);
                FileOutputStream out = new FileOutputStream(f);
                try {
                    int c;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    // out.flush();
                } catch (IOException e) {
                    throw e;
                } finally {
                    out.close();
                    in.close();

                }
            }
        }
        zipFile.close();
        zipfile.delete();
    }

    public static ArrayList<String> ConfigFileList(String filePath) {
        ArrayList<String> vecFile = new ArrayList<String>();
        File file = new File(filePath);
        File[] subFileList = file.listFiles();
        if (null == subFileList) {
            return vecFile;
        }
        for (int i = 0; i < subFileList.length; i++) {
            File[] configFiles = subFileList[i].listFiles();
            if (configFiles == null)
                return vecFile;
            for (int j = 0; j < configFiles.length; j++) {
                // 判断是否为文件夹
                if (!configFiles[j].isDirectory()) {
                    String fileName = configFiles[j].getName();
                    // 判断是否为.txt结尾
                    if (fileName.trim().toLowerCase().endsWith("config.txt")) {
                        vecFile.add(configFiles[j].getPath());
                    }
                }
            }

        }
        return vecFile;
    }

    public static ArrayList<String> BookFileList(String filePath) {
        ArrayList<String> vecFile = new ArrayList<String>();
        File file = new File(filePath);
        File[] subFileList = file.listFiles();
        if (null == subFileList) {
            return vecFile;
        }
        for (int i = 0; i < subFileList.length; i++) {
            vecFile.add(subFileList[i].getPath());
        }
        return vecFile;
    }

    public static Boolean deleteFileRN(File file_rn) {
        File file = new File(file_rn.getAbsolutePath() + System.currentTimeMillis());
        file_rn.renameTo(file);

        return file.delete();
    }

    private static int CHANNELS = 1;
    private static int BITS = 16;
    private static int FREQUENCY = 16000; // 抽样率

    // 写入数据
    public static void fwrite(RandomAccessFile file, byte[] data, int offset, int size) throws IOException {
        file.write(data, offset, size);
        Log.i(TAG, "写入数据");
    }

    // 关闭文件
    public static void fclose(RandomAccessFile file) throws IOException {
        try {
            file.seek(4);
            file.writeInt(Integer.reverseBytes((int) (file.length() - 8)));
            file.seek(40); // data chunk size
            file.writeInt(Integer.reverseBytes((int) (file.length() - 44)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "关闭文件");
            file.close();
        }
    }

    public static void createFile(String path) {
        File f = new File(path);
        // 判断文件是否存在
        if (f.exists()) {
            f.delete();
        } else {
            File parentDir = f.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        }
    }

    // 打开文件
    public static RandomAccessFile fopen(String path) throws IOException {
        File f = new File(path);
        // 判断文件是否存在
        if (f.exists()) {
            f.delete();
        } else {
            File parentDir = f.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        }

        RandomAccessFile file = new RandomAccessFile(f, "rw");

		/* RIFF header */
        file.writeBytes("RIFF"); // riff id
        file.writeInt(0); // riff chunk size *PLACEHOLDER*
        file.writeBytes("WAVE"); // wave type

		/* fmt chunk */
        file.writeBytes("fmt "); // fmt id
        file.writeInt(Integer.reverseBytes(16)); // fmt chunk size
        file.writeShort(Short.reverseBytes((short) 1)); // format: 1(PCM)
        file.writeShort(Short.reverseBytes((short) CHANNELS)); // channels: 1
        file.writeInt(Integer.reverseBytes(FREQUENCY)); // samples per second
        file.writeInt(Integer.reverseBytes((int) (CHANNELS * FREQUENCY * BITS / 8))); // BPSecond
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS / 8))); // BPSample
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS))); // bPSample

		/* data chunk */
        file.writeBytes("data"); // data id
        file.writeInt(0); // data chunk size *PLACEHOLDER*
        Log.i(TAG, "打开文件");
        return file;

    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 写入本地文件
     *
     * @param context
     * @param msg
     * @param fileName
     */

    public static void write(Context context, String msg, String fileName) {
        if (msg == null)
            return;
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(msg.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream inStream = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }
            inStream.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory, String fileName) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (fileName.equals(item.getName())) {
                    item.delete();
                }
            }
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        createFile(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String[] getDirFileName(String path) {
        File file = new File(path);
        String[] fileName = file.list();
        return fileName;
    }


}
