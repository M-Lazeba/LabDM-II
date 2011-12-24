import java.util.*;
import java.io.*;

public class mincost {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		mincost p = new mincost();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("mincost.in"));
		out = new PrintWriter("mincost.out");
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
		class Arc {
			int a, b;
			long use, cost, flow;
			Arc back;

			Arc(int a, int b, int u, int c) {
				this.a = a;
				this.b = b;
				this.cost = c;
				this.flow = 0;
				this.use = u;
			}
		}

		ArrayList<ArrayList<Arc>> arcs;
		int n;

		Graph(int n) {
			this.n = n;
			arcs = new ArrayList<ArrayList<Arc>>(n);
			for (int i = 0; i < n; i++) {
				arcs.add(new ArrayList<Arc>());
			}
		}

		void add(int a, int b, int u, int c) {
			a--;
			b--;
			Arc forward = new Arc(a, b, u, c);
			Arc back = new Arc(b, a, 0, -c);
			forward.back = back;
			back.back = forward;
			arcs.get(a).add(forward);
			arcs.get(b).add(back);
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int m = nextInt();
		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			g.add(nextInt(), nextInt(), nextInt(), nextInt());
		}
		long flow = 0;
		long cost = 0;

		while (true) {
			int color[] = new int[n];
			long d[] = new long[n];
			Arrays.fill(d, Long.MAX_VALUE);
			d[0] = 0;
			Graph.Arc pred[] = new Graph.Arc[n];

			LinkedList<Integer> q = new LinkedList<Integer>();
			q.add(0);
			while (!q.isEmpty()) {
				int v = q.pollFirst();
				color[v] = 2;
				for (Graph.Arc r : g.arcs.get(v)) {
					if (r.flow < r.use && d[v] + r.cost < d[r.b]) {
						d[r.b] = d[v] + r.cost;
						if (color[r.b] == 0)
							q.addLast(r.b);
						if (color[r.b] == 2)
							q.addFirst(r.b);
						color[r.b] = 1;
						pred[r.b] = r;
					}
				}
			}
			if (d[n - 1] == Long.MAX_VALUE)
				break;
			long addFlow = Long.MAX_VALUE;
			for (Graph.Arc r = pred[n - 1]; r != null; r = pred[r.a]) {
				if (addFlow > r.use - r.flow)
					addFlow = r.use - r.flow;
			}
			for (Graph.Arc r = pred[n - 1]; r != null; r = pred[r.a]) {
				r.flow += addFlow;
				r.back.flow -= addFlow;
				cost += r.cost * addFlow;
			}
			flow += addFlow;
		}

		out.println(cost);
	}

}
