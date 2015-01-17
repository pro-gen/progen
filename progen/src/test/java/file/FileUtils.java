package file;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

  private static String suffixTest = ".test";

  public static void setSuffixTest(String suffixTest) {
    FileUtils.suffixTest = suffixTest;
  }

  public static void cleanDir(File dir, String endPattern) {
    for (String fileName : dir.getParentFile().list()) {
      if (fileName.endsWith(endPattern)) {
        File fileToDelete = new File(dir.getParentFile() + File.separator + fileName);
        fileToDelete.renameTo(new File(fileToDelete.getAbsolutePath().replaceAll(endPattern, "-" + suffixTest)));
      }
    }
  }

  public static String sha1sum(InputStream file) {
    MessageDigest digest = calculateSHA1(file);
    return digestToString(digest);
  }

  private static String digestToString(MessageDigest digest) {
    byte[] mdbytes = digest.digest();
    StringBuilder sb = new StringBuilder("");
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  private static MessageDigest calculateSHA1(InputStream inputStream) {
    MessageDigest digest = getSHA1();
    byte[] dataBytes = new byte[1024];
    StringBuilder output = new StringBuilder();
    try {
      int nread = 0;
      while ((nread = inputStream.read(dataBytes)) != -1) {
        output.append(new String(dataBytes));
        digest.update(dataBytes, 0, nread);
      }
      System.out.println("\n------ example ----\n"+output.toString()+"\n---- end of example ----");
    } catch (NullPointerException e) {
      fail("File not found");
    } catch (IOException e) {
      fail(e.getMessage());
    }
    return digest;
  }

  private static MessageDigest getSHA1() {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException e) {
      fail("No such algorithmException thrown " + e.getMessage());
    }
    return digest;
  }

}
