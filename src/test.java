import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;


public class test {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		test p = new test();
//		p.init();
		p.solve();
//		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("test.in"));
		out = new PrintWriter("test.out");
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
			Vertex v;
			int cost;

			Edge(Vertex v, int cost) {
				this.v = v;
				this.cost = cost;
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

		void addArc(int a, int b, int num) {
			if (!v.containsKey(a)) {
				v.put(a, new Vertex(a));
				n++;
			}
			if (!v.containsKey(b)) {
				v.put(b, new Vertex(b));
				n++;
			}
			v.get(a).relative.add(new Edge(v.get(b), num));
			v.get(b).inRelative++;
		}

		void addEdge(int a, int b, int num) {
			addArc(a, b, num);
			addArc(b, a, num);
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
	}


	
	void solve() throws NumberFormatException, IOException {
		int N = 300;
		int M = 4000;
		int C = 10000;
		
		int k = 1;
		boolean h = true;
		while (h){
					int n = (int) (N*Math.random()+1);
			int m = (int) (M*Math.random()+1);
			Graph g = new Graph(n);
			for (int i = 0;i < m; i++){
				int a = (int) ((n-1)*Math.random()+1);
				int b = (int) ((n-1)*Math.random()+1);
				int c = (int) (C*Math.random()+1);
				g.addEdge(a, b, c);
			}
			PrintWriter out1 = new PrintWriter("pathmgep.in");
			PrintWriter out2 = new PrintWriter("pathbgep.in");
			out1.println(n+" "+(2*m));
			out2.println(n+" "+(2*m));
			for (Graph.Vertex v:g.getVertexes()){
				for (Graph.Edge e:v.getRelatives()){
					out2.println(v.ID + " " +e.v.ID + " " +e.cost);
					out1.println(v.ID + " " +e.v.ID + " " +e.cost);
				}
			}
			out1.flush();
			out2.flush();
			pathmgep.main(null);
			pathbgep.main(null);
//			pathmgep p1 = new pathmgep();
//			pathbgep p2 = new pathbgep();
//			p1.solve();
//			p1.out.flush();
//			p2.solve();
//			p2.out.flush();
			
			Scanner in1 = new Scanner(new File("pathmgep.out"));
			Scanner in2 = new Scanner(new File("pathmgep.out"));
			while (in1.hasNext()){
				if (!in1.next().equals(in2.next())){
					h = false;
					break;
				}
			}
			System.out.println("Test "+ (k++) + "OK");
		}
		
	}

}
