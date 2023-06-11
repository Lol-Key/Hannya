#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    int *p = new int[n];
    int *sz = new int[n];
    for (int i = 1; i < n; ++ i) scanf("%d", &p[i]), -- p[i], sz[i] = 0;
    sz[0] = 0;
    long long ans = 0;
    for (int i = n - 1; i > 0; -- i) sz[p[i]] += (++ sz[i]), ans += (sz[i] < n - sz[i] ? sz[i] : n - sz[i]);
    printf("%lld", 2 * ans);
    delete [] p;
    delete [] sz;
    return 0;
}