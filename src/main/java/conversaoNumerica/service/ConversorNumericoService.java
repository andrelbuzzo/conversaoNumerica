package conversaoNumerica.service;

/**
 * Created by André Buzzo on 05/12/17.
 */
public interface ConversorNumericoService {

	/**
	 * Método responsável por receber um número romano e converte-lo para indo-arábico .
	 * Exemplos:
	 *   - Entrada: II; saída: 2;
	 *   - Entrada: M; saída: 1000.
	 * @param pNumeroRomano
	 * @return número indo-arábico
	 */
	public String converterParaIndoArabico(String pNumeroRomano) throws IllegalArgumentException;;

	/**
	 * Método responsável por receber um número indo-arábico e converte-lo para romano.
	 * Exemplos:
	 *   - Entrada: 10; saída: X;
	 *   - Entrada: 1000; saída: M.
	 * @param pIndoArabico
	 * @return número romano
	 */
	public String converterParaRomano(String pIndoArabico) throws IllegalArgumentException;;
}