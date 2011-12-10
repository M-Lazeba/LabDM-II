import java.util.*;
import java.io.*;

public class bridges {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		bridges p = new bridges();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("bridges.in"));
		out = new PrintWriter("bridges.out");
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
		TreeSet<Integer> bridges;

		public FindBridges(Graph g) {
			this.g = g;
			timeIn = new int[g.getNumberOfVertexes()];
			vis = new boolean[g.getNumberOfVertexes()];
			bridges = new TreeSet<Integer>();
			time = 0;
		}

		SortedSet<Integer> start() {
			for (Graph.Vertex v : g.getVertexes()) {
				if (!vis[v.ID - 1]) {
					dfs(v,null);
				}
			}
			return bridges;
		}

		int dfs(Graph.Vertex v, Graph.Vertex forbidden) {
			int timeFind = timeIn[v.ID - 1] = time++;
			vis[v.ID - 1] = true;

			for (Graph.Edge e : v.getRelatives()) {
				Graph.Vertex u = e.v;
				if (u == forbidden)
					continue;
				if (!vis[u.ID - 1]) {
					int t = dfs(u,v);
					if (t > timeIn[v.ID - 1]) {
						bridges.add(e.ID);
					}
					timeFind = Math.min(timeFind,t);
				} else {
					timeFind = Math.min(timeFind, timeIn[u.ID - 1]);
				}
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
