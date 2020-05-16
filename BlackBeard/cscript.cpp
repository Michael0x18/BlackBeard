#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<fstream>
using namespace std;
int main(){
	freopen("TEMPLE.GRID","w+",stdout);
	for(int i = -20; i < 20; i++){
	for(int j = -20; j < 20; j++){
	cout<<"Block\t\t"<<i<<"\t-1\t"<<j<<endl;
	}
}
	fclose(stdout);
}
