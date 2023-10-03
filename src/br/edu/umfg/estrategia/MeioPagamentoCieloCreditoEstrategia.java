package br.edu.umfg.estrategia;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class MeioPagamentoCieloCreditoEstrategia implements MeioPagamentoEstrategia {

    private final String numeroCartao;
    private final String cpf;
    private final String cvv;
    private final String dataValidade;

    public MeioPagamentoCieloCreditoEstrategia(String numeroCartao, String cpf, String cvv, String dataValidade) {
        validarDados(numeroCartao, cpf, cvv, dataValidade);

        this.numeroCartao = numeroCartao;
        this.cpf = cpf;
        this.cvv = cvv;
        this.dataValidade = dataValidade;
    }

    @Override
    public void pagar(Double valor) {
        System.out.printf("Pagamento via Cielo Crédito no valor: " + valor + " realizado com sucesso \n");
    }

    private void validarDados(String numeroCartao, String cpf, String cvv, String dataValidade) {

        // Validação do número do cartão
        if (numeroCartao == null || numeroCartao.length() != 16 || possuiSequenciaRepetida(numeroCartao, 4) || !ehValidoPeloAlgoritmoLuhn(numeroCartao)) {
            throw new IllegalArgumentException("Número do cartão de crédito inválido");
        }

        // Validação do CPF
        if (cpf == null || cpf.length() != 11 || todosDigitosIguais(cpf) || !ehCPFValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Validação do CVV
        if (cvv == null || cvv.length() != 3 || !Pattern.matches("\\d{3}", cvv)) {
            throw new IllegalArgumentException("CVV inválido");
        }

        // Validação da data de validade
        YearMonth dataAtual = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth dataCartao = YearMonth.parse(dataValidade, formatter);
        if (dataCartao.isBefore(dataAtual)) {
            throw new IllegalArgumentException("Data de validade do cartão inválida");
        }
    }

    private boolean possuiSequenciaRepetida(String str, int tamanhoSequencia) {
        for (int i = 0; i <= str.length() - tamanhoSequencia; i++) {
            String substring = str.substring(i, i + tamanhoSequencia);
            String repetido = String.valueOf(substring.charAt(0)).repeat(tamanhoSequencia);
            if (substring.equals(repetido)) {
                return true;
            }
        }
        return false;
    }

    private boolean todosDigitosIguais(String str) {
        return str.chars().distinct().count() == 1;
    }

    private boolean ehValidoPeloAlgoritmoLuhn(String numeroCartao) {
        int soma = 0;
        boolean alternar = false;

        for (int i = numeroCartao.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numeroCartao.substring(i, i + 1));
            if (alternar) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            soma += n;
            alternar = !alternar;
        }

        return (soma % 10 == 0);
    }

    private boolean ehCPFValido(String cpf) {
        // Verificação dos dígitos verificadores
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += (cpf.charAt(i) - '0') * (10 - i);
            soma2 += (cpf.charAt(i) - '0') * (11 - i);
        }

        int digito1 = 11 - (soma1 % 11);
        digito1 = digito1 > 9 ? 0 : digito1;

        soma2 += digito1 * 2;
        int digito2 = 11 - (soma2 % 11);
        digito2 = digito2 > 9 ? 0 : digito2;

        return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;
    }
}
