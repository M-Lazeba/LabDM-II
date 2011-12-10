import java.util.*;
import java.io.*;

public class pathbgep {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		pathbgep p = new pathbgep();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("pathbgep.in"));
		out = new PrintWriter("pathbgep.out");
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
		TreeSet<Dist> tree;
		boolean vis[];
		int dist[];

		class Dist implements Comparable<Dist> {
			Graph.Vertex v;
			int d;

			Dist(Graph.Vertex v, int d) {
				this.v = v;
				this.d = d;
			}

			@Override
			public int compareTo(Dist o) {
				return d - o.d;
			}

		}

		int[] findPaths(Graph g) {
			tree = new TreeSet<Dist>();
			dist = new int[g.getNumberOfVertexes()];
			Arrays.fill(dist, Integer.MAX_VALUE / 2);
			dist[0] = 0;
			vis = new boolean[g.getNumberOfVertexes()];

			tree.add(new Dist(g.getVertexByID(1), 0));
			while (!tree.isEmpty()) {
				Dist d = tree.pollFirst();
				Graph.Vertex v = d.v;
				if (vis[v.ID - 1])
					continue;
				vis[v.ID - 1] = true;
				for (Graph.Edge e : v.getRelatives()) {
					Graph.Vertex u = e.v;
					if (vis[u.ID - 1])
						continue;
					if (dist[u.ID - 1] > d.d + e.cost) {
						dist[u.ID - 1] = d.d + e.cost;
						tree.add(new Dist(u, dist[u.ID - 1]));
					}
				}
			}
			return dist;
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt(), nextInt());
		}
		for (int ans : new ShortestPath().findPaths(g)) {
			out.print(ans + " ");
		}
	}

}
