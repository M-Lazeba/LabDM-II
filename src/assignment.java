import java.util.*;
import java.io.*;

public class assignment {
	StringTokenizer st;
	PrintWriter out;
	BufferedReader in;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		assignment p = new assignment();
		p.init();
		p.solve();
		p.out.flush();
	}

	void init() throws IOException {
		in = new BufferedReader(new FileReader("assignment.in"));
		out = new PrintWriter("assignment.out");
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

	void solve() throws NumberFormatException, IOException {
		n = nextInt();
		mat = new int[n][n];
		minR = new int[n];
		minC = new int[n];
		pairC = new int[n];
		pairR = new int[n];
		visC = new boolean[n];
		visR = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int c = nextInt();
				mat[i][j] = c;
			}
		}
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
								z = Math.min(z, mat[i][j] - minR[i] - minC[j]);
				for (int i = 0; i < n; ++i) {
					if (visR[i])
						minR[i] += z;
					if (visC[i])
						minC[i] -= z;
				}
			}
		}

		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += mat[i][pairR[i]];
		}
		out.println(ans);
		for (int i = 0; i < n; i++) {
			out.println((i + 1) + " " + (pairR[i] + 1));
		}
	}

}
