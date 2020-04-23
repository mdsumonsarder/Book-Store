package com.bookstore.mahir.utils;

import android.os.Environment;
import com.bookstore.mahir.model.FileInfo;
import com.google.android.gms.common.util.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.text.TextUtils;

public class CommonFileUtils {

  public final static String FILE_EXTENSION_SEPARATOR = ".";

  private CommonFileUtils() {
    throw new AssertionError();
  }


  public static List<File> getFiles(String dir, final String matchRegex) {
    File file = new File(dir);
    File[] files = null;
    if (matchRegex != null) {
      FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String fileName) {
          return fileName.matches(matchRegex);
        }
      };
      files = file.listFiles(filter);
    } else {
      files = file.listFiles();
    }
    return files != null ? Arrays.asList(files) : null;
  }



  public static StringBuilder readFile(String filePath, String charsetName) {
    File file = new File(filePath);
    StringBuilder fileContent = new StringBuilder("");
    if (file == null || !file.isFile()) {
      return null;
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
      return fileContent;
    } catch (IOException e) {
      throw new RuntimeException("IOException occurred. ", e);
    }
  }


  public static boolean writeFile(String filePath, InputStream stream) {
    return writeFile(filePath, stream, false);
  }


  public static boolean writeFile(String filePath, InputStream stream, boolean append) {
    return writeFile(filePath != null ? new File(filePath) : null, stream, append);
  }


  public static boolean writeFile(File file, InputStream stream) {
    return writeFile(file, stream, false);
  }


  public static boolean writeFile(File file, InputStream stream, boolean append) {
    OutputStream o = null;
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
      throw new RuntimeException("FileNotFoundException occurred. ", e);
    } catch (IOException e) {
      throw new RuntimeException("IOException occurred. ", e);
    }
  }


  public static void moveFile(String sourceFilePath, String destFilePath) {
    if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
      throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
    }
    moveFile(new File(sourceFilePath), new File(destFilePath));
  }


  public static void moveFile(File srcFile, File destFile) {
    boolean rename = srcFile.renameTo(destFile);
    if (!rename) {
      copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
      deleteFile(srcFile.getAbsolutePath());
    }
  }


  public static boolean copyFile(String sourceFilePath, String destFilePath) {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(sourceFilePath);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("FileNotFoundException occurred. ", e);
    }
    return writeFile(destFilePath, inputStream);
  }


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
      return fileContent;
    } catch (IOException e) {
      throw new RuntimeException("IOException occurred. ", e);
    }
  }


  public static String getFileNameWithoutExtension(String filePath) {
    if (StringUtils.isEmpty(filePath)) {
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


  public static String getFileName(String filePath) {
    if (StringUtils.isEmpty(filePath)) {
      return filePath;
    }

    int filePosi = filePath.lastIndexOf(File.separator);
    return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
  }


  public static String getFolderName(String filePath) {

    if (StringUtils.isEmpty(filePath)) {
      return filePath;
    }

    int filePosi = filePath.lastIndexOf(File.separator);
    return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
  }


  public static String getFileExtension(String filePath) {
    if (StringUtils.isBlank(filePath)) {
      return filePath;
    }

    int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
    int filePosi = filePath.lastIndexOf(File.separator);
    if (extenPosi == -1) {
      return "";
    }
    return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
  }


  public static boolean makeDirs(String filePath) {
    String folderName = getFolderName(filePath);
    if (StringUtils.isEmpty(folderName)) {
      return false;
    }

    File folder = new File(folderName);
    return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
  }


  public static boolean makeFolders(String filePath) {
    return makeDirs(filePath);
  }


  public static boolean isFileExist(String filePath) {
    if (StringUtils.isBlank(filePath)) {
      return false;
    }

    File file = new File(filePath);
    return (file.exists() && file.isFile());
  }


  public static boolean isFolderExist(String directoryPath) {
    if (StringUtils.isBlank(directoryPath)) {
      return false;
    }

    File dire = new File(directoryPath);
    return (dire.exists() && dire.isDirectory());
  }


  public static boolean deleteFile(String path) {
    if (StringUtils.isBlank(path)) {
      return true;
    }

    File file = new File(path);
    if (!file.exists()) {
      return true;
    }
    if (file.isFile()) {
      return file.delete();
    }
    if (!file.isDirectory()) {
      return false;
    }
    for (File f : file.listFiles()) {
      if (f.isFile()) {
        f.delete();
      } else if (f.isDirectory()) {
        deleteFile(f.getAbsolutePath());
      }
    }
    return file.delete();
  }


  public static long getFileSize(String path) {
    if (StringUtils.isBlank(path)) {
      return -1;
    }

    File file = new File(path);
    return (file.exists() && file.isFile() ? file.length() : -1);
  }


  public static List<FileInfo> fileList()
  {
    File root = new File(Environment.getExternalStorageDirectory() + "/pdf");
    File[] fileArray = root.listFiles();

    if (fileArray != null)
    {
      List<File> files = Arrays.asList(fileArray);

      Collections.sort(files, (lhs, rhs) -> {
        if (lhs.isDirectory() && !rhs.isDirectory())
        {
          return -1;
        }
        else if (!lhs.isDirectory() && rhs.isDirectory())
        {
          return 1;
        }
        else
        {
          return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
      });

      List<FileInfo> result = new ArrayList<>();

      for (File file : files)
      {
        if (file != null)
        {
          result.add(new FileInfo(file));
        }
      }

      return result;
    }
    else
    {
      return new ArrayList<>();
    }
  }

  public static File GetFile(String path)
  {
    File root = new File(Environment.getExternalStorageDirectory() + "/pdf");
    File[] fileArray = root.listFiles();

    if (fileArray != null)
    {
      List<File> files = Arrays.asList(fileArray);

      Collections.sort(files, (lhs, rhs) -> {
        if (lhs.isDirectory() && !rhs.isDirectory())
        {
          return -1;
        }
        else if (!lhs.isDirectory() && rhs.isDirectory())
        {
          return 1;
        }
        else
        {
          return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
      });

      File result = null;

      for (File file : files)
      {
        if (file.getName().equals(path))
        {
          result = file;
        }
      }

      return result;
    }
    else
    {
      return null;
    }
  }
}