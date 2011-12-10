import java.util.*;
import java.io.*;

public class pathmgep {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		pathmgep p = new pathmgep();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("pathmgep.in"));
		out = new PrintWriter("pathmgep.out");
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
			int cost;

			Edge(Vertex v, int cost) {
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

	class ShortestPath {
		int dist[];
		boolean vis[];

		int[] findPaths(Graph g, Graph.Vertex s) {
			dist = new int[g.getNumberOfVertexes()];
			Arrays.fill(dist, Integer.MAX_VALUE / 2);
			dist[s.ID - 1] = 0;
			for (int i = 0; i < g.getNumberOfVertexes(); i++) {
				for (Graph.Vertex u : g.getVertexes()) {
					for (Graph.Edge e : u.getRelatives()) {
						if (dist[e.v.ID - 1] > dist[u.ID - 1] + e.cost) {
							dist[e.v.ID - 1] = dist[u.ID - 1] + e.cost;
						}
					}
				}
			}

			return dist;
		}
	}

	void solve() throws NumberFormatException, IOException {
//		int n = nextInt();
//		int s = nextInt();
//		int f = nextInt();
//
//		Graph g = new Graph(n);
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				int c = nextInt();
//				if (c >= 0) {
//					g.addArc(i + 1, j + 1, c);
//				}
//			}
//		}
//		int[] d = new ShortestPath().findPaths(g, g.getVertexByID(s));
//		out.println(d[f - 1] == Integer.MAX_VALUE / 2 ? -1 : d[f - 1]);
		
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt(), nextInt());
		}
		for (int ans : new ShortestPath().findPaths(g,g.getVertexByID(1))) {
			out.print(ans + " ");
		}
	}

}
