package progen.experimenter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author jirsis
 * @since 2.0
 */
public class OutputFilenameFilter implements FilenameFilter {

  @Override
  public boolean accept(File dir, String name) {
    return name.startsWith("output");
  }

}
