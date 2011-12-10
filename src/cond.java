import java.util.*;
import java.io.*;

public class cond {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		cond p = new cond();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("cond.in"));
		out = new PrintWriter("cond.out");
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

		boolean addVertex(int ID) {
			if (v.containsKey(ID)) {
				return false;
			}
			v.put(ID, new Vertex(ID));
			return true;
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

		Vertex getVertexByID(int id) {
			return v.get(id);
		}

		int getNumberOfVertexes() {
			return n;
		}
	}

	class Condense {
		Graph g, cond;
		Set<Integer> arcs;
		int[] color;

		Condense(Graph g) {
			this.g = g;
		}

		int[] condense() {
			Graph rev = new Graph(g.getNumberOfVertexes());
			for (Graph.Vertex a : g.getVertexes()) {
				for (Graph.Vertex b : a.getRelatives()) {
					rev.addArc(b.ID, a.ID);
				}
			}

			List<Graph.Vertex> ord = topSort(g);

			color = new int[g.getNumberOfVertexes() + 1];

			color[0] = 0;
			
			for (Graph.Vertex v : ord) {
				if (color[v.ID] == 0) {
					dfs(rev.getVertexByID(v.ID), ++color[0]);
				}
			}

			return color;
		}

		void dfs(Graph.Vertex v, int c) {
			color[v.ID] = c;
			for (Graph.Vertex b : v.getRelatives()) {
				if (color[b.ID] == 0) {
					dfs(b, c);
				}
			}
		}

	}

	List<Graph.Vertex> topSort(Graph g) {
		int color[] = new int[g.getNumberOfVertexes()];
		int ret[] = new int[g.getNumberOfVertexes()];
		for (Graph.Vertex v : g.getVertexes()) {
			if (color[v.ID - 1] == 0) {
				dfs(v, color, ret);
			}
		}

		LinkedList<Graph.Vertex> list = new LinkedList<Graph.Vertex>();

		for (int v : ret) {
			list.add(g.getVertexByID(v));
		}
		return list;
	}

	int time = 0;

	void dfs(Graph.Vertex a, int[] color, int[] ret) {
		color[a.ID - 1] = 1;
		for (Graph.Vertex b : a.getRelatives()) {
			if (color[b.ID - 1] == 0) {
				dfs(b, color, ret);
			}
		}
		color[a.ID - 1] = 2;
		ret[ret.length - time++ - 1] = a.ID;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt());
		}

		Condense c = new Condense(g);
		int[] color = c.condense();
		
		for (int a:color){
			out.print(a + " ");
		}
		out.println();
		
	}

}
