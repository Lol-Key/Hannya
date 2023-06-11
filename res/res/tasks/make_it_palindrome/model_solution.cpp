#include<bits/stdc++.h>

using namespace std;

int main(){
	long long n;
	cin >> n;
	vector<vector<long long>> p(n+1);
	for(int i=1;i<=n;i++){
		long long x;
		cin >> x;
		p[x].push_back(i);
	}
	
	long long res=0;
	for(long long i=1;i<=n;i++)res+=(n+1-i)*(i/2);

	for(long long i=1;i<=n;i++){
		long long l=0,r=p[i].size()-1;
		while(l<r){
			if(p[i][l]<(n+1-p[i][r])){
				res-=(r-l)*p[i][l];
				l++;
			}
			else{
				res-=(r-l)*(n+1-p[i][r]);
				r--;
			}
		}
	}
	cout << res << "\n";
	return 0;
    }
