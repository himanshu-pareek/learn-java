public class Player {
  private final String id;

  public Player (String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Player " + id;
  }
}

