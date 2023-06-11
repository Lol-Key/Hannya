#include<iostream>
#include<queue>
#include<vector>

using namespace std;

bool find(int v, vector<vector<int> >& g, vector<int>& mt, vector<int>& vis) {
	if (vis[v]) return false;
    vis[v] = true;
	for (int u : g[v])
		if (mt[u] == -1 || find(mt[u], g, mt, vis)) {
            mt[v] = u, mt[u] = v;
            return true;
        }
	return false;
}

int turboMatching(vector<vector<int> >& g) {
	int ans = 0;
    vector<int> vis, mt(g.size(), -1);
    bool aug = true;
    while (aug) {
        aug = false;
        vis = vector<int>(g.size(), 0);
        for (int i = 0; i < g.size(); ++ i)
            if (mt[i] == -1 && find(i, g, mt, vis))
                aug = true, ++ ans;
    }
    return ans;
}

int main() {
    ios_base::sync_with_stdio(false);
    int n;
    cin >> n;
    vector<unsigned int> a(n);
    for (int i = 0; i < n; ++ i)
        cin >> a[i];
    vector<vector<int> > g(n);
    for (int i = 0; i < n; ++ i) {
        int p = -1;
        for (int j = 0; j < n; ++ j)
            if (j != i && (p == -1 || (a[j] ^ a[i]) > (a[p] ^ a[i])))
                p = j;
        if (p != -1) {
            g[i].push_back(p);
            g[p].push_back(i);
        }
    }
    cout << n - turboMatching(g) << '\n';
}

