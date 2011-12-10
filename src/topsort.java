import java.util.*;
import java.io.*;

public class topsort {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		topsort p = new topsort();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("topsort.in"));
		out = new PrintWriter("topsort.out");
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

		Graph(int n){
			v = new HashMap<Integer, Graph.Vertex>();
			for (int i = 0; i < n; i++){
				v.put(i+1, new Vertex(i+1));
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
	}

	void topSort(Graph g) {
		int color[] = new int[g.getNumberOfVertexes()];
		int ret[] = new int[g.getNumberOfVertexes()];
		for (Graph.Vertex v : g.getVertexes()) {
			if (color[v.ID - 1] == 0) {
				boolean circle = dfs(v, color, ret);
				if (circle) {
					out.println(-1);
					return;
				}
			}
		}
		for (int v : ret) {
			out.print(v + " ");
		}
		out.println();
	}

	int time = 0;

	boolean dfs(Graph.Vertex a, int[] color, int[] ret) {
		color[a.ID - 1] = 1;
		for (Graph.Vertex b : a.getRelatives()) {
			boolean halt = false;
			if (color[b.ID - 1] == 0) {
				halt = dfs(b, color, ret);
			}
			if (color[b.ID - 1] == 1 || halt) {
				return true;
			}
		}
		color[a.ID - 1] = 2;
		ret[ret.length - time++ - 1] = a.ID;
		return false;
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt());
		}

		topSort(g);
	}

}
