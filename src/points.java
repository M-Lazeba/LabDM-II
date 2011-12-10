import java.util.*;
import java.io.*;

public class points {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		points p = new points();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("points.in"));
		out = new PrintWriter("points.out");
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
			ArrayList<Edge> relative;

			Vertex(int id) {
				ID = id;
				relative = new ArrayList<Edge>();
			}

			ArrayList<Edge> getRelatives() {
				return relative;
			}
		}

		class Edge {
			Vertex v;
			int ID;

			Edge(Vertex v, int ID) {
				this.v = v;
				this.ID = ID;
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

		void addArc(int a, int b, int num) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			v.get(a).relative.add(new Edge(v.get(b), num));
			v.get(b).inRelative++;
		}

		void addEdge(int a, int b, int num) {
			addArc(a, b, num);
			addArc(b, a, num);
		}

		ArrayList<Edge> getRelatives(Vertex a) {
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

	class FindBridges {
		Graph g;
		int[] timeIn;
		int time;
		boolean vis[];
		TreeSet<Integer> points;
		Graph.Vertex root;

		public FindBridges(Graph g) {
			this.g = g;
			timeIn = new int[g.getNumberOfVertexes()];
			vis = new boolean[g.getNumberOfVertexes()];
			points = new TreeSet<Integer>();
			time = 0;
		}

		SortedSet<Integer> start() {
			for (Graph.Vertex v : g.getVertexes()) {
				if (!vis[v.ID - 1]) {
					root = v;
					dfs(v, null);
				}
			}
			return points;
		}

		int dfs(Graph.Vertex v, Graph.Vertex forbidden) {
			int timeFind = timeIn[v.ID - 1] = time++;
			int tdfs = 0;
			vis[v.ID - 1] = true;
			int c = 0;

			for (Graph.Edge e : v.getRelatives()) {
				Graph.Vertex u = e.v;
				if (u == forbidden)
					continue;
				int t = timeFind;
				if (!vis[u.ID - 1]) {
					t = dfs(u, v);
					c++;
					tdfs = Math.max(tdfs, t);
				}
				timeFind = Math.min(Math.min(t, timeFind), timeIn[u.ID - 1]);
			}
			if ((c > 0)
					&& ((tdfs >= timeIn[v.ID - 1] && v != root) || (v == root && c > 1))) {
				points.add(v.ID);
			}
			return timeFind;
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);

		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt(), i + 1);
		}

		SortedSet<Integer> bridges = new FindBridges(g).start();
		out.println(bridges.size());
		for (int e : bridges) {
			out.print(e + " ");
		}
	}

}
