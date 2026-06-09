package com.laryssa.fipeexplorer.main;

import com.laryssa.fipeexplorer.model.Dados;
import com.laryssa.fipeexplorer.model.Modelos;
import com.laryssa.fipeexplorer.service.ConsumoAPI;
import com.laryssa.fipeexplorer.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

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

        boolean vrf = true;
        while(vrf){
            String codigo = leitura.nextLine();

            if (codigo.matches("\\d+")) {
                endereco += "/" + codigo + "/modelos";
                vrf = false;
            } else {
                System.out.println("Digite apenas números!");
            }
        }

        json = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca:");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}
