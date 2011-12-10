import java.security.acl.LastOwnerException;
import java.util.*;
import java.io.*;

public class euler {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		euler p = new euler();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("euler.in"));
		out = new PrintWriter("euler.out");
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
			ArrayList<Edge> relative;
			int lastVisited = 0;

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
			boolean ifRemoved = false;

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

	ArrayList<Graph.Vertex> findPath(Graph g, Graph.Vertex start) {
		Stack<Graph.Vertex> st = new Stack<Graph.Vertex>();
		ArrayList<Graph.Vertex> ans = new ArrayList<euler.Graph.Vertex>();

		st.push(start);
		while (!st.isEmpty()) {
			Graph.Vertex v = st.peek();
			if (v.lastVisited == v.getRelatives().size()) {
				ans.add(v);
				st.pop();
			} else {
				Graph.Edge e = v.getRelatives().get(v.lastVisited++);
				if (e.ifRemoved)
					continue;
				e.ifRemoved = true;
				st.push(e.a == v ? e.b : e.a);
			}
		}

		return ans;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		Graph g = new Graph(n);
		Graph.Vertex start = null, finish = null;
		int kol = 0;
		int m = 0;

		for (int i = 0; i < n; i++) {
			int k = nextInt();
			m += k;
			if (k % 2 != 0) {
				kol++;
				if (start == null)
					start = g.getVertexByID(i + 1);
				else
					finish = g.getVertexByID(i + 1);
			}
			for (int j = 0; j < k; j++) {
				int v = nextInt();
				if (v > i + 1) {
					g.addEdge(i + 1, v, 0);
				}
			}

		}
		m /= 2;

		if (kol == 0) {
			out.println(m);
			for (Graph.Vertex v : findPath(g, g.getVertexByID(1))) {
				out.print(v.ID + " ");
			}
			return;
		}

		if (kol == 2) {
			out.println(m);
			for (Graph.Vertex v : findPath(g, start).subList(0, m + 1)) {
				out.print(v.ID + " ");
			}
			return;
		}
		out.println(-1);
	}

}
