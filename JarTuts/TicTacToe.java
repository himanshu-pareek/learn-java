import java.util.List;
import java.util.ArrayList;

public class TicTacToe {
  public static void main(String[] args) {
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      players.add (new Player ("" + (i + 1)));
    }

    assert players.size() == 2;
    
    System.out.println ("Following players are playing tic-tac-toe");
    players.stream()
      .forEach(System.out::println);
  }
}

