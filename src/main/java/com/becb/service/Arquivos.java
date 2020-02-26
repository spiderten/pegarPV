package com.becb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.becb.modelo.Dados;
import com.becb.modelo.Posto;

@Service
public class Arquivos {

	private String arquivo = "saida.csv";
	private List<String> linhas;

	public void limpaSaida(String arquivo) throws IOException {

		Path path = Paths.get(arquivo);
		try {
			Files.write(path, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (java.nio.file.NoSuchFileException e) {
			Files.write(path, "".getBytes(), StandardOpenOption.CREATE_NEW);
		}

	}

	@SuppressWarnings("finally")
	public List<Dados> lerArquivo(String nome, String arquivo) {

		List<Dados> lDados = new ArrayList<Dados>();
		try {

			Path path = Paths.get(nome);
			List<String> linhas = Files.readAllLines(path);

			if (null != linhas)
				for (String linha : linhas) {
					System.out.println(linha);
					lDados.add(new Dados(linha));
				}
			limpaSaida(arquivo);

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
			e.printStackTrace();
		} finally {
			return lDados;
		}

	}

	public void escreverArquivo(String string) throws IOException {
		Path path = Paths.get(arquivo);
		try {
			Files.write(path, string.getBytes(), StandardOpenOption.APPEND);
		} catch (java.nio.file.NoSuchFileException e) {
			Files.write(path, string.getBytes(), StandardOpenOption.CREATE_NEW);
		}
	}

	public void salvarDados(List<Posto> postos, Dados dados, String arquivo) {

		this.arquivo = arquivo;
		try {
			String escrever;
			for (Posto posto : postos) {
				escrever = dados.getId() + ";" + dados.getLatitude() + ";" + dados.getLongitute() + ";" + posto.getId()
						+ ";" + posto.getNomeFantasia() + ";" + posto.getCidade() + "\n";

				escreverArquivo(escrever);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cabecalho(String arquivo) throws IOException {
		this.arquivo = arquivo;
		String escrever;
		escrever = "CD_TOKEN_PED" + ";" + "LATITUDE" + ";" + "LONGITUDE" + ";" + "ID_POSTO" + ";" + "NOME_FANTASIA"
				+ ";" + "CIDADE" + "\n";
		escreverArquivo(escrever);
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public List<String> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<String> linhas) {
		this.linhas = linhas;
	}
}
