package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {
	
	private String adaptIp = " ";
	
	public RedesController() {
		super();
	}
	
	public void ip(String ipconfig) {
		
		String sistemaOp = os();
		String adap [] = new String [4];
		int i = 0;
		String ipv4 [] = new String [4];
		
		if(sistemaOp.contains("Windows")) {
			//TODO
			try {
				Process ipcfg = Runtime.getRuntime().exec(ipconfig);
				InputStream fluxo = ipcfg.getInputStream(); //fluxo de dados
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				while(linha !=null) {
					//System.out.println(linha);
					if (linha.contains("Adaptador")){
						adap[i] = linha;
						i++;
					} else if(linha.contains("IPv4")){
						ipv4[i-1] = linha;
					}
					linha = buffer.readLine();
					
				}
				buffer.close();
				leitor.close();
				fluxo.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		else {
			ipconfig = "ifconfig";
			try {
			Process ipcfg = Runtime.getRuntime().exec(ipconfig);
			InputStream fluxo = ipcfg.getInputStream(); //fluxo de dados
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			
			while(linha !=null) {
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}
		}
		for (int j = 0; j<4; j++) {
			System.out.println(adap[j] + " \n " + ipv4[j]);
		}
	}
		

	public String os() {
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		return os + " v. " + version + " arch. " +arch;
	}
	
	
	public String getAdaptador(BufferedReader buffer, String linha) {
		String adaptador = linha;
		String ipv4 = null;
		int aux = 0;
		System.out.println(adaptador);
		do {
			try {
				
				if(linha.contains("IPv4")) {
					ipv4 = getIPv4(linha);
				}
				linha = buffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(linha);
			if(linha == null) {
				aux = 1;
			}
		} while(!linha.contains("Adaptador")  || aux != 1);
		
		adaptIp =  adaptIp + adaptador + " " + ipv4 + " / ";
		System.out.println(adaptIp);
		
		if(linha != null) {
			getAdaptador(buffer, linha);
		}
		return adaptIp;		
	}
	
	public String getIPv4(String linha) {
		String IPv4 = linha;
		System.out.println(IPv4);
		return IPv4;
	}
	
}
