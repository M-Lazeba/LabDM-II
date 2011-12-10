import java.util.*;
import java.io.*;

public class biconv {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		biconv p = new biconv();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("biconv.in"));
		out = new PrintWriter("biconv.out");
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

	class FindPoints {
		Graph g;
		int[] timeIn, timeFind;
		int time;
		boolean vis[];
		HashSet<Graph.Vertex> points;
		Graph.Vertex root;

		public FindPoints(Graph g) {
			this.g = g;
			timeIn = new int[g.getNumberOfVertexes()];
			timeFind = new int[g.getNumberOfVertexes()];
			vis = new boolean[g.getNumberOfVertexes()];
			points = new HashSet<Graph.Vertex>();
			time = 0;
		}

		HashSet<Graph.Vertex> start() {
			for (Graph.Vertex v : g.getVertexes()) {
				if (!vis[v.ID - 1]) {
					root = v;
					dfs(v, null);
				}
			}
			return points;
		}

		int dfs(Graph.Vertex v, Graph.Vertex forbidden) {
			timeFind[v.ID - 1] = timeIn[v.ID - 1] = time++;
			int tdfs = 0;
			vis[v.ID - 1] = true;
			int c = 0;

			for (Graph.Edge e : v.getRelatives()) {
				Graph.Vertex u = e.v;
				if (u == forbidden)
					continue;
				int t = timeFind[v.ID - 1];
				if (!vis[u.ID - 1]) {
					t = dfs(u, v);
					c++;
					tdfs = Math.max(tdfs, t);
				}
				timeFind[v.ID - 1] = Math.min(Math.min(t, timeFind[v.ID - 1]),
						timeIn[u.ID - 1]);
			}
			if ((c > 0)
					&& ((tdfs >= timeIn[v.ID - 1] && v != root) || (v == root && c > 1))) {
				points.add(v);
			}
			return timeFind[v.ID - 1];
		}

		int comp;

		int[] findComponents(int m) {
			start();
			int[] ans = new int[m + 1];
			Arrays.fill(vis, false);
			comp = 1;
			for (Graph.Vertex v : g.getVertexes()) {
				if (!vis[v.ID - 1]) {
					dfs2(v, ans, comp, null);
				}
			}
			ans[m] = comp;
			return ans;
		}

		void dfs2(Graph.Vertex v, int[] ans, int c, Graph.Vertex parent) {
			vis[v.ID - 1] = true;
			for (Graph.Edge e : v.getRelatives()) {
				Graph.Vertex u = e.v;
				if (u == parent)
					continue;
				if (!vis[u.ID - 1]) {
					if (timeFind[u.ID - 1] >= timeIn[v.ID - 1]) {
						int c2 = comp++;
						ans[e.ID - 1] = c2;
						dfs2(u, ans, c2, v);
					} else {
						ans[e.ID - 1] = c;
						dfs2(u, ans, c, v);
					}
				} else {
					if (timeIn[u.ID - 1] <= timeIn[v.ID - 1]) {
						ans[e.ID - 1] = c;
					}
				}
			}
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);

		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt(), i + 1);
		}

		int[] ans = new FindPoints(g).findComponents(m);
		out.println(ans[m] - 1);
		for (int i = 0; i < m; i++) {
			out.print(ans[i] + " ");
		}
	}
}
