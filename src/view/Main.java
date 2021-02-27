package view;

import javax.swing.JOptionPane;

import controller.RedesController;

public class Main {

	public static void main(String[] args) {
		
		RedesController op = new RedesController();	
		
		int opc = 0;
		do {
			opc = Integer.parseInt(JOptionPane.showInputDialog("Digite a opção desejada: \n"
					+ "1 - Receber adaptadores com Ipv4; \n"
					+ "2 - Receber média de pings no servidor google. \n"
					+ "0 - Para sair;"));
			
			switch(opc) {
			case 1: op.ip("ipconfig");
			break;
			case 2: op.getPing("ping -4 -n 10 www.google.com.br");
			break;
			case 0: JOptionPane.showMessageDialog(null, "Obrigado por utilizar a aplicação");
			break;
			default: JOptionPane.showMessageDialog(null, "Opção inválida.");
			}
		}while(opc != 0);
		

	}

}
