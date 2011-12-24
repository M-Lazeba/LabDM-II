import java.util.*;
import java.io.*;

public class multiassignment {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		multiassignment p = new multiassignment();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("multiassignment.in"));
		out = new PrintWriter("multiassignment.out");
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

	class Assignment {
		StringTokenizer st;
		PrintWriter out;
		BufferedReader in;

		int n;
		int mat[][];
		int[] minR, minC, pairC, pairR, dc, dr;
		boolean[] visR, visC;

		boolean recomb(int i) {
			if (visR[i])
				return false;
			visR[i] = true;
			for (int j = 0; j < n; ++j)
				if (mat[i][j] - minR[i] - minC[j] == 0)
					visC[j] = true;
			for (int j = 0; j < n; ++j)
				if (mat[i][j] - minR[i] - minC[j] == 0 && pairC[j] == -1) {
					pairR[i] = j;
					pairC[j] = i;
					return true;
				}
			for (int j = 0; j < n; ++j)
				if (mat[i][j] - minR[i] - minC[j] == 0 && recomb(pairC[j])) {
					pairR[i] = j;
					pairC[j] = i;
					return true;
				}
			return false;
		}

		ArrayList<Integer> find(int[][] mat) {
			this.mat = mat;
			n = mat.length;
			minR = new int[n];
			minC = new int[n];
			pairC = new int[n];
			pairR = new int[n];
			visC = new boolean[n];
			visR = new boolean[n];

			Arrays.fill(minR, Integer.MAX_VALUE);
			Arrays.fill(minC, Integer.MAX_VALUE);
			for (int i = 0; i < n; ++i)
				for (int j = 0; j < n; ++j)
					minR[i] = Math.min(minR[i], mat[i][j]);
			for (int j = 0; j < n; ++j)
				for (int i = 0; i < n; ++i)
					minC[j] = Math.min(minC[j], mat[i][j] - minR[i]);

			Arrays.fill(pairC, -1);
			Arrays.fill(pairR, -1);

			for (int c = 0; c < n;) {
				Arrays.fill(visC, false);
				Arrays.fill(visR, false);
				int k = 0;
				for (int i = 0; i < n; ++i)
					if (pairR[i] == -1 && recomb(i))
						++k;
				c += k;

				if (k == 0) {
					int z = Integer.MAX_VALUE;
					for (int i = 0; i < n; ++i)
						if (visR[i])
							for (int j = 0; j < n; ++j)
								if (!visC[j])
									z = Math.min(z, mat[i][j] - minR[i]
											- minC[j]);
					for (int i = 0; i < n; ++i) {
						if (visR[i])
							minR[i] += z;
						if (visC[i])
							minC[i] -= z;
					}
				}
			}

			long w = 0;
			for (int i = 0; i < n; i++) {
				w += mat[i][pairR[i]];
			}
			weight += w;

			ArrayList<Integer> ans = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				ans.add(pairR[i]);
			}
			return ans;
		}

	}

	long weight = 0;

	void solve() throws NumberFormatException, IOException {
		int n = nextInt();
		int k = nextInt();
		int[][] mat = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				mat[i][j] = nextInt();
			}
		}
		ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		Assignment alg = new Assignment();
		for (int i = 0; i < k; i++) {
			ArrayList<Integer> newAns = alg.find(mat);
			ans.add(newAns);
			for (int u = 0; u < n; u++) {
				int v = newAns.get(u);
				mat[u][v] = Integer.MAX_VALUE / 2;
			}
		}

		out.println(weight);
		for (ArrayList<Integer> ansItem : ans) {
			for (int a : ansItem) {
				out.print((a + 1) + " ");
			}
			out.println();
		}
	}

}
