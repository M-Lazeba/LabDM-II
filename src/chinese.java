import java.util.*;
import java.io.*;

public class chinese {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		chinese p = new chinese();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("chinese.in"));
		out = new PrintWriter("chinese.out");
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

		int n, m = 0;

		HashMap<Integer, Vertex> v;
		ArrayList<Arc> e;

		class Vertex {
			int ID;
			ArrayList<Arc> outcoming;
			ArrayList<Arc> incoming;

			Vertex(int id) {
				ID = id;
				outcoming = new ArrayList<Arc>();
				incoming = new ArrayList<Arc>();
			}

			ArrayList<Arc> getOutcomings() {
				return outcoming;
			}

			ArrayList<Arc> getIncomings() {
				return incoming;
			}
		}

		class Arc {
			Vertex a, b;
			int c, ID;
			boolean used = false;

			Arc(Vertex a, Vertex b, int c) {
				this.a = a;
				this.b = b;
				this.c = c;
				this.ID = ++m;
			}
		}

		Graph() {
			v = new HashMap<Integer, Graph.Vertex>();
			e = new ArrayList<Arc>();
		}

		Graph(int n) {
			v = new HashMap<Integer, Graph.Vertex>();
			for (int i = 0; i < n; i++) {
				v.put(i + 1, new Vertex(i + 1));
			}
			this.n = n;
			e = new ArrayList<Arc>();
		}

		void addArc(int a, int b, int c) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			Arc newArc = new Arc(v.get(a), v.get(b), c);
			v.get(a).outcoming.add(newArc);
			v.get(b).incoming.add(newArc);
			e.add(newArc);
		}

		ArrayList<Arc> getOutcomings(Vertex a) {
			return a.outcoming;
		}

		Collection<Vertex> getVertexes() {
			return v.values();
		}

		int getNumberOfVertexes() {
			return n;
		}

		int getNumberOfArcs() {
			return m;
		}

		Vertex getVertexByID(int id) {
			return v.get(id);
		}

		ArrayList<Arc> getArcs() {
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


	
	class ChuLiu {
		Graph g;
		boolean impossible = false;
		DSU set;

		public ChuLiu(Graph g) {
			this.g = g;
			set = new DSU(g);
		}

		ArrayList<ArrayList<Graph.Arc>> findCycles() {
			ArrayList<ArrayList<Graph.Arc>> ans = new ArrayList<ArrayList<Graph.Arc>>();
			ArrayList<ArrayList<Graph.Arc>> set = new ArrayList<ArrayList<Graph.Arc>>();
			boolean visited[] = new boolean[g.getNumberOfVertexes()];
			for (Graph.Vertex v : g.getVertexes()) {
				if (visited[v.ID - 1])
					continue;
				set.add(dfs(v, new ArrayList<Graph.Arc>(), visited));
			}
			for (ArrayList<Graph.Arc> list : set) {
				if (list.isEmpty())
					continue;
				Graph.Arc first = list.get(0);
				Graph.Arc prec = null;

				for (Graph.Arc e : first.a.incoming) {
					if (e.used) {
						prec = e;
						break;
					}
				}

				if (!list.contains(prec)) {
					continue;
				}
				ans.add(list);
			}
			return ans;
		}

		ArrayList<Graph.Arc> dfs(Graph.Vertex v, ArrayList<Graph.Arc> set,
				boolean visited[]) {
			if (visited[v.ID - 1]) {
				return set;
			}
			visited[v.ID - 1] = true;
			for (Graph.Arc e : v.getOutcomings()) {
				if (!e.used)
					continue;
				set.add(e);
				dfs(e.b, set, visited);
			}
			return set;
		}

		int ifPossible(Graph.Vertex v, boolean visited[], int k) {
			if (visited[v.ID - 1]) {
				return k;
			}
			visited[v.ID - 1] = true;
			k++;
			for (Graph.Arc e : v.getOutcomings()) {
				k = ifPossible(e.b, visited, k);
			}
			return k;
		}

		int findMST() {
			Graph.Vertex start = g.getVertexByID(1);

			if (g.getNumberOfVertexes() > ifPossible(start,
					new boolean[g.getNumberOfVertexes()], 0)) {
				return -1;
			}

			for (Graph.Vertex v : g.getVertexes()) {
				if (v == start)
					continue;
				Graph.Arc min = v.getIncomings().get(0);
				for (Graph.Arc e : v.getIncomings()) {
					if (min.c > e.c)
						min = e;
				}
				if (min != null)
					min.used = true;
			}

			
			boolean inCycle[] = new boolean[g.getNumberOfVertexes()];
			while (true) {
				ArrayList<ArrayList<Graph.Arc>> cycles = findCycles();
					System.out.println("\nnext step");
					System.out.println(cycles.size());

				if (cycles.size() == 0)
					break;

				for (ArrayList<Graph.Arc> cycle : cycles) {
						System.out.println("\nCycle--------------");
					Graph.Arc min = cycle.get(0);
					Arrays.fill(inCycle, false);
					for (Graph.Arc e : cycle) {
						inCycle[e.a.ID - 1] = true;
							System.out.print(e.a.ID + " ");
						if (min.c > e.c) {
							min = e;
						}
					}
					Graph.Arc minIn = null;
					int minCost = Integer.MAX_VALUE;
					
						System.out.println("\n#Incomings:");
					for (Graph.Arc e : cycle) {

						for (Graph.Arc in : e.a.incoming) {
							if (in.used)
								continue;
							if (inCycle[in.a.ID - 1])
								continue;
								System.out.println(in.a.ID+" " + in.b.ID);
							int cost = in.c - (e.c - min.c);
							if (minIn == null || cost < minCost) {
								minIn = in;
								minCost = cost;
							}
						}
					}
					minIn.used = true;
					for (Graph.Arc e : cycle) {
						if (minIn.b == e.b) {
							e.used = false;
							break;
						}
					}
				}
			}

			int ans = 0;
			for (Graph.Arc e : g.getArcs()) {
				if (e.used) {
					ans += e.c;
				}
			}
			return ans;
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addArc(nextInt(), nextInt(), nextInt());
		}

		ChuLiu algorithm = new ChuLiu(g);
		int ans = algorithm.findMST();

		if (ans == -1) {
			out.println("NO");
			return;
		}
		out.println("YES");
		out.println(ans);
	}

}
