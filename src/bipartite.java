import java.util.*;
import java.io.*;

public class bipartite {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		bipartite p = new bipartite();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("bipartite.in"));
		out = new PrintWriter("bipartite.out");
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
			int inRelative = 0;
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

		Graph(int n) {
			v = new HashMap<Integer, Graph.Vertex>();
			for (int i = 0; i < n; i++) {
				v.put(i + 1, new Vertex(i + 1));
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
			v.get(b).inRelative++;
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

	boolean col[], vis[];

	boolean dfs(Graph.Vertex v, boolean c) {
		vis[v.ID - 1] = true;
		col[v.ID - 1] = c;
		for (Graph.Vertex u : v.getRelatives()) {
			if (!vis[u.ID - 1]) {
				if (!dfs(u, !c))
					return false;
			} else {
				if (col[u.ID - 1] != !c) {
					return false;
				}
			}
		}
		return true;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();
		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt());
		}

		vis = new boolean[n];
		col = new boolean[n];
		boolean ans = true;
		for (Graph.Vertex v : g.getVertexes()) {
			if (!vis[v.ID - 1]) {
				ans &= dfs(v, true);
			}
		}
		out.println(ans ? "YES" : "NO");
	}

}
