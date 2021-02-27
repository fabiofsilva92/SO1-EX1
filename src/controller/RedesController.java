package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {
	
	
	public RedesController() {
		super();
	}
	
	public String os() {
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		return os + " v. " + version + " arch. " +arch;
	}
	
	public void ip(String ipconfig) {
		
		String sistemaOp = os();
		
		int contador = contagemAdap (ipconfig, sistemaOp);
		String[][] AdapIp = new String [contador][contador];
		
		if(sistemaOp.contains("Windows")) {
			//TODO
			try {
				Process ipcfg = Runtime.getRuntime().exec(ipconfig);
				InputStream fluxo = ipcfg.getInputStream(); //fluxo de dados
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				AdapIp = getAdapIpv4(buffer, linha);
				
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
			
			AdapIp = getAdapIpv4(buffer, linha);
			
			buffer.close();
			leitor.close();
			fluxo.close();
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}
		}
		for (int j = 0; j<contador; j++) {
			if(AdapIp[1][j] != null) {
				System.out.println(AdapIp[0][j] + " \n " + AdapIp[1][j]);
			}
		}
	}
	
	public String [][] getAdapIpv4 (BufferedReader buffer, String linha) {
		String [] [] AdapIpv4 = new String[5][5];
		int i = 0;
		while(linha !=null) {
			//System.out.println(linha);
			if (linha.contains("Adaptador")){
				AdapIpv4[0][i] = linha;
				i++;
			} else if(linha.contains("IPv4")){
				AdapIpv4[1][i-1] = linha;
			}
			try {
				linha = buffer.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return AdapIpv4;
	}
	
	//Método para pegar quantos adaptadores tem no sistema
	public int contagemAdap (String ipconfig, String sistemaOp) {
		
		int contador = 0;
		
		if(sistemaOp.contains("Windows")) {
			try {
				Process ipcfg = Runtime.getRuntime().exec(ipconfig);
				InputStream fluxo = ipcfg.getInputStream(); //fluxo de dados
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				while(linha!=null) {
					if(linha.contains("Adaptador")) {
						contador++;
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
			
			while(linha!=null) {
				if(linha.contains("Adaptador")) {
					contador++;
				}
				linha = buffer.readLine();
			}
			
			buffer.close();
			leitor.close();
			fluxo.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//System.out.println(contador);
		return contador;
	}
	
	public void getPing(String ping) {
		
		String sistemaOp = os();
		String getMedia = "";
		
		if(sistemaOp.contains("Windows")) {
			getMedia = passagemPing(getMedia, ping);
			}
		else {
			ping = "ping -4 -c 10 www.google.com.br";
			getMedia = passagemPing(getMedia, ping);
		}
		
		System.out.println(getMedia);
		System.out.println(" // ");
		
		String [] media = getMedia.split(",");
		
		System.out.println(media[2]);
		
	}
	
	public String passagemPing(String getMedia, String ping) {
		int i = 0;
		try {
			Process getping = Runtime.getRuntime().exec(ping);
			InputStream fluxo = getping.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			System.out.println("Aguarde um momento...");
			
			while(linha!=null) {
				if(linha.contains("dia")) {
					getMedia = linha;
				} else if (linha.contains("Resposta") && linha !=null) {
					System.out.println("Ping " + (i+1));
					i++;
				}
				//System.out.println(linha);
				linha = buffer.readLine();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getMedia;
	}

	
}
