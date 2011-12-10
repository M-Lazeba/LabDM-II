import java.util.*;
import java.io.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class spantree {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		spantree p = new spantree();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("spantree.in"));
		out = new PrintWriter("spantree.out");
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

	class Vertex {
		Vertex accessory;
		int x, y, ID;

		Vertex(int x, int y, int ID) {
			this.x = x;
			this.y = y;
			this.ID = ID;
			this.accessory = this;
		}

		double dist(Vertex v) {
			return Math.sqrt((x - v.x) * (x - v.x) + (y - v.y) * (y - v.y));
		}
	}

	class Edge implements Comparable<Edge> {
		int length;
		Vertex a, b;

		Edge(Vertex a, Vertex b) {
			this.a = a;
			this.b = b;
			this.length = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
		}

		@Override
		public int compareTo(Edge o) {
			return length - o.length;
		}
	}

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		Vertex vertexes[] = new Vertex[n];
		for (int i = 0; i < n; i++) {
			vertexes[i] = new Vertex(nextInt(), nextInt(), i);
		}
		boolean used[] = new boolean[n];
		double minEdge[] = new double[n];
		int edgeTo[] = new int[n];
		Arrays.fill(minEdge, Double.POSITIVE_INFINITY);
		minEdge[0] = 0;
		Arrays.fill(edgeTo, -1);

		double ans = 0;

		for (int i = 0; i < n; i++) {
			int v = -1;
			for (int j = 0; j < n; j++) {
				if (!used[j] && (v == -1 || minEdge[j] < minEdge[v])) {
					v = j;
				}
			}

			used[v] = true;
			ans += minEdge[v];

			for (int j = 0; j < n; j++) {
				double d = vertexes[v].dist(vertexes[j]);
				if (d < minEdge[j]) {
					minEdge[j] = d;
				}
			}
		}

		out.println(ans);
	}

}
