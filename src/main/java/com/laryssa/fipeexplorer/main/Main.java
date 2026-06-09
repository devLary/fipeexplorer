package com.laryssa.fipeexplorer.main;

import com.laryssa.fipeexplorer.model.Dados;
import com.laryssa.fipeexplorer.model.Modelos;
import com.laryssa.fipeexplorer.service.ConsumoAPI;
import com.laryssa.fipeexplorer.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();

        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(d -> Integer.parseInt(d.codigo())))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca para consulta:");

        while(true){
            String codigo = leitura.nextLine();

            if (codigo.matches("\\d+")) {
                endereco += "/" + codigo + "/modelos";
                break;
            } else {
                System.out.println("\nDigite apenas números!");
            }
        }

        json = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca:");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do veiculo desejado:");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo:");

        while (true){
            var codigoModelo = leitura.nextLine();
            if (codigoModelo.matches("\\d+")) {
                endereco += "/" + codigoModelo + "/anos";
                break;
            } else {
                System.out.println("\nDigite apenas números!");
            }
        }

        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        System.out.println(json);
    }
}
