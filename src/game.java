import java.util.*;
import java.io.*;

public class game {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		game p = new game();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("game.in"));
		out = new PrintWriter("game.out");
	}

	String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine());
		}
		return st.nextToken();
	}

	boolean hasMoreTokens() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			String buffer = in.readLine();
			if (buffer == null) {
				return false;
			}
			st = new StringTokenizer(buffer);
		}
		return true;
	}

	int nextInt() throws NumberFormatException, IOException {
		return Integer.parseInt(nextToken());
	}

	long nextLong() throws NumberFormatException, IOException {
		return Long.parseLong(nextToken());
	}

	double nextDouble() throws NumberFormatException, IOException {
		return Double.parseDouble(nextToken());
	}

	class Graph {

		int n;

		HashMap<Integer, Vertex> v;

		class Vertex {
			int ID;
			ArrayList<Vertex> relative;

			Vertex(int id) {
				ID = id;
				relative = new ArrayList<Vertex>();
			}

			ArrayList<Vertex> getRelatives() {
				return relative;
			}
		}

		Graph() {
			v = new HashMap<Integer, Graph.Vertex>();
		}

		Graph(int n){
			v = new HashMap<Integer, Graph.Vertex>();
			for (int i = 0; i < n; i++){
				v.put(i+1, new Vertex(i+1));
			}
			this.n = n;
		}

		void addArc(int a, int b) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			v.get(a).relative.add(v.get(b));
		}

		void addEdge(int a, int b) {
			addArc(a, b);
			addArc(b, a);
		}

		ArrayList<Vertex> getRelatives(Vertex a) {
			return a.relative;
		}

		Collection<Vertex> getVertexes() {
			return v.values();
		}

		int getNumberOfVertexes() {
			return n;
		}
		
		Vertex getVertexByID(int id) {
			return v.get(id);
		}
	}
	
	boolean vis[];
	boolean win[];
	
	boolean dfs(Graph.Vertex v){
		win[v.ID - 1] = false;
		
		for (Graph.Vertex b:v.getRelatives()){
			boolean bWin = vis[b.ID - 1] ? win[b.ID - 1] : dfs(b);
			win[v.ID - 1] = win[v.ID - 1] | !bWin;
		}
		
		return win[v.ID - 1];
	}
	
	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();
		int s = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt());
		}
		
		vis = new boolean[g.getNumberOfVertexes()];
		win = new boolean[g.getNumberOfVertexes()];
		
		out.println(dfs(g.getVertexByID(s)) ? "First player wins" : "Second player wins");
	}

}
