package conversaoNumerica.service.impl;

import conversaoNumerica.service.ConversorNumericoService
import util.MainUtil
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test

/**
 * Created by André Buzzo on 05/12/17.
 */
public class ConversorNumericoServiceTest {
	private final ConversorNumericoService service = new ConversorNumericoServiceImpl()

	@Test
	public void testConversorNumericoTestTable() throws IOException {
		//inicio do teste
		def inicioTeste = System.currentTimeMillis()

		// leio o arquivo texto, e transformo-o em uma lista de strings
		final List<String> linhasTestTable = MainUtil.obterLinhasDoArquivo(this.getClass(), "testTableAndre.txt");
		linhasTestTable.remove(0); // linha 0 corresponde ao titulo das colunas
		// os erro serão adicionados nessa lista
		final List<String> erros = new ArrayList<String>();
		for (String linhaTxt : linhasTestTable) {
			final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

			//|cenario|indoArabico|romano|ok|
			final String cenario = linha[0];
			final String indoArabicoEsperado = linha[1]
			final String romanoEsperado = linha[2]
			def resultadoEsperado = linha[3];

			final boolean erroEsperado = 'erro' == resultadoEsperado || 'ERRO' == resultadoEsperado;

			def indoArabicoGerado
			def romanoGerado

			def inicioCenario
			def fimCenario
			def mensagemFalha = "\n - Cenário: $linhaTxt \n - Saida: indo-arábico: %s; romano: %s.\n"
			try {
				inicioCenario = System.currentTimeMillis()
				if (romanoEsperado)
					indoArabicoGerado = service.converterParaIndoArabico(romanoEsperado);
				if (indoArabicoEsperado)
					romanoGerado = service.converterParaRomano(indoArabicoEsperado);
				fimCenario = System.currentTimeMillis()

				if (erroEsperado) {
					erros.add("Cenario " + cenario + " falhou: pois um erro era esperado. Tempo execução: ${fimCenario - inicioCenario} milisegundos. " + String.format(mensagemFalha, indoArabicoGerado, romanoGerado));
					continue;
				}

				if (indoArabicoEsperado != indoArabicoGerado) {
					erros.add("Cenario $cenario falhou: Esperado indo-arábico $indoArabicoEsperado, mas retornado $indoArabicoGerado. Tempo execução: ${fimCenario - inicioCenario} milisegundos." + String.format(mensagemFalha, indoArabicoGerado, romanoGerado));
					continue;
				}
				if (romanoEsperado != romanoGerado) {
					erros.add("Cenario $cenario falhou: Esperado romano $romanoEsperado, mas retornado $romanoGerado. Tempo execução: ${fimCenario - inicioCenario} milisegundos." + String.format(mensagemFalha, indoArabicoGerado, romanoGerado));
					continue;
				}

			} catch (Throwable e) {
				fimCenario = System.currentTimeMillis()
				if (!erroEsperado) {
					erros.add("Cenario $cenario falhou: pois NAO era esperado erro neste cenario. Tempo execução: ${fimCenario - inicioCenario} milisegundos. ${e.class.name} ${e.getMessage()}" + String.format(mensagemFalha, indoArabicoGerado, romanoGerado));
					continue;
				}
			}
			println("Cenario " + cenario + ": ok! Tempo execucao: ${fimCenario - inicioCenario} milisegundos");
		}

		def fimTeste = System.currentTimeMillis()
		println("==== Tempo execucao total do teste: ${fimTeste - inicioTeste} milisegundos ====");

		// se lista de erros contiver algum item, então falho o teste e exibo os erros.
		if (!erros.isEmpty()) {
			Assert.fail("Falharam ${erros.size()} de ${linhasTestTable.size()} cenários de teste: \n${StringUtils.join(erros, "\n")}");
		}
	}
}