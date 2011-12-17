import java.util.*;
import java.io.*;

public class paths {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		paths p = new paths();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("paths.in"));
		out = new PrintWriter("paths.out");
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
		ArrayList<Edge> e;

		class Vertex {
			int ID;
			Vertex matching = null;
			private ArrayList<Edge> relative;

			Vertex(int id) {
				ID = id;
				relative = new ArrayList<Edge>();
			}

			ArrayList<Edge> getRelatives() {
				return relative;
			}
		}

		class Edge {
			Vertex a, b;
			int c;

			Edge(Vertex a, Vertex b, int c) {
				this.a = a;
				this.b = b;
				this.c = c;
			}
		}

		Graph() {
			v = new HashMap<Integer, Graph.Vertex>();
			e = new ArrayList<Edge>();
		}

		Graph(int n) {
			v = new HashMap<Integer, Graph.Vertex>();
			for (int i = 0; i < n; i++) {
				v.put(i + 1, new Vertex(i + 1));
			}
			this.n = n;
			e = new ArrayList<Edge>();
		}

		void addEdge(int a, int b, int c) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			Edge newEdge = new Edge(v.get(a), v.get(b), c);
			v.get(a).relative.add(newEdge);
			v.get(b).relative.add(newEdge);
			e.add(newEdge);
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

		List<Edge> getEdges() {
			return e;
		}
	}

	class Kuhn {
		boolean[] visited;

		int getMaxMatching(Graph g, int leftSize) {
			visited = new boolean[g.getNumberOfVertexes()];

			int ans = 0;
			for (int i = 1; i <= leftSize; i++) {
				Graph.Vertex v = g.getVertexByID(i);
				if (v.matching != null) {
					ans++;
					continue;
				}
				Arrays.fill(visited, false);
				if (dfs(v))
					ans++;
			}

			return ans;
		}

		boolean dfs(Graph.Vertex v) {
			if (visited[v.ID - 1])
				return false;
			visited[v.ID - 1] = true;
			for (Graph.Edge e : v.getRelatives()) {
				Graph.Vertex u = e.b;
				if (u.matching == null) {
					v.matching = u;
					u.matching = v;
					return true;
				}
				if (dfs(u.matching)) {
					v.matching = u;
					u.matching = v;
					return true;
				}
			}
			return false;
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int k = nextInt();

		Graph g = new Graph(n);

		for (int i = 0; i < k; i++) {
			g.addEdge(nextInt(), n + nextInt(), 0);
		}

		out.println(n - (new Kuhn().getMaxMatching(g, n)));

	}

}
