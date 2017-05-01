import java.util.ArrayList;
public class Board {
    private int[][] blocks;
    private int n;
    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) this.blocks[i][j] = blocks[i][j];
        }
    }
    public int dimension() { return n; }
    
    public int hamming() {
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != ((i*n) + (j+1))) h++;
            }
        }
        return h;
    }
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) continue;
                int aci = (blocks[i][j] - 1) / n;
                int acj = (blocks[i][j] - 1) % n;
                if (aci == i && acj == j) continue;
                m = m + Math.abs(aci - i) + Math.abs(acj - j);
            }
        }
        return m;
    }
    public boolean isGoal() {
        boolean flag = true;
        out: for (int  i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != ((i*n) + (j +1))) {
                    flag = false;
                    break out;
                }
            }
        }
        return flag;
    }
    public Board twin() {
        Board twin = new Board(blocks);
        if (twin.blocks[0][0] != 0 && twin.blocks[1][1] != 0) {
            int temp = twin.blocks[0][0];
            twin.blocks[0][0] = twin.blocks[1][1];
            twin.blocks[1][1] = temp;
        }
        else {
            int temp = twin.blocks[0][1];
            twin.blocks[0][1] = twin.blocks[1][0];
            twin.blocks[1][0] = temp;
        }
        return twin;
    }
    public boolean equals(Object y) {
        
        if(y == null) return false;
        
        if(this == y) return true;
        
        if(this.getClass() != y.getClass() ) return false;
        
        if(this.toString().equalsIgnoreCase(y.toString())) return true;
        
        Board x = (Board) y;
        if(this.dimension() != x.dimension()) return false;
        
        return this.compareTo(x);
    }
    private boolean compareTo(Board that) {
        boolean flag = true;
        out: for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] !=that.blocks[i][j]) {
                    flag = false;
                    break out;
                }
            }
        }
        return flag;
    }
    public Iterable<Board> neighbors() {
        ArrayList<Board> neigh = new ArrayList<Board>();
        this.getNeighbors(neigh);
        return neigh;
    }
    public String toString() {
        String ss = "";
        ss = ss + Integer.toString(n) + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) ss = ss + blocks[i][j] + "\t";
            ss = ss + "\n";
        }
        return ss;
    }
    private void getNeighbors(ArrayList<Board> neigh) {
        int i0 = 0, j0 = 0;
        out: for (int i = 0; i < n; i++) {
            for (int j = 0;  j < n; j++) {
                if (this.blocks[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break out;
                }
            }
        }
        if (i0 != 0) neigh.add(exch(i0, j0, i0 - 1, j0));
        if (i0 != n-1) neigh.add(exch(i0, j0, i0 + 1, j0));
        if (j0 != 0) neigh.add(exch(i0, j0, i0, j0 - 1));
        if (j0 != n-1) neigh.add(exch(i0, j0, i0, j0 + 1));
    }
    private Board exch(int i0, int j0, int i, int j) {
        Board tempBo = new Board(blocks);
        int temp = tempBo.blocks[i][j];
        tempBo.blocks[i][j] = 0;
        tempBo.blocks[i0][j0] = temp;
        return tempBo;
    }
}
    
    
    