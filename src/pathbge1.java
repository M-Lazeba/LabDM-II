import java.util.*;
import java.io.*;

public class pathbge1 {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		pathbge1 p = new pathbge1();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("pathbge1.in"));
		out = new PrintWriter("pathbge1.out");
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

	class ShortestPath {
		int dist[];
		boolean vis[];

		int[] findPaths(Graph g) {
			dist = new int[g.getNumberOfVertexes()];
			vis = new boolean[g.getNumberOfVertexes()];

			Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
			q.add(g.getVertexByID(1));
			vis[0] = true;
			while (!q.isEmpty()) {
				Graph.Vertex u = q.poll();
				for (Graph.Vertex v : u.getRelatives()) {
					if (!vis[v.ID - 1]) {
						dist[v.ID - 1] = dist[u.ID - 1] + 1;
						vis[v.ID - 1] = true;
						q.add(v);
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
			g.addEdge(nextInt(), nextInt());
		}
		for (int ans : new ShortestPath().findPaths(g)) {
			out.print(ans + " ");
		}
	}

}
