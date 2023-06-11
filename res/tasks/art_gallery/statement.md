## Art Gallery

There is given a simple undirected graph with N vertices (numbered from 1 to N) and M edges.    
There are K security guards. Each guard has starting vertex P and stamina H.    
Guard guards a vertex X if distance between X and P is at most H.    
Find guarded vertices.

### input format
First line consists of numbers N,M,K (1<=N<=200 000, 0<=M<=200 000, 1<=K<=N)     
Next M lines describe the graph. I-th line consists of two numbers: the vertices connected by i-th edge.   
Next K lines describe guards. I-th line consists of two numbers: starting vertex and stamina of i-th guard.  
### output
First line contains number of guarded vertices - G.  
Next G lines contain the vertex numbers in ascending order. 
### sample test
input | output
---|---
5 5 2 | 4
1 2 | 1
2 3 | 2
2 4 | 3 
3 5 | 5
1 5 |
1 1 |
5 2 |

