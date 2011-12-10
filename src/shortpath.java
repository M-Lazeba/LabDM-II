import java.util.*;
import java.io.*;

public class shortpath {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		shortpath p = new shortpath();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("shortpath.in"));
		out = new PrintWriter("shortpath.out");
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
			ArrayList<Arc> relative;

			Vertex(int id) {
				ID = id;
				relative = new ArrayList<Arc>();
			}

			ArrayList<Arc> getRelatives() {
				return relative;
			}
		}

		class Arc {
			Vertex v;
			int c;

			Arc(Vertex v, int c) {
				this.v = v;
				this.c = c;
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

		void addArc(int a, int b, int c) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			v.get(a).relative.add(new Arc(v.get(b), c));
		}

		void addEdge(int a, int b, int c) {
			addArc(a, b, c);
			addArc(b, a, c);
		}

		ArrayList<Arc> getRelatives(Vertex a) {
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
	int dist[];
	int s, f;

	void dfs(Graph.Vertex v) {
		vis[v.ID - 1] = true;
		if (v.ID == f) {
			dist[v.ID - 1] = 0;
			return;
		}
		int d = Integer.MAX_VALUE;
		for (Graph.Arc b : v.getRelatives()) {
			if (!vis[b.v.ID - 1]) {
				dfs(b.v);
			}
			d = Math.min(d,
					dist[b.v.ID - 1] != Integer.MAX_VALUE ? dist[b.v.ID - 1]
							+ b.c : Integer.MAX_VALUE);
		}
		dist[v.ID - 1] = d;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();
		s = nextInt();
		f = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt(), nextInt());
		}

		vis = new boolean[g.getNumberOfVertexes()];
		dist = new int[g.getNumberOfVertexes()];
		Arrays.fill(dist, Integer.MAX_VALUE);

		dfs(g.getVertexByID(s));

		if (!vis[f - 1]) {
			out.println("Unreachable");
		} else {
			out.println(dist[s - 1]);
		}

	}

}
