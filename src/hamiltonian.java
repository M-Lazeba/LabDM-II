import java.util.*;
import java.io.*;

public class hamiltonian {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		hamiltonian p = new hamiltonian();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("hamiltonian.in"));
		out = new PrintWriter("hamiltonian.out");
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
			addArc(a, a);
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

	boolean vis[];
	LinkedList<Graph.Vertex> topsort;

	void dfs(Graph.Vertex v) {
		vis[v.ID - 1] = true;
		for (Graph.Vertex b : v.getRelatives()) {
			if (!vis[b.ID - 1]) {
				dfs(b);
			}
		}
		topsort.addFirst(v);
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt());
		}

		topsort = new LinkedList<Graph.Vertex>();
		vis = new boolean[g.getNumberOfVertexes()];

		Graph.Vertex s = null;
		for (Graph.Vertex v : g.getVertexes()) {
			if (v.inRelative == 0) {
				s = v;
				break;
			}
		}
		if (s == null) {
			out.println("NO");
			return;
		}
		dfs(s);

		boolean ans = true;
		
		for (boolean v:vis){
			ans &= v;
		}
		
		Graph.Vertex a, b;
		b = topsort.removeFirst();
		while (ans && !topsort.isEmpty()) {
			a = b;
			b = topsort.removeFirst();
			ans &= a.getRelatives().contains(b);
		}
		
		
		out.println(ans ? "YES" : "NO");
	}

}
