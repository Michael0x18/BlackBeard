package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Tester {

	public static void main(String[] args) throws Exception {
		Socket s = new Socket("",59090);
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while(true) {
			if(in.ready()) {
				if(in.readLine().equals(":ping")) {
					out.println("-ping");
				}
			}
		}
	}
	
}
