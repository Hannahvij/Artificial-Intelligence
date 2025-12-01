import java.util.*;

public class HanoiBFS {

    static class State {
        List<Stack<Integer>> pegs;
        List<String> moves;

        State(List<Stack<Integer>> pegs, List<String> moves) {
            this.pegs = pegs;
            this.moves = moves;
        }

        String encode() {
            StringBuilder sb = new StringBuilder();
            for (Stack<Integer> peg : pegs) {
                sb.append(peg.toString());
            }
            return sb.toString();
        }
    }

    public static void bfs(int n) {
        // Initial state
        List<Stack<Integer>> start = new ArrayList<>();
        for (int i = 0; i < 3; i++) start.add(new Stack<>());
        for (int i = n; i >= 1; i--) start.get(0).push(i);

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State initial = new State(start, new ArrayList<>());
        queue.offer(initial);
        visited.add(initial.encode());

        while (!queue.isEmpty()) {
            State current = queue.poll();

            // Goal: all disks on peg C
            if (current.pegs.get(2).size() == n) {
                System.out.println("BFS Tower of Hanoi Solution:");
                for (String move : current.moves)
                    System.out.println(move);
                return;
            }

            // Explore moves
            for (int from = 0; from < 3; from++) {
                if (current.pegs.get(from).isEmpty()) continue;

                for (int to = 0; to < 3; to++) {
                    if (from == to) continue;

                    int disk = current.pegs.get(from).peek();

                    // Valid move check
                    if (!current.pegs.get(to).isEmpty() &&
                            current.pegs.get(to).peek() < disk)
                        continue;

                    // -------- SAFE COPY OF PEGS -------- //
                    List<Stack<Integer>> newPegs = new ArrayList<>();
                    for (Stack<Integer> peg : current.pegs) {
                        Stack<Integer> copy = new Stack<>();
                        copy.addAll(peg);   // safe deep copy
                        newPegs.add(copy);
                    }
                    // ----------------------------------- //

                    // Apply move
                    newPegs.get(from).pop();
                    newPegs.get(to).push(disk);

                    List<String> newMoves = new ArrayList<>(current.moves);
                    newMoves.add("Move disk " + disk + " from " +
                            (char) ('A' + from) + " to " + (char) ('A' + to));

                    State next = new State(newPegs, newMoves);

                    if (!visited.contains(next.encode())) {
                        visited.add(next.encode());
                        queue.offer(next);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        bfs(n);
    }
}
