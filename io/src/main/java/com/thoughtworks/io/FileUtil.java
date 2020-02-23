package com.thoughtworks.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

  /**
   * 完成复制文件夹方法:
   * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
   * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
   * <p>
   * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
   */
  public static void copyDirectory(File from, File to) throws IOException {

    preActionForToDirectory(to);

    File[] fromFiles = from.listFiles();
    if (fromFiles != null) {
      for (File file : fromFiles) {
        File targetFile = new File(to + File.separator + file.getName());
        if (file.isDirectory()) {
          targetFile.mkdir();
          copyDirectory(file, targetFile);
        } else {
          Files.copy(file.toPath(), targetFile.toPath());
        }
      }
    }
  }

  private static void preActionForToDirectory(File directory) {
    if (!directory.exists()) {
      directory.mkdir();
    }
    if (directory.listFiles() != null) {
      clearFolder(directory);
    }
  }

  private static void clearFolder(File to) {
    File[] files = to.listFiles();
    assert files != null;
    for (File file : files) {
      if (file.isDirectory()) {
        clearFolder(file);
      }
      file.delete();
    }
  }
}
