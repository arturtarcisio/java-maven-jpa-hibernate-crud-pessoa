package br.com.treinaweb.jpa.ui;

import java.util.List;
import java.util.Scanner;

import br.com.treinaweb.jpa.models.Pessoa;
import br.com.treinaweb.jpa.services.impl.PessoaService;
import br.com.treinaweb.jpa.services.interfaces.CrudService;
import br.com.treinaweb.jpa.services.interfaces.PessoaBuscaPorNome;

public class Main {

	public static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		int opcao = 0;
		final int CONDICAO_DE_SAIDA = 6;

		while (opcao != CONDICAO_DE_SAIDA) {

			System.out.println("\n Escolha uma ação:");
			System.out.println("1. Listar pessoa");
			System.out.println("2. Inserir pessoa");
			System.out.println("3. Atualizar pessoa");
			System.out.println("4. Excluir pessoa");
			System.out.println("5. Pesquisar pessoa por nome");
			System.out.println("6. Sair");

			System.out.print("\n Sua opcao: ");

			opcao = SCANNER.nextInt();
			SCANNER.nextLine();
			switch (opcao) {
			case 1:
				listarPessoas();
				break;
			case 2:
				inserirPessoas();
				break;
			case 3:
				atualizarPessoas();
				break;
			case 4:
				excluirPessoas();
				break;
			case 5:
				pesquisarPorNome();
				break;
			case 6:
				break;
			default:
				System.out.println("Opção invalida!");
				break;
			}

		}
		System.out.println("Até mais!");

	}

	private static void listarPessoas() {
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();

		System.out.println("**** Gerenciamento de Pessoas ****");
		System.out.println(" > Lista de pessoas cadastradas: \n");
		try {
			List<Pessoa> pessoas = pessoaService.all();
			pessoas.forEach(pessoa -> {
				System.out.println(String.format(" -  (%d) %s %s - %d anos", pessoa.getId(), pessoa.getNome(),
						pessoa.getSobrenome(), pessoa.getIdade()));
			});
			if (pessoas.isEmpty()) {
				System.out.println(" - Não existe pessoas cadastradas.");
			}
		} catch (Exception e) {
			System.out.println("Houve um erro ao utilizar a jpa" + e.getMessage());
		}
	}

	private static void inserirPessoas() {
		System.out.println("\n **Inclusão de pessoa** ");

		Pessoa novaPessoa = new Pessoa();
		System.out.print("Nome:");
		novaPessoa.setNome(SCANNER.nextLine());
		System.out.print("Sobrenome:");
		novaPessoa.setSobrenome(SCANNER.nextLine());
		System.out.print("idade:");
		novaPessoa.setIdade(SCANNER.nextInt());

		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		pessoaService.insert(novaPessoa);

		System.out.println(" - Pessoa inserida com sucesso! :)");

	}

	private static void atualizarPessoas() {
		System.out.println("\n ** Atualização de pessoa **");
		System.out.println(" - Digite o id da pessoa a ser atualizada: ");
		int idPessoa = SCANNER.nextInt();
		SCANNER.nextLine();
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		Pessoa pessoaAtual = pessoaService.byId(idPessoa);
		if (pessoaAtual != null) {
			System.out.println(" - Pessoa encontrada!");
			System.out.println(String.format(" - Nome: %s", pessoaAtual.getNome()));
			System.out.println(String.format(" - Sobrenome: %s", pessoaAtual.getSobrenome()));
			System.out.println(String.format(" - Idade: %d\n", pessoaAtual.getIdade()));
			System.out.print(" - Novo nome: ");
			pessoaAtual.setNome(SCANNER.nextLine());
			System.out.print(" - Novo sobrenome: ");
			pessoaAtual.setSobrenome(SCANNER.nextLine());
			System.out.print(" - Nova idade: ");
			pessoaAtual.setIdade(SCANNER.nextInt());
			pessoaService.update(pessoaAtual);
			System.out.println("Pessoa atualizada com sucesso!");
		} else {
			System.out.println("Não existe pessoa com esse ID.");
		}

	}

	private static void excluirPessoas() {
		System.out.println("\n ** Remoção pessoa **");
		System.out.print("- Digite o id da pessoa a ser removida: ");
		int pessoaASerRemovida = SCANNER.nextInt();
		SCANNER.nextLine();

		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		pessoaService.deleteById(pessoaASerRemovida);

		System.out.println(" - Pessoa removida com sucesso!");

	}

	private static void pesquisarPorNome() {
		System.out.println("\n ** Pesquisando pessoas por nome **");
		System.out.print(" - Digite o nome da pessoa: ");
		String pessoaASerPesquisada = SCANNER.next();
		PessoaBuscaPorNome pessoaService = new PessoaService();

		pessoaService.searchByName(pessoaASerPesquisada)
				.forEach(pessoa -> System.out.println(String.format(" -  (%d) %s %s - %d anos", pessoa.getId(),
						pessoa.getNome(), pessoa.getSobrenome(), pessoa.getIdade())));

	}

}
