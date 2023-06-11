#include<iostream>
#include<queue>
#include<vector>

using namespace std;

int main()
{
        int n,m,k;
        cin>>n>>m>>k;
        vector<vector<int>>v(n+1);
        while(m--)
        {
                int a,b;
                cin>>a>>b;
                v[a].push_back(b);
                v[b].push_back(a);
        }
        vector<bool>ans(n+1);
        priority_queue<pair<int,int>>pq;
        while(k--)
        {
                int p,h;
                cin>>p>>h;
                pq.push(make_pair(h,p));
        }

        while(pq.size())
        {
                auto x = pq.top();
                pq.pop();

                if(ans[x.second])continue;
                ans[x.second] = 1;
                if(x.first == 0)continue;
                for(auto i:v[x.second])
                {
                        if(!ans[i])pq.push(make_pair(x.first-1,i));
                }
        }

        int G=0;
        for(int i=1;i<n+1;i++)G+=ans[i];
        cout<<G<<'\n';
        for(int i=1;i<n+1;i++)
                if(ans[i])cout<<i<<'\n';
}

