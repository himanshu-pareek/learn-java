import java.util.*;

public class LabelsBundle_en extends ListResourceBundle {

  @Override
  public Object[][] getContents() {
    return this.contents;
  }

  private Object[][] contents = {
    { "s1", new Integer(21300) },
    { "s2", new Double (2.3) },
    { "s3", new Boolean (false) },
    { "s4", "Four" },
  };
}

