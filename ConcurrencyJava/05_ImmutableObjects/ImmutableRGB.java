final class ImmutableRGB {
  final private int red;
  final private int green;
  final private int blue;
  final private String name;

  private void check (int value) {
    if (value < 0 || value > 255) {
      throw IllegalArgumentException("Value must be between 0 and 255.");
    }
  }

  private void check (int red, int green, int blue) {
    check (red);
    check (green);
    check (blue);
  }

  ImmutableRGB (
      int red,
      int green,
      int blue,
      String name
  ) {
    check (red, green, blue);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.name = name;
  }

  int getRGB () {
    return (red << 16) | (green << 8) | blue;
  }

  int getName () {
    return this.name;
  }

  public ImmutableRGB invert () {
    return new ImmutableRGB (
        255 - red,
        255 - green,
        255 - blue,
        "Inverse of " + this.name
    );
  }
}

