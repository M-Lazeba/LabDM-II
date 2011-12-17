import java.util.*;
import java.io.*;

public class maxflow {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		maxflow p = new maxflow();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("maxflow.in"));
		out = new PrintWriter("maxflow.out");
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

	public class Graph {

		int[][] e;
		int num;

		public Graph(int n) {
			num = n;
			e = new int[n][n];
		}

		public Graph(int[][] edges) {
			num = edges.length;
			e = edges.clone();
		}

		public void addEdge(int u, int v, int c) {
			e[u][v] = c;
		}

		public ArrayList<Integer> getNeighbours(int u) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < num; i++) {
				if (e[u][i] != 0) {
					list.add(i);
				}
			}
			return list;
		}

		public int getCapacity(int u, int v) {
			return e[u][v];
		}

		public int getNumberOfVertex() {
			return num;
		}
	}

	public class EdmondsKarp {

		Graph g;

		int start, finish;

		public EdmondsKarp(Graph newGraph, int s, int f) {
			g = newGraph;
			start = s;
			finish = f;

		}

		public Graph findMaxFlow() {
			int n = g.getNumberOfVertex();
			int[][] f = new int[n][n];
			while (true) {
				boolean find = false;
				ArrayList<Integer> list = new ArrayList<Integer>();
				ArrayList<Integer> paths = new ArrayList<Integer>();
				list.add(start);
				paths.add(-1);
				boolean vis[] = new boolean[n];
				vis[start] = true;

				for (int i = 0; i < list.size(); i++) {
					int u = list.get(i);
					for (int v : g.getNeighbours(u)) {
						if (!vis[v] && (g.getCapacity(u, v) - f[u][v] > 0)) {
							vis[v] = true;
							list.add(v);
							paths.add(i);
							if (v == finish) {
								find = true;
								break;
							}
						}
					}
					if (find)
						break;
				}

				if (!find)
					break;

				int min = Integer.MAX_VALUE;
				for (int i = paths.size() - 1;;) {
					if (paths.get(i) < 0)
						break;
					int u = list.get(paths.get(i));
					int v = list.get(i);
					if (min > g.getCapacity(u, v) - f[u][v]) {
						min = g.getCapacity(u, v) - f[u][v];
					}
					i = paths.get(i);
				}

				for (int i = paths.size() - 1;;) {
					if (paths.get(i) < 0)
						break;
					int u = list.get(paths.get(i));
					int v = list.get(i);

					f[u][v] += min;
					f[v][u] = -f[u][v];

					i = paths.get(i);
				}

			}

			return new Graph(f);
		}

	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();

		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.addEdge(nextInt() - 1, nextInt() - 1, nextInt());
		}

		Graph flow = new EdmondsKarp(g, 0, n - 1).findMaxFlow();
		int ans = 0;
		for (int u : flow.e[0]) {
			ans += u;
		}

		out.println(ans);
	}

}
