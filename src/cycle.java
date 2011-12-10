import java.util.*;
import java.io.*;

public class cycle {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		cycle p = new cycle();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("cycle.in"));
		out = new PrintWriter("cycle.out");
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

	int color[];
	LinkedList<Graph.Vertex> cycle;

	int dfs(Graph.Vertex v) {
		color[v.ID - 1] = 1;

		for (Graph.Vertex b : v.getRelatives()) {
			if (color[b.ID - 1] == 1) {
				cycle = new LinkedList<Graph.Vertex>();
				cycle.add(v);
				return b.ID;
			}
			if (color[b.ID - 1] == 0) {
				int res = dfs(b);
				if (res > -1) {
					if (res == 0) {
						return 0;
					}
					cycle.addFirst(v);
					return v.ID == res ? 0 : res;
				}
			}
		}
		color[v.ID - 1] = 2;
		return -1;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt());
		}

		color = new int[g.getNumberOfVertexes()];
		int result = -1;
		for (Graph.Vertex v : g.getVertexes()) {
			if (result == -1 && color[v.ID - 1] == 0) {
				result = dfs(v);
			}
		}
		if (result == -1) {
			out.println("NO");
		} else {
			out.println("YES");
			for (Graph.Vertex v : cycle) {
				out.print(v.ID + " ");
			}
			out.println();
		}

	}

}
