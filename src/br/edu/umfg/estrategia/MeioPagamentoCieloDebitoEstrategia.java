package br.edu.umfg.estrategia;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class MeioPagamentoCieloDebitoEstrategia implements MeioPagamentoEstrategia {

    private final String numeroCartao;
    private final String cpf;
    private final String cvv;
    private final String dataValidade;

    public MeioPagamentoCieloDebitoEstrategia(String numeroCartao, String cpf, String cvv, String dataValidade) {
        validarDados(numeroCartao, cpf, cvv, dataValidade);

        this.numeroCartao = numeroCartao;
        this.cpf = cpf;
        this.cvv = cvv;
        this.dataValidade = dataValidade;
    }

    @Override
    public void pagar(Double valor) {
        System.out.printf("Pagamento via Cielo Débito no valor: " + valor + " realizado com sucesso \n");
    }

    private void validarDados(String numeroCartao, String cpf, String cvv, String dataValidade) {

        // Validação do número do cartão
        if (numeroCartao == null || numeroCartao.length() != 16 || possuiSequenciaRepetida(numeroCartao, 4) || !ehValidoPeloAlgoritmoLuhn(numeroCartao)) {
            throw new IllegalArgumentException("Número do cartão de débito inválido");
        }

        // Validação do CPF
        if (cpf == null || cpf.length() != 11 || !isValidCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Validação do CVV
        if (cvv == null || cvv.length() != 3 || !Pattern.matches("\\d{3}", cvv) || isRepeated(cvv)) {
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

    private boolean isRepeated(String sequence) {
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) != sequence.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCPF(String cpf) {
        if (isRepeated(cpf)) {
            return false;
        }
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        Integer digito1 = calculateDigit(cpf.substring(0, 9), weights1);
        Integer digito2 = calculateDigit(cpf.substring(0, 9) + digito1, weights2);

        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private int calculateDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Integer.parseInt("" + str.charAt(i)) * weights[i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }
}
