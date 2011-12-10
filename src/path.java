import java.util.*;
import java.io.*;

public class path {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		path p = new path();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("path.in"));
		out = new PrintWriter("path.out");
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
			long cost;

			Edge(Vertex v, long cost) {
				this.v = v;
				this.cost = cost;
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

		void addArc(int a, int b, long num) {
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

		void addEdge(int a, int b, long num) {
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

	class ShortestPath {
		long dist[];
		boolean vis[];

		long[] findPaths(Graph g, Graph.Vertex s) {
			dist = new long[g.getNumberOfVertexes()];
			Arrays.fill(dist, INF);
			dist[s.ID - 1] = 0;
			for (int i = 0; i < g.getNumberOfVertexes() - 1; i++) {
				for (Graph.Vertex u : g.getVertexes()) {
					for (Graph.Edge e : u.getRelatives()) {
						if (dist[u.ID - 1] < INF) {
							dist[e.v.ID - 1] = Math.min(dist[e.v.ID - 1],
									Math.max(-INF, dist[u.ID - 1] + e.cost));
						}
					}
				}
			}

			for (int i = 0; i < g.getNumberOfVertexes(); i++) {
				for (Graph.Vertex u : g.getVertexes()) {
					for (Graph.Edge e : u.getRelatives()) {
						if (dist[u.ID - 1] < INF) {
							if (dist[e.v.ID - 1] > dist[u.ID - 1] + e.cost) {
								dist[e.v.ID - 1] = -INF;
							}
						}
					}
				}
			}
			return dist;
		}
	}

	long INF = Long.MAX_VALUE / 2;

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();
		int s = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt(), nextLong());
		}
		long[] d = new ShortestPath().findPaths(g, g.getVertexByID(s));
		for (long ans : d) {
			if (ans == INF) {
				out.println("*");
				continue;
			}
			if (ans == -INF) {
				out.println("-");
				continue;
			}
			out.println(ans);
		}
	}

}
