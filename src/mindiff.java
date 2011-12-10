import java.util.*;
import java.io.*;

public class mindiff {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		mindiff p = new mindiff();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("mindiff.in"));
		out = new PrintWriter("mindiff.out");
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

	class DSU {
		int[] parent;
		Graph.Vertex[] object;

		DSU(Graph g) {
			int n = g.getNumberOfVertexes();
			parent = new int[n];
			object = new Graph.Vertex[n];
			for (Graph.Vertex v : g.getVertexes()) {
				parent[v.ID - 1] = v.ID - 1;
				object[v.ID - 1] = v;
			}
		}

		int get(Graph.Vertex v) {
			int to = v.ID - 1;
			while (parent[to] != to) {
				parent[to] = parent[parent[to]];
				to = parent[to];
			}
			return to;
		}

		void union(Graph.Vertex a, Graph.Vertex b) {
			int setA = get(a);
			int setB = get(b);
			parent[setA] = setB;
		}
	}

	int minSpanTreeWeight(List<Graph.Edge> listEdge, Graph g) {
		int ans = 0;

		DSU set = new DSU(g);

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (Graph.Edge e : listEdge) {
			if (set.get(e.a) != set.get(e.b)) {
				// ans += e.c;
				if (min > e.c)
					min = e.c;
				if (max < e.c)
					max = e.c;
				set.union(e.a, e.b);
			}
		}
		if (max - min < 0) {
			return 0;
		}
		int a = set.get(g.getVertexByID(1));
		for (Graph.Vertex v : g.getVertexes()) {
			if (a != set.get(v))
				return -1;
		}
		return max - min;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);

		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt(), nextInt(), nextInt());
		}

		Graph.Edge[] listEdge = g.getEdges().toArray(new Graph.Edge[0]);

		Arrays.sort(listEdge, new Comparator<Graph.Edge>() {

			@Override
			public int compare(Graph.Edge o1, Graph.Edge o2) {
				return o1.c - o2.c;
			}

		});
		LinkedList<Graph.Edge> list = new LinkedList<Graph.Edge>();
		for (Graph.Edge e : listEdge) {
			list.add(e);
		}
		int ans = Integer.MAX_VALUE;
		int dif;
		while ((dif = minSpanTreeWeight(list, g)) >= 0) {
			if (ans > dif) {
				ans = dif;
			}
			list.removeFirst();
		}
		if (ans == Integer.MAX_VALUE) {
			out.println("NO");
		} else {
			out.println("YES");
			out.println(ans);
		}
	}

}
