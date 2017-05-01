import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
import java.util.Comparator;
public class Solver {
    private class Node {
        private Board item;
        private Node parent;
        private int moves;
        private int pri;
        public Node(Board item, Node parent, int moves) {
            this.item = item;
            this.parent = parent;
            this.moves = moves;
            this.pri = item.manhattan() + moves;
        }
    }
    private static Comparator<Node> manComp = new Comparator<Node>() {
        
        public int compare(Node a, Node b) {
            int aman = a.pri;
            int bman = b.pri;
            if (aman > bman) return +1;
            if (aman < bman) return -1;
            return 0;
        }
    };
    private boolean iniSol, twinSol;
    private Node endNode;
    public Solver(Board initial) {
        Node start = new Node(initial, null, 0);
        
        Node twinStart = new Node(initial.twin(), null, 0);
        
        iniSol = false;
        twinSol = false;
        
        MinPQ<Node> iniPq = new MinPQ<Node>(manComp);
        iniPq.insert(start);
        MinPQ<Node> twinPq = new MinPQ<Node>(manComp);
        twinPq.insert(twinStart);
        
        while (!iniSol && !twinSol) {
            iniSol = step(iniPq);
            twinSol = step(twinPq);
        }
    }
    private boolean step(MinPQ<Node> pq) {
        Node k = pq.delMin();
        /*System.out.println("--------------");
        System.out.print(k.item.toString());
        System.out.println("Manhattan priority is : " + k.pri);
        System.out.println("--------------");*/
        if (k.item.isGoal()) {
            endNode = k;
            return true;
        }
        for (Board i : k.item.neighbors()) {
            //System.out.print(i.toString());
            if (k.parent != null && i.equals(k.parent.item)) continue;
            Node temp = new Node(i, k, k.moves + 1);
            //System.out.println("Manhattan no :" + temp.manhattan + " Moves :"+temp.moves+" Priority :"+temp.pri);
            pq.insert(temp);
        }
        //System.out.println("--------------");
        return false;
    }
    public boolean isSolvable() { return iniSol; }
    public int moves() {
        if (!isSolvable()) return -1;
        int moves = 0;
        Node currentNode = endNode;
        while(currentNode.parent != null) {
            moves++;
            currentNode = currentNode.parent;
        }
        return moves;
    }
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        LinkedList<Board> sol = new LinkedList<Board>();
        Node currentNode = endNode;
        sol.addFirst(currentNode.item);
        while(currentNode.parent != null) {
            currentNode = currentNode.parent;
            sol.addFirst(currentNode.item);
        }
        return sol;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("input.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}