#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<fstream>
using namespace std;
int main(){
	freopen("TEMPLE.GRID","w+",stdout);
	for(int i = -20; i < 20; i++){
	for(int j = -20; j < 20; j++){
	for(int k = -1; k < 5; k++){
	cout<<"Block\t\t"<<i<<"\t"<<k<<"\t"<<j<<endl;
	}
}
}
	fclose(stdout);
}
