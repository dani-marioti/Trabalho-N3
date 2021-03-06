package n2.ui;

import java.util.Scanner;

public class MenuPrincipalTexto {
	
	private static final int OP_PESSOAS = 1;
	private static final int OP_DEPTOS = 2;

	private static final int OP_ADICIONAR = 1;
	private static final int OP_LISTAR = 2;
	private static final int OP_EDITAR = 3;
	private static final int OP_EXCLUIR = 4;
	
	// conjunto de estados possiveis no sistema
	private enum Estado {PRINCIPAL, PESSOAS, DEPTOS};
	
	private Estado estadoAtual; // armazena o estado atual do menu
	private Scanner entrada;
	
	public MenuPrincipalTexto() {
		estadoAtual = Estado.PRINCIPAL;
		entrada = new Scanner(System.in); // configura o Scanner para ler da entrada padr�o (STDIN)
	}
	
	private void imprimeMenuPrincipal() {
		System.out.println("1 - Administra��o de Pessoas");
		System.out.println("2 - Administra��o de Departamentos");
	}
	
	private void imprimeMenuSecund�rio(String tipoMenu) {
		System.out.println("Administra��o de " + tipoMenu);
		System.out.println();
		System.out.println("1 - Adicionar");
		System.out.println("2 - Listar");
		System.out.println("3 - Editar");
		System.out.println("4 - Excluir");
	}
	
	// m�todo principal de execu��o do menu
	public void executa() {
		int opcao;
		MenuEspecificoTexto menuEspecificoTexto;
		
		do {
			// Mostra o menu principal ou o menu secund�rio
			System.out.println("Administra��o de RH"); // T�tulo
			System.out.println();
			
			switch(estadoAtual) {
			// se estado PESSOAS imprime menu pessoas
			case PESSOAS:
				imprimeMenuSecund�rio("Pessoas");
				break;
			// se estado DEPTOS imprime menu departamentos
			case DEPTOS:
				imprimeMenuSecund�rio("Departamentos");
				break;
			default:
				imprimeMenuPrincipal();
			}
			
			System.out.println();
			System.out.println("0 - Sair");
			
			System.out.println();
			System.out.print("Escolha uma op��o: ");
	
			// obtem entrada do usu�rio
			opcao = entrada.nextInt();
			entrada.nextLine();
			
			System.out.println("Voce escolheu a op��o: " + opcao);
				
			// toma uma a��o conforme o que o usu�rio escolhe
			if (estadoAtual == Estado.PRINCIPAL) {
				switch (opcao) {
				case OP_PESSOAS:
					estadoAtual = Estado.PESSOAS;
					break;
				//case OP_DEPTOS:
				//	estadoAtual = Estado.DEPTOS;
				//	break;
				}
			} else {
				menuEspecificoTexto = new MenuPessoaTexto(); // apagar esta linha

                /*
                if (estadoAtual == Estado.PESSOAS) {
                    menuEspecificoTexto = new MenuPessoaTexto();
                } else {
                    menuEspecificoTexto = new MenuDepartamentoTexto(); // <-- implementar esta classe
                }
                 */

                /*
                if (estadoAtual == Estado.PESSOAS) {
                    menuEspecificoTexto = new MenuPessoaTexto();
                } else {
                    menuEspecificoTexto = new MenuDepartamentoTexto(); // <-- implementar esta classe
                }
                 */

				switch (opcao) {
					case OP_ADICIONAR:
						//adicionar um item
						menuEspecificoTexto.adicionar();
						break;
					case OP_EDITAR:
						//editar um item
						menuEspecificoTexto.editar();
						break;
					case OP_EXCLUIR:
						//excluir um item
						menuEspecificoTexto.excluir();
						break;
					case OP_LISTAR:
						//listar um item
						menuEspecificoTexto.listarTodos();
						break;
					default:
						System.out.println("Op��o inv�lida. Tente novamente!");
				}
			}
			
			
		} while (opcao != 0);// enquanto o usu�rio n�o sai do sistema
		
	}
	
}